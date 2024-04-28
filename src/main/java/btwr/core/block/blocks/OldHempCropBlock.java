package btwr.core.block.blocks;

import btwr.core.block.BTWR_Blocks;
import btwr.core.block.interfaces.BlockAdded;
import btwr.core.item.BTWR_Items;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class OldHempCropBlock extends CropBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 8);
    public static final BooleanProperty TOP = BooleanProperty.of("top");


    public OldHempCropBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), 0).with(TOP, false));
    }


    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return false;
    }

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            // Define VoxelShapes for each stage from 0 to 8
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 2.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 4.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 8.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 10.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 12.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 14.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 16.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 16.0, 11.0)

    };


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }


    @Override
    protected ItemConvertible getSeedsItem() {
        return BTWR_Items.HEMP_SEEDS;
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 8;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, TOP);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState lowerPart = world.getBlockState(blockPos);
        if (lowerPart.getBlock() instanceof OldHempCropBlock) {
            return lowerPart.get(OldHempCropBlock.AGE) == 7;
        }
        return super.canPlaceAt(state, world, pos);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.addExhaustion(0.2F);

        if (stack.isOf(Items.SHEARS)) {
            // If the crop is fully grown, drop items
            dropStack(world, pos, new ItemStack(BTWR_Items.HEMP_LEAVES, 1));

            // Generate a random number of hemp seeds between 0 and 1
            int numHempSeeds = world.getRandom().nextInt(2); // Generates a number between 0 and 1 (inclusive)

            for (int i = 0; i < numHempSeeds; i++) {
                dropStack(world, pos, new ItemStack(BTWR_Items.HEMP_SEEDS, 1));
            }
        }
    }





    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        int age = state.get(AGE);


        if (world.isAir(pos.up()))
        {

            // Check if the crop is attempting to grow (random chance)
            if ((world.getBaseLightLevel(pos, 0) >= 15) || hasLightBlockAbove(world, pos)) {

                BlockState blockBelowPlant = world.getBlockState(pos.down());
                if (blockBelowPlant != null && ((BlockAdded) blockBelowPlant.getBlock()).isBlockHydratedForPlantGrowthOn(world, pos.down())) {

                    // only the base of the plants grows, and only does if its on hydrated soil

                    if (age < 7 && !state.get(TOP)) {

                        float fChanceOfGrowth = getBaseGrowthChance() *
                                ((BlockAdded) blockBelowPlant.getBlock()).getPlantGrowthOnMultiplier(world, pos.down(), this);

                        // Check if the crop is attempting to grow (random chance)
                        if (random.nextFloat() <= fChanceOfGrowth)
                        {
                            incrementGrowthLevel(world, pos, state);
                        }

                    }
                }
                else if (age == 7 && world.isAir(pos.up()))
                {

                    // check for growth of top block
                    float fChanceOfGrowth = (getBaseGrowthChance() / 4F) *
                            ((BlockAdded) blockBelowPlant.getBlock()).getPlantGrowthOnMultiplier(world, pos.down(), this);

                    if (random.nextFloat() <= fChanceOfGrowth)
                    {
                        // top of the plant
                        world.setBlockState(pos.up(), BTWR_Blocks.CROP_HEMP.getDefaultState().with(AGE, 8).with(TOP, true), 3);
                        ((BlockAdded) blockBelowPlant.getBlock()).notifyOfFullStagePlantGrowthOn(world, pos.down(), this);

                    }
                }
            }
        }
    }

    protected void incrementGrowthLevel(World world, BlockPos pos, BlockState state)
    {
        int iGrowthLevel = this.getAge(state) + 1;

        world.setBlockState(pos, state.with(AGE, iGrowthLevel));

        if ( state.get(AGE) == 7 || ( state.get(AGE) == 8 && state.get(TOP) ) )
        {
            BlockState belowState = world.getBlockState(pos.down());

            if ( belowState != null )
            {
                ((BlockAdded)belowState.getBlock()).notifyOfFullStagePlantGrowthOn(world, pos.down(), this);
            }
        }
    }



    // Check if there are crops in the adjacent north or south rows
    private boolean hasCropsAdjacentOnEitherSides(World world, BlockPos pos)
    {
        BlockState blockNorth = world.getBlockState(pos.north());
        BlockState blockSouth = world.getBlockState(pos.south());
        BlockState blockWest = world.getBlockState(pos.west());
        BlockState blockEast = world.getBlockState(pos.east());

        return ((blockNorth.isOf(this) && blockSouth.isOf(this))
                || (blockWest.isOf(this) && blockEast.isOf(this)));
    }

    private boolean hasLightBlockAbove(World world, BlockPos pos)
    {
        boolean isAbove = world.getBlockState(pos.up(1)).isOf(BTWR_Blocks.LIGHTBLOCK.getDefaultState().with(LightBlock.LIT, true).getBlock());
        boolean isTwoAbove = world.getBlockState(pos.up(2)).isOf(BTWR_Blocks.LIGHTBLOCK.getDefaultState().with(LightBlock.LIT, true).getBlock());

        return isAbove || isTwoAbove;

    }

    private float getBaseGrowthChance()
    {
        return 0.1F;
    }




}

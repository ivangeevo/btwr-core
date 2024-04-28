package btwr.core.block.blocks;

import btwr.core.block.BTWR_Blocks;
import btwr.core.block.interfaces.BlockAdded;
import btwr.core.item.BTWR_Items;
import btwr.core.tag.BTWRConventionalTags;
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

public class HempCropBlock extends CropBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 7);
    public static final BooleanProperty TOP = BooleanProperty.of("top"); // Add the IS_TALL property

    private final int firstStageTimer = 0;
    private final int topStageTimer = 0;


    public HempCropBlock(AbstractBlock.Settings settings) {
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
        return 7;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, TOP);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState lowerPart = world.getBlockState(blockPos);
        if (lowerPart.getBlock() instanceof HempCropBlock) {
            return lowerPart.get(HempCropBlock.AGE) == 7;
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
            if ((world.getBaseLightLevel(pos, 0) >= 15) || hasLightBlockAbove(world, pos))
            {

                BlockState blockBelowPlant = world.getBlockState(pos.down());
                if ( blockBelowPlant != null &&  ((BlockAdded)blockBelowPlant.getBlock()).isBlockHydratedForPlantGrowthOn(world, pos.down()) )
                {

                    // only the base of the plants grows, and only does if its on hydrated soil

                    if (age < 7 && !state.get(TOP))
                    {
                        float moisture = getAvailableMoisture(this, world, pos);

                        // Check if the crop is attempting to grow (random chance)
                        if (random.nextInt((int) (30 / moisture) + 1) == 0)
                        {

                            if (!hasCropsAdjacentOnEitherSides(world, pos))
                            {
                                // Delay block update of the crop block itself
                                world.setBlockState(pos, state.with(AGE, age + 1));
                            }
                            else
                            {
                                // Slow down growth by 40% if adjacent crops are present
                                if (random.nextFloat() < 0.4)
                                {
                                    world.setBlockState(pos, state.with(AGE, age + 1));
                                }
                            }
                        }
                    }
                    else if (age == 7 && world.isAir(pos.up()))
                    {
                        if (random.nextInt(30) == 0)
                        {


                            if (!hasCropsAdjacentOnEitherSides(world, pos)) {
                                // Delay block update of the block above
                                world.setBlockState(pos.up(), BTWR_Blocks.CROP_HEMP.getDefaultState().with(AGE, 7).with(TOP, true));
                            }
                        }
                    }
                }
            }
        }
    }

    protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockPos = pos.down();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float g = 0.0F;
                BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
                if (blockState.isIn(BTWRConventionalTags.Blocks.FARMLAND_BLOCKS))
                {
                    g = 1.0F;
                    if (blockState.get(FarmlandBlock.MOISTURE) > 0) {
                        g = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    g /= 4.0F;
                }

                f += g;
            }
        }

        BlockPos blockPos2 = pos.north();
        BlockPos blockPos3 = pos.south();
        BlockPos blockPos4 = pos.west();
        BlockPos blockPos5 = pos.east();
        boolean bl = world.getBlockState(blockPos4).isOf(block) || world.getBlockState(blockPos5).isOf(block);
        boolean bl2 = world.getBlockState(blockPos2).isOf(block) || world.getBlockState(blockPos3).isOf(block);
        if (bl && bl2) {
            f /= 2.0F;
        } else {
            boolean bl3 = world.getBlockState(blockPos4.north()).isOf(block) || world.getBlockState(blockPos5.north()).isOf(block) || world.getBlockState(blockPos5.south()).isOf(block) || world.getBlockState(blockPos4.south()).isOf(block);
            if (bl3) {
                f /= 2.0F;
            }
        }

        return f;
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




}

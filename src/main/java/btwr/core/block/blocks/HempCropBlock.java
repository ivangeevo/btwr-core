package btwr.core.block.blocks;

import btwr.core.block.BTWR_Blocks;
import btwr.core.block.interfaces.BlockAdded;
import btwr.core.item.BTWR_Items;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
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

import static btwr.core.block.blocks.LightBlock.LIT;

public class HempCropBlock extends CropBlock {
    public static final BooleanProperty TOP = BooleanProperty.of("top");
    public static final IntProperty AGE = IntProperty.of("age", 0, 7);

    public HempCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(TOP, false));
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
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 16.0, 11.0)

    };

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        return AGE_TO_SHAPE[state.get(AGE)];
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return BTWR_Items.HEMP_SEEDS;
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(TOP, AGE);
    }

    @Override
    public int getMaxAge() {
        return 8;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {

        super.afterBreak(world, player, pos, state, blockEntity, stack);

        if (stack.getItem() instanceof ShearsItem && player.getEquippedStack(EquipmentSlot.MAINHAND) != null) {
            // If the crop is fully grown, drop items
            dropStack(world, pos, new ItemStack(BTWR_Items.HEMP_LEAVES, 1));

            // Generate a random number of hemp seeds between 0 and 2
            int numHempSeeds = world.getRandom().nextInt(3); // Generates a number between 0 and 2 (inclusive)

            for (int i = 0; i < numHempSeeds; i++) {
                dropStack(world, pos, new ItemStack(BTWR_Items.HEMP_SEEDS, 1));
            }
        }
    }



    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (!getIsTopBlock(world, pos) && isValidLightSourceAbove(world, pos)
        /** && getWeedsGrowthLevel(world, pos) == 0 **/ )
        {
            Block blockBelowPlant = world.getBlockState(pos.down()).getBlock();

            if ( blockBelowPlant != null &&  ((BlockAdded)blockBelowPlant).isBlockHydratedForPlantGrowthOn(world, pos.down()) )
            {
                // only the base of the plants grows, and only does if its on hydrated soil

                if (state.get(AGE) < 7)
                {
                    float fChanceOfGrowth = getBaseGrowthChance() *
                            ((BlockAdded)blockBelowPlant).getPlantGrowthOnMultiplier(world, pos.down(), this);

                    if ( random.nextFloat() <= fChanceOfGrowth )
                    {
                        incrementGrowthLevel(world, pos, state);
                    }
                }
                else if ( world.isAir(pos.up()) )
                {
                    // check for growth of top block

                    float fChanceOfGrowth = (getBaseGrowthChance() / 4F ) *
                            ((BlockAdded)blockBelowPlant).getPlantGrowthOnMultiplier(world, pos.down(), this);

                    if ( random.nextFloat() <= fChanceOfGrowth )
                    {
                        // top of the plant
                        world.setBlockState( pos.up(), state.with(TOP, true),3);

                        ((BlockAdded)blockBelowPlant).notifyOfFullStagePlantGrowthOn(world, pos.down(), this);
                        //replace(blockBelowPlant, Blocks.FARMLAND.getDefaultState(), world, pos, 33 );
                    }
                }
            }
        }

    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return super.canPlaceAt(state, world, pos) ||
                ( world.getBlockState( pos.down() ) == this.withAge(7) && !getIsTopBlock(world, pos.down()) );
    }

    protected void incrementGrowthLevel(World world, BlockPos pos, BlockState state)
    {
        int iGrowthLevel = this.getAge(state) + 1;

        world.setBlockState(pos, state.with(AGE, iGrowthLevel));

        if (state.get(AGE) == 7)
        {
            Block blockBelow = world.getBlockState(pos.down()).getBlock();
            ((BlockAdded) blockBelow).notifyOfFullStagePlantGrowthOn(world, pos.down(), this);
        }
    }



    /**  Helper method to check if a block is TOP for a specific position more easily. **/
    protected boolean getIsTopBlock(BlockState state)
    {
        return state.get(TOP);
    }

    protected boolean getIsTopBlock(WorldView blockAccess, BlockPos pos)
    {
        return getIsTopBlock(blockAccess.getBlockState(pos));
    }

    protected BlockState setIsTopBlock(BlockState state, boolean bTop)
    {
        int age = state.get(AGE);

        if ( bTop )
        {
            age |= 8;
        }
        else
        {
            age &= (~8);
        }

        return state.with(AGE, age);
    }
    // ------------------- /

    private boolean isValidLightSourceAbove(World world, BlockPos pos)
    {
        BlockState lightBlockLit = BTWR_Blocks.LIGHTBLOCK.getDefaultState().with(LIT, true);
        boolean isAbove = world.getBlockState(pos.up(1)).isOf(lightBlockLit.getBlock());
        boolean isTwoAbove = world.getBlockState(pos.up(2)).isOf(lightBlockLit.getBlock());

        return (isAbove || isTwoAbove) || world.getLightLevel(pos) >= 15 ;
    }

    private float getBaseGrowthChance()
    {
        return 185.1F;
    }


    private void checkForAdjacentToSlow(World world, BlockPos pos, BlockState state, Random random)
    {
        int age = state.get(AGE);

        if (hasCropsAdjacentOnEitherSides(world, pos))
        {
            // Slow down growth by 40%
            if (random.nextFloat() < 0.4)
            {
                world.setBlockState(pos, state.with(AGE, age + 1));
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

}

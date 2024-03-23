package btwr.core.block.blocks;

import btwr.core.block.BTWR_Blocks;
import btwr.core.item.BTWR_Items;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
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

public class CropHemp extends CropBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 8);
    public static final BooleanProperty IS_TALL = BooleanProperty.of("tall"); // Add the IS_TALL property

    private final int firstStageTimer = 0;
    private final int topStageTimer = 0;


    public CropHemp(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), 0).with(IS_TALL, false));
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
    public int getMaxAge() {
        return 8;
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, IS_TALL);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState lowerPart = world.getBlockState(blockPos);
        if (lowerPart.getBlock() instanceof CropHemp) {
            return lowerPart.get(CropHemp.AGE) == 7;
        }
        return super.canPlaceAt(state, world, pos);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.addExhaustion(0.2F);

        if (stack.isOf(Items.SHEARS)) {
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

        if (world.isAir(pos.up())) {
            BlockState blockNorth = world.getBlockState(pos.north());
            BlockState blockSouth = world.getBlockState(pos.south());
            BlockState blockWest = world.getBlockState(pos.west());
            BlockState blockEast = world.getBlockState(pos.east());


            boolean isLightBlockAbove = world.getBlockState(pos.up(1)).isOf(BTWR_Blocks.LIGHTBLOCK);
            boolean isLightBlockTwoAbove = world.getBlockState(pos.up(2)).isOf(BTWR_Blocks.LIGHTBLOCK);

            // Check if the crop is attempting to grow (random chance)
            if ((world.getBaseLightLevel(pos, 0) >= 15) || isLightBlockAbove || isLightBlockTwoAbove)
            {
                int age = state.get(AGE);

                boolean hasCropsAdjacentOnEitherSides = (
                        (blockNorth.isOf(this) && blockSouth.isOf(this)) ||
                                (blockWest.isOf(this) && blockEast.isOf(this)));

                if (age < 7 && !state.get(IS_TALL))
                {
                    float f;
                    // Check if there are crops in the adjacent north or south rows
                    // Check if the crop is attempting to grow (random chance)
                    if (random.nextInt((int) (80 / (f = CropBlock.getAvailableMoisture(this, world, pos))) + 1) == 0)
                    {
                        // Check if there are crops in the adjacent north or south rows

                        if (!hasCropsAdjacentOnEitherSides)
                        {
                            // Delay block update of the crop block itself
                            world.setBlockState(pos, state.with(AGE, age + 1));
                        }
                        else
                        {
                            // Slow down growth by 40% if adjacent crops are present
                            if (random.nextFloat() < 0.4) {
                                world.setBlockState(pos, state.with(AGE, age + 1));
                            }
                        }
                    }
                }
                else if (age == 7 && world.isAir(pos.up()))
                {

                    if (random.nextInt(150) == 0)
                    {

                        if (!hasCropsAdjacentOnEitherSides)
                        {
                            // Delay block update of the block above
                            world.setBlockState(pos.up(),
                                    BTWR_Blocks.CROP_HEMP.getDefaultState()
                                    .with(AGE, 8)
                                    .with(IS_TALL, true));
                        }
                    }
                }
            }
        }
    }




}

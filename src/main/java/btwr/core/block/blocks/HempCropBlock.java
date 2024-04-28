package btwr.core.block.blocks;

import btwr.core.block.BTWR_Blocks;
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

public class HempCropBlock extends CropBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 8);
    public static final BooleanProperty TOP = BooleanProperty.of("top"); // Add the IS_TALL property


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

            // Generate a random number of hemp seeds between 0 and 2
            int numHempSeeds = world.getRandom().nextInt(3); // Generates a number between 0 and 2 (inclusive)

            for (int i = 0; i < numHempSeeds; i++) {
                dropStack(world, pos, new ItemStack(BTWR_Items.HEMP_SEEDS, 1));
            }
        }
    }





    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (world.isAir(pos.up()))
        {

            boolean isLightBlockAbove = world.getBlockState(pos.up(1)).isOf(BTWR_Blocks.LIGHTBLOCK);
            boolean isLightBlockTwoAbove = world.getBlockState(pos.up(2)).isOf(BTWR_Blocks.LIGHTBLOCK);

            // Check if the crop is attempting to grow (random chance)
            if ((world.getBaseLightLevel(pos, 0) >= 15) || isLightBlockAbove || isLightBlockTwoAbove)
            {
                int age = state.get(AGE);

                if (age < 7 && !state.get(TOP))
                {
                    float moisture = CropBlock.getAvailableMoisture(this, world, pos);

                    // Check if the crop is attempting to grow (random chance)
                    if (random.nextInt((int) (30 / moisture) + 1) == 0)
                    {
                        world.setBlockState(pos, state.with(AGE, age + 1));
                    }

                }
                else if (age == 7 && world.isAir(pos.up()))
                {
                    if (random.nextInt(30) == 0)
                    {
                        world.setBlockState(pos.up(), BTWR_Blocks.CROP_HEMP.getDefaultState().with(AGE, 8).with(TOP, true));
                    }
                }
            }
        }
    }




}

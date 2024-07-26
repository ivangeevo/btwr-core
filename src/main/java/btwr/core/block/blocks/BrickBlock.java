package btwr.core.block.blocks;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class BrickBlock extends Block
{

    public static final float BRICK_HEIGHT = (4F / 16F );
    public static final float BRICK_WIDTH = (6F / 16F );
    public static final float BRICK_HALF_WIDTH = (BRICK_WIDTH / 2F );
    public static final float BRICK_LENGTH = (12F / 16F );
    public static final float BRICK_HALF_LENGTH = (BRICK_LENGTH / 2F );
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public BrickBlock(Settings settings)
    {
        super(settings);
    }




    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add( FACING );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        Direction facing = state.get(FACING);

        if (facing.getAxis() == Direction.Axis.Z)
        {
            return VoxelShapes.cuboid(
                    (0.5F - BRICK_HALF_LENGTH),
                    0D,
                    (0.5F - BRICK_HALF_WIDTH),
                    (0.5F + BRICK_HALF_LENGTH),
                    BRICK_HEIGHT,
                    (0.5F + BRICK_HALF_WIDTH));
        }
        return VoxelShapes.cuboid(
                (0.5F - BRICK_HALF_WIDTH),
                0D,
                (0.5F - BRICK_HALF_LENGTH),
                (0.5F + BRICK_HALF_WIDTH),
                BRICK_HEIGHT,
                (0.5F + BRICK_HALF_LENGTH));

    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify)
    {
        if (!world.getBlockState(pos.down()).isSolidBlock(world, pos.down())) {
            dropAsItem(world, pos);
            world.removeBlock(pos, false);
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }


    @Override public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
    {
        return world.getBlockState(pos.down()).isSolidBlock(world, pos.down());
    }
    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    public void notifyOfFullStagePlantGrowthOn(World world, BlockPos pos, Block plantBlock) {}

    @Override
    public float getPlantGrowthOnMultiplier(World world, BlockPos pos, Block plantBlock) {
        return 0;
    }
    @Override
    public boolean isBlockHydratedForPlantGrowthOn(World world, BlockPos pos) {
        return false;
    }

    // -------- Class specific methods -------- //

    private void dropAsItem(World world, BlockPos pos)
    {
        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), Items.BRICK.getDefaultStack());
    }

    // -------- -------------------- -------- //

}

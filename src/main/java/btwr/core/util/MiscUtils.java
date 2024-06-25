package btwr.core.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class MiscUtils {

    public static final int TICKS_PER_SECOND = 20;

    public static final int TICKS_PER_MINUTE = (TICKS_PER_SECOND * 60 );
    public static final int TICKS_PER_GAME_DAY = (TICKS_PER_MINUTE * 20 );


    // Initiating Fluid states here for easy access.
    private static final FluidState regularFlowingState = Fluids.FLOWING_WATER.getFlowing(7,false);;
    private static final FluidState lowFlowingState =  Fluids.FLOWING_WATER.getFlowing(2,false);

    public static void placeNonPersistentWater(WorldAccess world, BlockPos pos) {

        world.setBlockState(pos, regularFlowingState.getBlockState(), 1);

        // spread the water around a bit so that it doesn't dissipate immediately

        // init
        BlockPos modifiedPos1 = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
        BlockPos modifiedPos2 = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
        BlockPos modifiedPos3 = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
        BlockPos modifiedPos4 = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);

        flowWaterIntoBlockIfPossible((World) world, modifiedPos1, lowFlowingState.getBlockState());
        flowWaterIntoBlockIfPossible((World) world, modifiedPos2, lowFlowingState.getBlockState());
        flowWaterIntoBlockIfPossible((World) world, modifiedPos3, lowFlowingState.getBlockState());
        flowWaterIntoBlockIfPossible((World) world, modifiedPos4, lowFlowingState.getBlockState());

    }


    static public void flowWaterIntoBlockIfPossible(World world, BlockPos pos, BlockState state)
    {
        if (canWaterDisplaceBlock(world, pos))
        {
            if (!(state.getBlock() == null))
            {
                onFluidFlowIntoBlock(world, pos, state);
            }

            world.setBlockState(pos, regularFlowingState.getBlockState());
        }
    }


    static public boolean canWaterDisplaceBlock(World world, BlockPos pos)
    {
        BlockState state = world.getBlockState(pos);

        if ( state.isOf(Blocks.WATER) )
        {
            return false;
        }

        if ( state.isOf(Blocks.LAVA) )
        {
            return false;
        }
        else
        {
            return state.getBlock() == null || !getPreventsFluidFlow( world, pos, Fluids.FLOWING_WATER.getFlowing().getDefaultState().getBlockState() );
        }
    }

    public static boolean getPreventsFluidFlow(World world, BlockPos pos, BlockState state) {
        return state.isIn(BlockTags.PORTALS) || state.blocksMovement();
    }

    public static void onFluidFlowIntoBlock(World world, BlockPos pos, BlockState state) {
        if (state.getHardness(world, pos) == 0f) {
            Block.dropStack(world, pos, world.getBlockState(pos).getBlock().asItem().getDefaultStack());

        }
    }
}

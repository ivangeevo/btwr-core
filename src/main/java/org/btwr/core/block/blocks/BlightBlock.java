package org.btwr.core.block.blocks;

import org.btwr.core.block.BTWR_Blocks;
import org.btwr.core.tag.BTWRTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;

public class BlightBlock extends Block {

    public static final IntProperty LEVEL = IntProperty.of("level", 0, 3);

    public BlightBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(LEVEL, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    protected IntProperty getLevelProperty() {
        return LEVEL;
    }

    public int getMaxLevel() {
        return 3;
    }

    public int getLevel(BlockState state) {
        return state.get(this.getLevelProperty());
    }

    public BlockState withLevel(int level) {
        return this.getDefaultState().with(this.getLevelProperty(), level);
    }

    /** Helper method to set Blight Roots more easily. **/
    private BlockState blightRootsWithLevel(int level) {
        return BTWR_Blocks.BLIGHT_ROOTS.getDefaultState().with(BlightRootsBlock.LEVEL, level);
    }

    public final boolean isMature(BlockState state) {
        return this.getLevel(state) >= this.getMaxLevel();
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Only tick if the blight doesn't change forms
        if (checkForBlightSurfaceConversions(world, pos, state)) return;

        if (this.getLevel(state) == 1) {
            blightKillLeaves(world, pos, random);
        } else if (this.getLevel(state) >= 2) {
            blightKillVinesAndLeaves(world, pos, random);
        }

        // Don't spread in The Nether
        if (!world.getDimensionEntry().matchesId(DimensionTypes.THE_NETHER_ID)) {
            checkForBlightSpread(world, pos, state, random);
            checkForBlightEvolution(world, pos, state, random);
        }

    }

    private void checkForBlightSpread(World world, BlockPos pos, BlockState state, Random random) {
        int blightLevel = this.getLevel(state);

        if (blightLevel == 0) {
            int randomX = pos.getX() + random.nextInt(3) - 1;
            int randomY = pos.getY() + random.nextInt(3) - 1;
            int randomZ = pos.getZ() + random.nextInt(3) - 1;
            BlockPos posToCheck = new BlockPos(randomX, randomY, randomZ);

            checkForSpreadToLocation(world, state, posToCheck);
        }
        else if (blightLevel == 1) {
            // Check for spread

            for (int tempCount = 0; tempCount < 2; tempCount++) {
                int randomX = pos.getX() + random.nextInt(3) - 1;
                int randomY = pos.getY() + random.nextInt(4) - 1;
                int randomZ = pos.getZ() + random.nextInt(3) - 1;
                BlockPos posToCheck = new BlockPos(randomX, randomY, randomZ);

                checkForSpreadToLocation(world, state, posToCheck);
            }
        }
        else {
            // Levels 2 & 3
            // Check for spread

            for (int tempCount = 0; tempCount < 4; tempCount++) {
                int randomX = pos.getX() + random.nextInt(3) - 1;
                int randomY = pos.getY() + random.nextInt(5) - 2;
                int randomZ = pos.getZ() + random.nextInt(3) - 1;
                BlockPos posToCheck = new BlockPos(randomX, randomY, randomZ);

                checkForSpreadToLocation(world, state, posToCheck);
            }

            // Grow roots

            //int iRootsSubtype = getRootsSubtypeForLevel(subtypeToBlightLevel[iBlockSubtype]);
            int rootsLevel = getRootsLevelForBlightLevel(blightLevel);

            if (world.getBlockState(pos.down()).isOf(Blocks.DIRT)) {
                world.setBlockState(pos.down(), this.blightRootsWithLevel(rootsLevel), Block.NOTIFY_ALL);
            }

            if (world.getBlockState(pos.up()).isOf(Blocks.DIRT)) {
                world.setBlockState(pos.up(), this.blightRootsWithLevel(rootsLevel), Block.NOTIFY_ALL);
            }
        }
    }

    private void checkForSpreadToLocation(World world, BlockState state, BlockPos targetPos) {
        BlockState spreadTargetState = world.getBlockState(targetPos);

        if (spreadTargetState.isAir()) return;

        int blightLevel = this.getLevel(state);

        if (spreadTargetState.isOf(this)) {
            // Evolve lower level blight

            // This is a general int check on whether this will spread a roots or normal blight block.
            int targetBlightLevel = getBlightLevelForSpread(spreadTargetState);

            if (targetBlightLevel < blightLevel && targetBlightLevel >= 0) {
                if (isSurfaceBlight(spreadTargetState)) {
                    if (blightLevel == 3) {
                        targetBlightLevel = 3;
                    }
                    else {
                        targetBlightLevel++;
                    }

                    world.setBlockState(targetPos, this.withLevel(targetBlightLevel), Block.NOTIFY_ALL);
                }
                else {
                    world.setBlockState(targetPos, this.blightRootsWithLevel(1), Block.NOTIFY_ALL);
                }
            }
        }
        else {

            BlockState targetState = world.getBlockState(targetPos);
            BlockState aboveState = world.getBlockState(targetPos.up());

            // Reconsider adding the hook getCanBlightSpreadToBlock() in BTWR:SL instead?
            if (targetState.isIn(BTWRTags.Blocks.BLIGHT_SPREADS_TO) || (targetState.isOf(Blocks.MYCELIUM) && this.getDefaultState().get(LEVEL) >= 2)) {
                if (blightLevel < 3) {
                    if (aboveState.getOpacity(world, targetPos.up()) <= 2) {
                        world.setBlockState(targetPos, this.withLevel(0), Block.NOTIFY_ALL);
                    }
                }
                else {
                    if (aboveState.getOpacity(world, targetPos.up()) <= 2) {
                        world.setBlockState(targetPos, this.withLevel(3), Block.NOTIFY_ALL);
                    }
                    else {
                        world.setBlockState(targetPos, this.blightRootsWithLevel(1), Block.NOTIFY_ALL);
                    }
                }
            }
        }
    }

    private void checkForBlightEvolution(World world, BlockPos pos, BlockState state, Random random) {
        if (!state.contains(LEVEL)) return;
        int blightLevel = this.getLevel(state);
        if (this.getLevel(state) == 0) {
            // Check for evolution

            Direction randomFacing = Direction.values()[random.nextInt(6)];
            BlockPos targetPos = pos.offset(randomFacing);

            if (isMatchingFluid(world, targetPos, Blocks.WATER)) {
                world.setBlockState(pos, this.withLevel(1), Block.NOTIFY_ALL);
            }
        }
        else if (this.getLevel(state) == 1) {
            // Check for evolution

            Direction randomFacing = Direction.values()[random.nextInt(6)];
            BlockPos targetPos = pos.offset(randomFacing);

            if (isMatchingFluid(world, targetPos, Blocks.LAVA)) {
                world.setBlockState(pos, state.with(LEVEL, 2));
            }
            // TODO: Possible wrong check in the 2nd part where we check blight roots and this.getLevel(state)
        }
        else if (this.getLevel(state) == 2 || (state.isOf(BTWR_Blocks.BLIGHT_ROOTS) && this.getLevel(state) == 1)) {
            // Check for evolution
            int randomX = pos.getX() + random.nextInt(7) - 3;
            int randomY = pos.getY() + random.nextInt(7) - 3;
            int randomZ = pos.getZ() + random.nextInt(7) - 3;

            BlockState targetState = world.getBlockState(new BlockPos(randomX, randomY, randomZ));

            if (targetState.isOf(Blocks.NETHER_PORTAL)) {
                if (this.getLevel(state) == 2) {
                    world.setBlockState(pos, this.withLevel(3));
                }
                else {
                    world.setBlockState(pos, this.blightRootsWithLevel(1));
                }
            }
        }
    }

    /**
     * Returns true if the blight has changed forms
     */
    private boolean checkForBlightSurfaceConversions(World world, BlockPos pos, BlockState state) {
        BlockState aboveState = world.getBlockState(pos.up());
        int blightLevel = this.getLevel(state);

        if (aboveState.getOpacity(world, pos.up()) > 2) {
            // Below surface

            if (blightLevel == 0) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState(), Block.NOTIFY_ALL);

                return true;
            }
            else if (blightLevel == 2) {
                world.setBlockState(pos, this.getDefaultState().with(LEVEL, 2), Block.NOTIFY_ALL);

                return true;
            }
            else if (blightLevel == 3) {
                world.setBlockState(pos, this.getDefaultState().with(LEVEL, 3), Block.NOTIFY_ALL);

                return true;
            }
        }
        else {
            // On the surface
            if ((state.isOf(BTWR_Blocks.BLIGHT_ROOTS) && blightLevel == 0)) {
                world.setBlockState(pos, this.getDefaultState().with(LEVEL, 2));

                return true;
            }

            if ((state.isOf(BTWR_Blocks.BLIGHT_ROOTS) && state.get(LEVEL) == 1)) {
                world.setBlockState(pos, this.getDefaultState().with(LEVEL, 3));

                return true;
            }
        }

        return false;
    }

    private void blightKillLeaves(World world, BlockPos pos, Random random) {
        for (int tempCount = 0; tempCount < 4; ++tempCount) {
            int randomX = pos.getX() + random.nextInt(3) - 1;
            int randomY = pos.getY() + random.nextInt(9);
            int randomZ = pos.getZ() + random.nextInt(3) - 1;

            BlockPos targetPos = new BlockPos(randomX, randomY, randomZ);
            BlockState targetState = world.getBlockState(targetPos);

            if (targetState.isIn(BlockTags.LEAVES)) {
                //world.setBlockWithNotify(randomX, randomY, randomZ, 0);
                // I wasn't sure about earlier notify checks, but here I'm pretty sure air should not notify neighbors
                world.setBlockState(targetPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
            }
        }
    }

    private void blightKillVinesAndLeaves(World world, BlockPos pos, Random random) {
        for (int tempCount = 0; tempCount < 4; ++tempCount) {
            int randomX = pos.getX() + random.nextInt(3) - 1;
            int randomY = pos.getY() + random.nextInt(9);
            int randomZ = pos.getY() + random.nextInt(3) - 1;

            BlockPos targetPos = new BlockPos(randomX, randomY, randomZ);
            BlockState targetState = world.getBlockState(targetPos);

            if (targetState.isIn(BlockTags.LEAVES) || targetState.isOf(Blocks.VINE)) {
                world.setBlockState(targetPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
            }
        }
    }

    public boolean isSurfaceBlight(BlockState state) {
        int blightLevel = state.get(LEVEL);
        if (!state.contains(LEVEL)) return false;
        return blightLevel >= 0 && blightLevel <= 3 && (state.isOf(BTWR_Blocks.BLIGHT_ROOTS) && state.get(LEVEL) != 1);
    }

    private int getRootsLevelForBlightLevel(int level) {
        return level >= 3 ? 1 : 0;
    }

    private boolean isMatchingFluid(World world, BlockPos pos, Block fluidBlock) {
        FluidState state = world.getFluidState(pos);
        if (fluidBlock == Blocks.WATER) {
            return state.isOf(Fluids.WATER) || state.isOf(Fluids.FLOWING_WATER);
        }
        else if (fluidBlock == Blocks.LAVA) {
            return state.isOf(Fluids.LAVA) || state.isOf(Fluids.FLOWING_LAVA);
        }
        return false;
    }

    public int getBlightLevelForSpread(BlockState state) {
        // Roots only spawn at Blight level 3
        if (state.isOf(BTWR_Blocks.BLIGHT_ROOTS)) {
            return 3;
        }

        return state.get(LEVEL);
    }

}
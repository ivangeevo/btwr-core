package btwr.core.block.blocks;

import btwr.core.block.BTWR_Blocks;
import btwr.core.tag.BTWRTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;

public class OGBlightBlock extends Block {

    public static final IntProperty AGE = Properties.AGE_3;


    public OGBlightBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(AGE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!checkForBlightSurfaceConversions(world, pos, state)) {

            if (state.get(AGE) == 1) {
                blightKillLeaves(world, pos, random);
            } else if (state.get(AGE) >= 2) {
                blightKillVinesAndLeaves(world, pos, random);
            }

            // Don't spread in The Nether
            if (!world.getDimensionEntry().matchesId(DimensionTypes.THE_NETHER_ID)) {
                checkForBlightSpread(world, pos, state, random);
                checkForBlightEvolution(world, pos, state, random);
            }
        }
    }

    private void checkForSpreadToLocation(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        if (!state.isAir()) {
            int blightLevel = state.get(AGE);

            if (state.getBlock() == this) {
                // Evolve lower level blight

                BlockState spreadTargetState = world.getBlockState(pos);
                int targetBlightLevel = spreadTargetState.get(AGE);

                if (targetBlightLevel < blightLevel && targetBlightLevel >= 0) {
                    if (isSurfaceBlight(spreadTargetState)) {
                        if (blightLevel == 3) {
                            targetBlightLevel = 3;
                        } else {
                            targetBlightLevel++;
                        }

                        //world.setBlockMetadataWithNotify(i, j, k, blightLevelToSubtype[targetBlightLevel]);
                        world.setBlockState(pos, spreadTargetState.with(AGE, targetBlightLevel), Block.NOTIFY_ALL);
                    } else {
                        //world.setBlockMetadataWithNotify(i, j, k, SUBTYPE_BLIGHT_ROOTS_LEVEL_3);
                        world.setBlockState(pos, BTWR_Blocks.BLIGHT_ROOTS.getDefaultState().with(AGE, 1), Block.NOTIFY_ALL);
                    }
                }
            } else {
                //Block targetBlock = Block.blocksList[iTargetBlockID];
                BlockState targetState = world.getBlockState(pos);
                BlockState aboveState = world.getBlockState(pos.up());

                // Reconsider adding the hook getCanBlightSpreadToBlock() in BTWR:SL instead?
                if (targetState.isIn(BTWRTags.Blocks.BLIGHT_SPREADS_TO) || (targetState.isOf(Blocks.MYCELIUM) && targetState.get(AGE) >= 2)) {
                    if (blightLevel < 3) {
                        if (aboveState.getOpacity(world, pos.up()) <= 2) {
                            //world.setBlockAndMetadataWithNotify(i, j, k, blockID, SUBTYPE_BLIGHT_LEVEL_0);
                            world.setBlockState(pos, this.getDefaultState().with(AGE, 0), Block.NOTIFY_ALL);
                        }
                    }
                    else {
                        if (aboveState.getOpacity(world, pos.up()) <= 2) {
                            //world.setBlockAndMetadataWithNotify(i, j, k, blockID, SUBTYPE_BLIGHT_LEVEL_3);
                            world.setBlockState(pos, this.getDefaultState().with(AGE, 3), Block.NOTIFY_ALL);
                        }
                        else {
                            //world.setBlockAndMetadataWithNotify(i, j, k, blockID, SUBTYPE_BLIGHT_ROOTS_LEVEL_3);
                            world.setBlockState(pos, BTWR_Blocks.BLIGHT_ROOTS.getDefaultState().with(AGE, 1), Block.NOTIFY_ALL);
                        }
                    }
                }
            }
        }
    }

    private void checkForBlightSpread(World world, BlockPos pos, BlockState state, Random random) {

        if (state.get(AGE) == 0) {
            int randomX = pos.getX() + random.nextInt(3) - 1;
            int randomY = pos.getY() + random.nextInt(3) - 1;
            int randomZ = pos.getZ() + random.nextInt(3) - 1;
            BlockPos posToCheck = new BlockPos(randomX, randomY, randomZ);

            checkForSpreadToLocation(world, posToCheck);
        }
        else if (state.get(AGE) == 1) {
            // Check for spread

            for (int tempCount = 0; tempCount < 2; tempCount++) {
                int randomX = pos.getX() + random.nextInt(3) - 1;
                int randomY = pos.getY() + random.nextInt(4) - 1;
                int randomZ = pos.getZ() + random.nextInt(3) - 1;
                BlockPos posToCheck = new BlockPos(randomX, randomY, randomZ);

                checkForSpreadToLocation(world, posToCheck);
            }
        } else {
            // levels 2 & 3

            // Check for spread

            for (int tempCount = 0; tempCount < 4; tempCount++) {
                int randomX = pos.getX() + random.nextInt(3) - 1;
                int randomY = pos.getY() + random.nextInt(5) - 2;
                int randomZ = pos.getZ() + random.nextInt(3) - 1;
                BlockPos posToCheck = new BlockPos(randomX, randomY, randomZ);

                checkForSpreadToLocation(world, posToCheck);
            }

            // Grow roots

            //int iRootsSubtype = getRootsSubtypeForLevel(subtypeToBlightLevel[iBlockSubtype]);
            int rootsLevel = getRootsAgeForBlightLevel(state.get(AGE));

            if (world.getBlockState(pos.down()).isOf(Blocks.DIRT)) {
                //world.setBlockAndMetadataWithNotify(i, j - 1, k, blockID, iRootsSubtype);
                world.setBlockState(pos.down(), BTWR_Blocks.BLIGHT_ROOTS.getDefaultState().with(AGE, rootsLevel), Block.NOTIFY_ALL);
            }

            if (world.getBlockState(pos.up()).isOf(Blocks.DIRT)) {
                //world.setBlockAndMetadataWithNotify(i, j + 1, k, blockID, iRootsSubtype);
                world.setBlockState(pos.up(), BTWR_Blocks.BLIGHT_ROOTS.getDefaultState().with(AGE, rootsLevel), Block.NOTIFY_ALL);
            }
        }
    }

    private void checkForBlightEvolution(World world, BlockPos pos, BlockState state, Random random) {
        if (state.get(AGE) == 0) {
            // Check for evolution

            Direction randomFacing = Direction.values()[random.nextInt(6)];
            BlockPos targetPos = pos.offset(randomFacing);

            if (isMatchingFluid(world, targetPos, Blocks.WATER)) {
                //world.setBlockMetadataWithNotify(i, j, k, SUBTYPE_BLIGHT_LEVEL_1);
                world.setBlockState(pos, state.with(AGE, 1));
            }
        } else if (state.get(AGE) == 1) {
            // Check for evolution

            Direction randomFacing = Direction.values()[random.nextInt(6)];
            BlockPos targetPos = pos.offset(randomFacing);

            if (isMatchingFluid(world, targetPos, Blocks.LAVA)) {
                //world.setBlockMetadataWithNotify(i, j, k, SUBTYPE_BLIGHT_LEVEL_2);
                world.setBlockState(pos, state.with(AGE, 2));
            }
        } else if (state.get(AGE) == 2 || (state.isOf(BTWR_Blocks.BLIGHT_ROOTS) && state.get(AGE) == 1)) {
            // Check for evolution

            int randomX = pos.getX() + random.nextInt(7) - 3;
            int randomY = pos.getY() + random.nextInt(7) - 3;
            int randomZ = pos.getZ() + random.nextInt(7) - 3;

            //int iTargetBlockID = world.getBlockId(randomX, randomY, randomZ);
            BlockState targetState = world.getBlockState(new BlockPos(randomX, randomY, randomZ));

            if (targetState.isOf(Blocks.NETHER_PORTAL)) {
                if (state.get(AGE) == 2) {
                    //world.setBlockMetadataWithNotify(i, j, k, SUBTYPE_BLIGHT_LEVEL_3);
                    world.setBlockState(pos, state.with(AGE, 3));
                } else {
                    //world.setBlockMetadataWithNotify(i, j, k, SUBTYPE_BLIGHT_ROOTS_LEVEL_3);
                    world.setBlockState(pos, BTWR_Blocks.BLIGHT_ROOTS.getDefaultState().with(AGE, 1));
                }
            }
        }
    }

    /**
     * Returns true if the blight has changed forms
     */
    private boolean checkForBlightSurfaceConversions(World world, BlockPos pos, BlockState state) {
        BlockState aboveState = world.getBlockState(pos.up());

        if (aboveState.getOpacity(world, pos.up()) > 2) {
            // Below surface

            if (state.get(AGE) == 0) {
                //world.setBlockWithNotify(pos, Block.dirt.blockID);
                world.setBlockState(pos, Blocks.DIRT.getDefaultState(), Block.NOTIFY_ALL);

                return true;
            } else if (state.get(AGE) == 2) {
                //world.setBlockAndMetadataWithNotify(pos, blockID, SUBTYPE_BLIGHT_ROOTS_LEVEL_2);
                world.setBlockState(pos, this.getDefaultState().with(AGE, 2), Block.NOTIFY_ALL);

                return true;
            } else if (state.get(AGE) == 3) {
                //world.setBlockAndMetadataWithNotify(pos, blockID, SUBTYPE_BLIGHT_ROOTS_LEVEL_3);
                world.setBlockState(pos, this.getDefaultState().with(AGE, 3), Block.NOTIFY_ALL);

                return true;
            }
        } else {
            // On surface

            if ((state.isOf(BTWR_Blocks.BLIGHT_ROOTS) && state.get(AGE) == 0)) {
                //world.setBlockAndMetadataWithNotify(pos, blockID, SUBTYPE_BLIGHT_LEVEL_2);
                world.setBlockState(pos, this.getDefaultState().with(AGE, 2));

                return true;
            }

            if ((state.isOf(BTWR_Blocks.BLIGHT_ROOTS) && state.get(AGE) == 1)) {
                //world.setBlockAndMetadataWithNotify(pos, blockID, SUBTYPE_BLIGHT_LEVEL_3);
                world.setBlockState(pos, this.getDefaultState().with(AGE, 3));

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
                //world.setBlockWithNotify(randomX, randomY, randomZ, 0);
                world.setBlockState(targetPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
            }
        }
    }

    public boolean isSurfaceBlight(BlockState state) {
        int blightLevel = state.get(AGE);
        return blightLevel >= 0 && blightLevel <= 3 && (state.isOf(BTWR_Blocks.BLIGHT_ROOTS) && state.get(AGE) != 1);
    }

    private int getRootsAgeForBlightLevel(int level) {
        return level >= 3 ? 1 : 0;
    }

    private boolean isMatchingFluid(World world, BlockPos pos, Block fluidBlock) {
        FluidState state = world.getFluidState(pos);
        if (fluidBlock == Blocks.WATER) {
            return state.isOf(Fluids.WATER) || state.isOf(Fluids.FLOWING_WATER);
        } else if (fluidBlock == Blocks.LAVA) {
            return state.isOf(Fluids.LAVA) || state.isOf(Fluids.FLOWING_LAVA);
        }
        return false;
    }


}

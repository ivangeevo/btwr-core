package org.btwr.core.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.btwr.core.entity.CementBlockEntity;
import org.jetbrains.annotations.Nullable;

public class CementBlock extends BlockWithEntity {
    public static final int MAX_CEMENT_SPREAD_DIST = 16;
    public static final int CEMENT_TICKS_TO_DRY = 12;
    public static final int CEMENT_TICKS_TO_PARTIALLY_DRY = 8;
    public static final BooleanProperty DRYING = BooleanProperty.of("drying");

    private final boolean[] tempSpreadToSideFlags = new boolean[4];
    private final int[] tempClosestDownslopeToSideDist = new int[4];

    public CementBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DRYING);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CementBlockEntity(pos, state);
    }

    // No block entity ticker needed - we use scheduled ticks like BTW

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL; // Custom renderer handles visuals
    }

    @Override
    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (world.getBlockState(pos.up()).getBlock() == this) {
            return VoxelShapes.fullCube();
        }
        return VoxelShapes.cuboid(0, 0, 0, 1, 0.5, 1);
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (world.getBlockState(pos).getBlock() == this) {
            world.scheduleBlockTick(pos, this, 20);
        }
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int cementDist = getCementSpreadDist(world, pos);

        // --- Spread distance propagation ---
        if (cementDist > 0) {
            int newCementDist = -100;

            newCementDist = checkForLesserSpreadDist(world, pos.west(), newCementDist);
            newCementDist = checkForLesserSpreadDist(world, pos.east(), newCementDist);
            newCementDist = checkForLesserSpreadDist(world, pos.north(), newCementDist);
            newCementDist = checkForLesserSpreadDist(world, pos.south(), newCementDist);
            newCementDist = checkForLesserSpreadDist(world, pos.up(), newCementDist);

            if (newCementDist < 0) {
                newCementDist = -1;
            } else {
                newCementDist += 1;
            }

            int cementDistUp = getCementSpreadDist(world, pos.up());

            if (cementDistUp >= 0 && cementDistUp < newCementDist) {
                newCementDist = cementDistUp + 1;
            }

            if (newCementDist > 0 && newCementDist < cementDist) {
                cementDist = newCementDist;
                setCementSpreadDist(world, pos, cementDist);
                setCementDryTime(world, pos, 0); // Fresh infusion resets dry time
            }
        }

        // --- Dry time update ---
        int dryTime = getCementDryTime(world, pos);

        dryTime++;

        int minDryTime = checkNeighboursCloserToSourceForMinDryTime(world, pos);

        if (dryTime > CEMENT_TICKS_TO_DRY) {
            // Fully dried -> become stone
            world.setBlockState(pos, Blocks.STONE.getDefaultState());
        } else {
            setCementDryTime(world, pos, dryTime);
            world.scheduleBlockTick(pos, this, 20);

            // --- Spreading ---
            if (isBlockOpenToSpread(world, pos.down())) {
                // Propagate downward first
                int targetDist = cementDist + 1;
                if (targetDist <= MAX_CEMENT_SPREAD_DIST) {
                    world.setBlockState(pos.down(), state);
                    setCementSpreadDist(world, pos.down(), targetDist);
                }
            } else if (cementDist >= 0 && (cementDist == 0 || blockBlocksFlow(world, pos.down()))) {
                // Propagate horizontally
                boolean[] spreadFlags = checkSideBlocksForPotentialSpread(world, pos);
                int spreadDist = cementDist + 1;

                if (spreadDist <= MAX_CEMENT_SPREAD_DIST) {
                    BlockPos[] sides = { pos.west(), pos.east(), pos.north(), pos.south() };
                    for (int i = 0; i < 4; i++) {
                        if (spreadFlags[i]) {
                            attemptToSpreadToBlock(world, sides[i], spreadDist);
                        }
                    }
                }
            }
        }
    }

    // --- Spread distance helpers ---

    public int getCementSpreadDist(BlockView world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() != this) return -1;
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof CementBlockEntity cbe) return cbe.getSpreadDist();
        return -1;
    }

    public void setCementSpreadDist(World world, BlockPos pos, int dist) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof CementBlockEntity cbe) {
            cbe.setSpreadDist(dist);
            world.updateNeighbors(pos, this);
        }
    }

    public boolean isCementSourceBlock(BlockView world, BlockPos pos) {
        return getCementSpreadDist(world, pos) == 0;
    }

    // --- Dry time helpers ---

    public int getCementDryTime(BlockView world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() != this) return 0;
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof CementBlockEntity cbe) return cbe.getDryTime();
        return 0;
    }

    public void setCementDryTime(World world, BlockPos pos, int time) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof CementBlockEntity cbe) {
            cbe.setDryTime(time);
            world.updateNeighbors(pos, this);

            // Swap texture via blockstate
            boolean partiallyDry = time >= CEMENT_TICKS_TO_PARTIALLY_DRY;
            BlockState current = world.getBlockState(pos);
            if (current.get(DRYING) != partiallyDry) {
                world.setBlockState(pos, current.with(DRYING, partiallyDry), Block.NOTIFY_LISTENERS); // flag 2: don't cause block updates
            }
        }
    }

    public boolean isCementPartiallyDry(BlockView world, BlockPos pos) {
        return getCementDryTime(world, pos) >= CEMENT_TICKS_TO_PARTIALLY_DRY;
    }

    // --- Neighbor dry time propagation ---

    private int checkNeighboursCloserToSourceForMinDryTime(World world, BlockPos pos) {
        int minDryTime = 1000;
        int distToSource = getCementSpreadDist(world, pos);

        minDryTime = getLesserDryTimeIfCloserToSource(world, pos.up(), distToSource, minDryTime);
        minDryTime = getLesserDryTimeIfCloserToSource(world, pos.east(), distToSource, minDryTime);
        minDryTime = getLesserDryTimeIfCloserToSource(world, pos.west(), distToSource, minDryTime);
        minDryTime = getLesserDryTimeIfCloserToSource(world, pos.south(), distToSource, minDryTime);
        minDryTime = getLesserDryTimeIfCloserToSource(world, pos.north(), distToSource, minDryTime);

        return minDryTime;
    }

    private int getLesserDryTimeIfCloserToSource(World world, BlockPos pos, int distToSource, int dryTime) {
        if (world.getBlockState(pos).getBlock() != this) return dryTime;

        int targetDist = getCementSpreadDist(world, pos);

        if (targetDist < distToSource) {
            int targetDryTime = getCementDryTime(world, pos);
            if (targetDryTime < dryTime) return targetDryTime;
        }
        return dryTime;
    }

    // --- Spreading helpers ---

    private void attemptToSpreadToBlock(World world, BlockPos pos, int newSpreadDist) {
        if (!isBlockOpenToSpread(world, pos)) return;

        BlockState existing = world.getBlockState(pos);
        if (!existing.isAir()) {
            Block.dropStacks(existing, world, pos);
        }

        world.setBlockState(pos, getDefaultState());
        setCementSpreadDist(world, pos, newSpreadDist);
    }

    private boolean[] checkSideBlocksForPotentialSpread(World world, BlockPos pos) {
        BlockPos[] sides = { pos.west(), pos.east(), pos.north(), pos.south() };

        for (int i = 0; i < 4; i++) {
            BlockPos side = sides[i];
            boolean blocksFlow = blockBlocksFlow(world, side);
            boolean isSourceCement = world.getBlockState(side).getBlock() == this && isCementSourceBlock(world, side);

            tempSpreadToSideFlags[i] = !blocksFlow && !isSourceCement;
        }
        return tempSpreadToSideFlags;
    }

    private boolean blockBlocksFlow(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        // Cement doesn't block itself; everything solid does
        return block != this && !state.isAir() && state.isSolidBlock(world, pos);
    }

    private boolean isBlockOpenToSpread(World world, BlockPos pos) {
        if (pos.getY() < world.getBottomY()) return false;
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == this) return false;
        return !blockBlocksFlow(world, pos);
    }

    protected int checkForLesserSpreadDist(BlockView world, BlockPos pos, int sourceDist) {
        int targetDist = getCementSpreadDist(world, pos);
        if (targetDist < 0) return sourceDist;
        if (sourceDist < 0 || targetDist < sourceDist) return targetDist;
        return sourceDist;
    }


}

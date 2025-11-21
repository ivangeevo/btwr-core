package org.btwr.core.util;

import org.btwr.core.block.BTWR_Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlightSpreader {
    private static final BlightSpreader INSTANCE = new BlightSpreader();

    private BlightSpreader() {}

    public static BlightSpreader getInstance() {
        return INSTANCE;
    }

    private static final int BLIGHT_SPREAD_RANGE = 3;

    private static final double BLIGHT_SPREAD_RANGE_SQ = ((double) BLIGHT_SPREAD_RANGE * (double) BLIGHT_SPREAD_RANGE);

    public void spreadBlightInArea(WitherSkullEntity entity) {
        int centerX = MathHelper.floor(entity.getPos().getX());
        int centerY = MathHelper.floor(entity.getPos().getY());
        int centerZ = MathHelper.floor(entity.getPos().getZ());

        for (int tempX = centerX - BLIGHT_SPREAD_RANGE; tempX <= centerX + BLIGHT_SPREAD_RANGE; tempX++) {
            for (int tempY = centerY - BLIGHT_SPREAD_RANGE; tempY <= centerY + BLIGHT_SPREAD_RANGE; tempY++) {
                for (int tempZ = centerZ - BLIGHT_SPREAD_RANGE; tempZ <= centerZ + BLIGHT_SPREAD_RANGE; tempZ++) {
                    double deltaX = tempX - centerX;
                    double deltaY = tempY - centerY;
                    double deltaZ = tempZ - centerZ;

                    double distSq = (deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ);

                    if (distSq <= BLIGHT_SPREAD_RANGE_SQ) {
                        BlockPos spreadPos = new BlockPos(tempX, tempY, tempZ);
                        attemptSpreadBlightToBlock(entity, spreadPos);
                    }
                }
            }
        }
    }

    private void attemptSpreadBlightToBlock(WitherSkullEntity entity, BlockPos pos) {
        World world = entity.getWorld();
        BlockState state = world.getBlockState(pos);

        if (state.isOf(Blocks.GRASS_BLOCK)) {
            BlockState aboveState = world.getBlockState(pos.up());

            if (aboveState.getOpacity(world, pos.up()) <= 2) {
                world.setBlockState(pos, BTWR_Blocks.BLIGHT.getDefaultState(), Block.NOTIFY_ALL);
            }
        }
    }
}
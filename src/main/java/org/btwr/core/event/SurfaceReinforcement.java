package org.btwr.core.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

import java.util.*;

public class SurfaceReinforcement {

    private static final int RADIUS = 64;
    private static final int MIN_DISTANCE = 24;
    private static final int MAX_DISTANCE = 48;
    private static final int COOLDOWN_TICKS = 20;

    private static final Map<UUID, Long> lastSpawnTick = new HashMap<>();

    public static void init() {
        ServerTickEvents.END_WORLD_TICK.register(SurfaceReinforcement::onWorldTick);
    }

    private static void onWorldTick(ServerWorld world) {
        if (world.getRegistryKey() != World.OVERWORLD) return;
        if (world.isDay()) return;

        long time = world.getTime();

        for (ServerPlayerEntity player : world.getPlayers()) {

            if (player.isSpectator()) continue;
            if (player.isCreative()) continue;
            if (!player.isAlive()) continue;
            if (player.getY() > 60) continue;

            UUID id = player.getUuid();

            if (lastSpawnTick.containsKey(id)) {
                if (time - lastSpawnTick.get(id) < COOLDOWN_TICKS) continue;
            }

            int currentSurfaceCount = countSurfaceHostiles(world, player);

            int target = calculateTarget(world, player);

            if (currentSurfaceCount >= target) continue;

            int attempts = calculateAttempts(world);

            for (int i = 0; i < attempts; i++) {
                trySpawnSurfaceMob(world, player);
            }

            lastSpawnTick.put(id, time);
        }
    }

    private static int countSurfaceHostiles(ServerWorld world, ServerPlayerEntity player) {
        Box box = new Box(
                player.getX() - RADIUS, player.getY() - 32, player.getZ() - RADIUS,
                player.getX() + RADIUS, player.getY() + 32, player.getZ() + RADIUS
        );

        return world.getEntitiesByClass(
                HostileEntity.class,
                box,
                entity ->
                        entity.isAlive()
                        && !entity.isRemoved()
                        //&& entity.getEntityWorld().isSkyVisible(entity.getBlockPos())
                        && isNearSurface(world, entity)
        ).size();
    }

    private static boolean isNearSurface(ServerWorld world, HostileEntity entity) {
        BlockPos pos = entity.getBlockPos();

        BlockPos surface = world.getTopPosition(
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                pos
        );

        return Math.abs(pos.getY() - surface.getY()) <= 4;
    }

    private static int calculateTarget(ServerWorld world, ServerPlayerEntity player) {
        float moon = world.getMoonSize(); // 0.0 - 1.0
        LocalDifficulty difficulty = world.getLocalDifficulty(player.getBlockPos());

        int base = 8;

        return base
                + (int)(moon * 6)
                + (int)difficulty.getClampedLocalDifficulty();
    }

    private static int calculateAttempts(ServerWorld world) {
        float moon = world.getMoonSize();
        return 2 + (int)(moon * 3);
    }

    private static void trySpawnSurfaceMob(ServerWorld world, ServerPlayerEntity player) {
        Random random = world.random;

        double angle = random.nextDouble() * Math.PI * 2.0;
        double distance = MIN_DISTANCE + random.nextDouble() * (MAX_DISTANCE - MIN_DISTANCE);

        int x = (int)(player.getX() + Math.cos(angle) * distance);
        int z = (int)(player.getZ() + Math.sin(angle) * distance);

        BlockPos top = world.getTopPosition(
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                new BlockPos(x, 0, z)
        );

        if (!world.isSkyVisible(top)) return;
        if (world.getLightLevel(top) > 0) return;
        if (!world.getBlockState(top.down()).isSolidBlock(world, top.down())) return;
        if (!world.getBlockState(top).isAir()) return;

        EntityType<? extends HostileEntity> type = chooseMob(world);

        HostileEntity entity = type.create(world);
        if (entity == null) return;

        entity.refreshPositionAndAngles(
                top.getX() + 0.5,
                top.getY(),
                top.getZ() + 0.5,
                random.nextFloat() * 360f,
                0
        );

        entity.initialize(
                world,
                world.getLocalDifficulty(top),
                SpawnReason.NATURAL,
                null
        );

        world.spawnEntity(entity);
    }

    private static EntityType<? extends HostileEntity> chooseMob(ServerWorld world) {
        float r = world.random.nextFloat();
        float moon = world.getMoonSize();

        if (r < 0.5f) return EntityType.ZOMBIE;
        if (r < 0.8f) return EntityType.SKELETON;

        if (moon > 0.8f && r > 0.9f) return EntityType.CREEPER;

        return EntityType.SPIDER;
    }

}
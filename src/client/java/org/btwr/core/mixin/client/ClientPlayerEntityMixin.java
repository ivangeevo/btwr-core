package org.btwr.core.mixin.client;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.btwr.core.util.EnderSpectaclesHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Unique private static final int PARTICLE_SCAN_RANGE = 10;

    @Shadow @Final protected MinecraftClient client;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        ClientPlayerEntity self = (ClientPlayerEntity)(Object)this;

        if (!client.options.getPerspective().isFirstPerson()) return;
        if (!EnderSpectaclesHelper.isWearingEnderSpectacles(self)) return;

        addTrueSightParticles(self);

        /**
         if (trueSight) {
         addSpawnChunksParticles(self);
         }
         **/
    }

    @Unique
    private void addTrueSightParticles(ClientPlayerEntity player) {
        World world = player.getWorld();
        BlockPos playerPos = player.getBlockPos();

        int particleSetting = client.options.getParticles().getValue().getId();

        int spawned = 0;
        int maxPerTick = 200;

        for (int x = -PARTICLE_SCAN_RANGE; x <= PARTICLE_SCAN_RANGE; x++) {
            for (int y = -PARTICLE_SCAN_RANGE; y <= PARTICLE_SCAN_RANGE; y++) {
                for (int z = -PARTICLE_SCAN_RANGE; z <= PARTICLE_SCAN_RANGE; z++) {

                    if (spawned >= maxPerTick) return;

                    BlockPos pos = playerPos.add(x, y, z);

                    if (!canMobsSpawnHere(world, pos)) continue;

                    // toned-down randomness
                    if (world.random.nextInt(20) > (3 - particleSetting)) continue;

                    BlockState below = world.getBlockState(pos.down());
                    double shapeMax = below.getCollisionShape(world, pos.down())
                            .getMax(Direction.Axis.Y);

                    double verticalOffset = shapeMax < 1.0 ? shapeMax : 0.0;

                    double px = pos.getX() + world.random.nextDouble();
                    double py = pos.getY() + verticalOffset + world.random.nextDouble() * 0.25;
                    double pz = pos.getZ() + world.random.nextDouble();

                    world.addParticle(
                            EntityEffectParticleEffect.create(ParticleTypes.ENTITY_EFFECT, 0, 0, 0f),
                            px,
                            py,
                            pz,
                            0, 0.0, 0
                    );

                    spawned++;
                }
            }
        }
    }

    /**
    // Uncomment when true sight potion is implemented
    @Unique
    private void addSpawnChunksParticles(ClientPlayerEntity player) {
        World world = player.getWorld();

        // Only active in the overworld
        if (!world.getDimensionEntry().matchesId(DimensionTypes.OVERWORLD_ID)) return;

        // TODO: Get spawn chunk coordinates from server via packet
        //  In BTW this was sent via setSpawnChunksVisualiazation() from EntityPlayerMP
        //  We will need a custom S2C packet to sync spawn pos to client

        BlockPos spawnPos = <received spawn pos>;
        int spawnChunkRange = 16; // vanilla spawn chunks are 16x16 around spawn

        // for each block on the boundary of the spawn chunks area:
        // world.addParticle(ParticleTypes.PORTAL, ...)
    }
    **/

    @Unique
    private boolean canMobsSpawnHere(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        FluidState fluid = world.getFluidState(pos);

        // 1. Block itself: not solid, not liquid
        if (!state.getCollisionShape(world, pos).isEmpty()) return false;
        if (!fluid.isEmpty()) return false;

        // 2. Block below
        BlockPos belowPos = pos.down();
        BlockState below = world.getBlockState(belowPos);

        if (below.isAir()) return false;

        // must have solid top surface
        if (!below.isSideSolidFullSquare(world, belowPos, Direction.UP)) return false;

        // no leaves
        if (below.isIn(BlockTags.LEAVES)) return false;

        // 3. Light check (modern hostile spawning)
        if (world.getLightLevel(LightType.BLOCK, pos) != 0) return false;

        // 4. Collision (redundant with first check, but keeps parity with BTW intent)
        return state.getCollisionShape(world, pos).isEmpty();
    }

}

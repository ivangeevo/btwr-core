package org.btwr.core.mixin.entity;

import net.minecraft.entity.LightningEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.btwr.core.data.saved.BTWRDifficultyData;
import org.btwr.core.difficulty.BTWRDifficulties;
import org.btwr.core.difficulty.impl.BTWRDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin {
    @Inject(method = "spawnFire", at = @At("HEAD"), cancellable = true)
    private void beforeSpawnFire(int spreadAttempts, CallbackInfo ci) {
        LightningEntity self = (LightningEntity)(Object)this;
        World world = self.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            BTWRDifficulty difficulty = BTWRDifficultyData.get(serverWorld.getServer()).getDifficulty();

            // Allows cancellation of lightnings from spawning fire based on the difficulty
            if (!difficulty.get(BTWRDifficulties.SHOULD_LIGHTNING_START_FIRES)) {
                ci.cancel();
            }
        }

    }
}

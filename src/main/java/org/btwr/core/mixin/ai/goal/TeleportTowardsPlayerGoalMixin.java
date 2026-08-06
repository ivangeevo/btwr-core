package org.btwr.core.mixin.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import org.btwr.core.data.saved.BTWRDifficultyData;
import org.btwr.core.difficulty.BTWRDifficulties;
import org.btwr.core.difficulty.impl.BTWRDifficulty;
import org.btwr.core.entity.util.EndermanMoveUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndermanEntity.TeleportTowardsPlayerGoal.class)
public abstract class TeleportTowardsPlayerGoalMixin  {

    @Shadow @Final private EndermanEntity enderman;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/EndermanEntity;teleportRandomly()Z"))
    private void beforeTeleportAway(CallbackInfo ci) {
        MinecraftServer server = enderman.getWorld().getServer();
        if (server == null) return;

        LivingEntity target = enderman.getTarget();

        if (target == null) return;

        BTWRDifficulty difficulty = BTWRDifficultyData.get(server).getDifficulty();

        if (difficulty.get(BTWRDifficulties.CAN_ENDERMAN_MOVE_PLAYERS)) {
            if (target instanceof PlayerEntity player) {
                EndermanMoveUtil.tryMovePlayer(enderman, player);
            }
        }
    }

}

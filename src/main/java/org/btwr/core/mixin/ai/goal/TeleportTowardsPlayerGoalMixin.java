package org.btwr.core.mixin.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.btwr.core.difficulty.ModDifficulties;
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
        World world = enderman.getWorld();
        LivingEntity target = enderman.getTarget();

        if (target == null) return;

        if (world.btwr$difficulty().get(ModDifficulties.CAN_ENDERMAN_MOVE_PLAYERS)) {
            if (target instanceof PlayerEntity player) {
                EndermanMoveUtil.tryMovePlayer(enderman, player);
            }
        }
    }

}

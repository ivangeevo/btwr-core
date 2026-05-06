package org.btwr.core.mixin.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import org.btwr.core.config.BTWRModConfig;
import org.btwr.core.util.HeadDropHandler;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "takeKnockback", at = @At("HEAD"), cancellable = true)
    private void modifyKnockback(double strength, double x, double z, CallbackInfo ci) {
        if (!BTWRModConfig.knockbackRestrictions.get()) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) (Object) this;

        if (livingEntity.getAttacker() instanceof PlayerEntity player) {
            ItemStack weaponStack = player.getMainHandStack();

            if (!weaponStack.isIn(BTWRConventionalTags.Items.DO_KNOCKBACK_ITEMS) && !player.isSprinting()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "drop", at = @At("TAIL"))
    private void checkForHeadDrop(ServerWorld world, DamageSource damageSource, CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;

        if (!self.isBaby() && world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            HeadDropHandler.checkForHeadDrop(self, damageSource);
        }
    }

}
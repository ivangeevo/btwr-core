package btwr.core.mixin.entity;

import btwr.btwrsl.tag.BTWRConventionalTags;
import btwr.core.BTWRMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "takeKnockback", at = @At("HEAD"), cancellable = true)
    private void modifyKnockback(double strength, double x, double z, CallbackInfo ci) {

        if (BTWRMod.getInstance().settings.isKnockbackRestrictionEnabled()) {

            LivingEntity livingEntity = (LivingEntity) (Object) this;

            // Load the configuration dynamically when the method is called
            if (livingEntity.getAttacker() instanceof PlayerEntity player) {
                ItemStack weaponStack = player.getMainHandStack();

                if (!weaponStack.isIn(BTWRConventionalTags.Items.DO_KNOCKBACK_ITEMS) && !player.isSprinting()) {
                    ci.cancel();
                }

            }
        }
    }

}

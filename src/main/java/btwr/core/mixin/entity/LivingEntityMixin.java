package btwr.core.mixin.entity;

import btwr.core.config.BTWRSettings;
import btwr.core.config.BTWRSettingsGUI;
import btwr.core.item.items.ClubItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {



    @Inject(method = "takeKnockback", at = @At("HEAD"), cancellable = true)
    private void modifyKnockback(double strength, double x, double z, CallbackInfo ci) {
        boolean isEnabled = BTWRSettingsGUI.getConfigValue(BTWRSettings.KNOCKBACK_RESTRICTION_KEY);

        if (!isEnabled) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) (Object) this;

        // Load the configuration dynamically when the method is called

        if (livingEntity.getAttacker() instanceof PlayerEntity player) {
            ItemStack weaponStack = player.getMainHandStack();

            if (isAppropriateWeapon(weaponStack)) {
                ci.cancel();
            }
        }
    }




    @Unique
    private boolean isAppropriateWeapon(ItemStack weaponStack)
    {
        return !(weaponStack.getItem() instanceof SwordItem) && !(weaponStack.getItem() instanceof ClubItem) &&
                !(weaponStack.getItem() instanceof BowItem) && !(weaponStack.getItem() instanceof CrossbowItem) &&
                !(weaponStack.getItem() instanceof TridentItem);
    }
}

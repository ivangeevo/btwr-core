package btwr.core.mixin.entity;

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

    // Cancels the knockback method if the incorrect weapon is used.
    @Inject(method = "takeKnockback", at = @At("HEAD"), cancellable = true)
    private void modifyKnockback(double strength, double x, double z, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        if (livingEntity.getAttacker() instanceof PlayerEntity player)
        {
            ItemStack weaponStack = player.getMainHandStack();

            if (isAppropriateWeapon(weaponStack))
            {
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

package btwr.core.mixin.entity;

import btwr.core.item.items.ClubItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    // Cancels the knockback if the incorrect weapon is used.

    @Inject(method = "takeKnockback", at = @At("HEAD"), cancellable = true)
    private void modifyKnockback(double strength, double x, double z, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        if (livingEntity.getAttacker() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) livingEntity.getAttacker();
            ItemStack weaponStack = player.getMainHandStack();



            if (!(weaponStack.getItem() instanceof SwordItem) && !(weaponStack.getItem() instanceof AxeItem) && !(weaponStack.getItem() instanceof ClubItem)) {

                ci.cancel();
            }
        }
    }
}

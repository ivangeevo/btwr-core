package btwr.core.mixin.entity;

import btwr.core.BTWRMod;
import btwr.core.config.BTWRSettings;
import btwr.core.config.SettingsGUI;
import btwr.core.item.items.ClubItem;
import btwr.core.tag.BTWRTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{



    @Inject(method = "takeKnockback", at = @At("HEAD"), cancellable = true)
    private void modifyKnockback(double strength, double x, double z, CallbackInfo ci)
    {

        if (BTWRMod.getInstance().settings.isKnockbackRestrictionEnabled())
        {

            LivingEntity livingEntity = (LivingEntity) (Object) this;

            // Load the configuration dynamically when the method is called
            if (livingEntity.getAttacker() instanceof PlayerEntity player)
            {
                ItemStack weaponStack = player.getMainHandStack();

                if (!isAppropriateWeapon(weaponStack))
                {
                    ci.cancel();
                }

            }
        }
    }


    @Unique
    private boolean isAppropriateWeapon(ItemStack weaponStack)
    {
        return weaponStack.isIn(BTWRTags.Items.DO_KNOCKBACK_ITEMS);
    }
}

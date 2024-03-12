package btwr.core.mixin.entity;

import btwr.core.config.BTWRSettings;
import btwr.core.config.BTWRSettingsGUI;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin
{

    // Makes baby zombies not able to spawn.
    //@Inject(method = "shouldBeBaby", at = @At("HEAD"), cancellable = true)
    private static void setShouldBeBaby(Random random, CallbackInfoReturnable<Boolean> cir)
    {

        if (BTWRSettingsGUI.getConfigValue(BTWRSettings.DISABLE_BABY_ZOMBIES_KEY))
        {
            cir.setReturnValue(false);
        }

    }
}


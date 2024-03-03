package btwr.core.mixin.entity;

import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {

    // Makes baby zombies not able to spawn.
    //@Inject(method = "shouldBeBaby", at = @At("HEAD"), cancellable = true)
    private static void setShouldBeBaby(Random random, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}

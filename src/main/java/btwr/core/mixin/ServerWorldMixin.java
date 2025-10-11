package btwr.core.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin
{

    // did some test; trying to make mobs get ticked even when far away
    //@Inject(method = "tickEntity", at = @At("HEAD"))
    private void forceEntityTick(Entity entity, CallbackInfo ci) {
        if (entity instanceof MobEntity) {
            entity.tick(); // Forces ticking, even if normally skipped
        }
    }
}


package btwr.core.mixin.entity;

import btwr.core.BTWRMod;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.SpawnGroup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpawnGroup.class)
public abstract class SpawnGroupMixin {

    @Shadow @Final private String name;

    @ModifyReturnValue(method = "getCapacity", at = @At("RETURN"))
    private int modifyCapacity(int maxInstancesPerChunk) {
        if (!BTWRMod.getInstance().settings.shouldIncreaseMaxMobCapacity())
            return maxInstancesPerChunk;

        if (this.name.equals(SpawnGroup.MONSTER.getName()))
            return 90;

        return maxInstancesPerChunk;
    }
}
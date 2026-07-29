package org.btwr.core.mixin.entity;

import org.btwr.core.config.BTWRModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {

    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    // Makes creeper explosion get calculated from the eyes instead of its feet
    @ModifyArg(method = "explode", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"), index = 2)
    private double injected(double y) {
        boolean shouldModify = BTWRModConfig.changedCreeperExplosionPos.get();
        return shouldModify ? this.getEyeY() : y;
    }

}
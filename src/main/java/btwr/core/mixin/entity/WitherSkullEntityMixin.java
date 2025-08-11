package btwr.core.mixin.entity;

import btwr.core.block.BTWR_Blocks;
import btwr.core.util.BlightSpreader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkullEntity.class)
public abstract class WitherSkullEntityMixin extends ExplosiveProjectileEntity {



    protected WitherSkullEntityMixin(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract boolean isCharged();

    @Inject(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"))
    private void setBlightOnCollision(HitResult hitResult, CallbackInfo ci) {
        if (!isCharged()) {
            BlightSpreader.getInstance().spreadBlightInArea((WitherSkullEntity)(Object)this);
        }
    }

}

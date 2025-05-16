package btwr.core.mixin.entity;

import btwr.core.BTWRMod;
import btwr.core.entity.CreeperModificationManager;
import btwr.core.entity.ai.goal.CreeperSwellBehavior;
import btwr.core.entity.interfaces.CreeperEntityAdded;
import btwr.core.item.BTWR_Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity implements CreeperEntityAdded {

    @Shadow private int fuseTime;

    @Shadow public abstract boolean isIgnited();

    @Shadow private int lastFuseTime;

    @Shadow private int currentFuseTime;

    @Shadow public abstract void setFuseSpeed(int fuseSpeed);

    @Shadow public abstract int getFuseSpeed();

    @Shadow protected abstract void explode();

    @Unique private boolean determinedToExplode = false;

    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyArg(method = "initGoals", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
            ordinal = 1), index = 1)
    private Goal injected(Goal goal) {
        return new CreeperSwellBehavior((CreeperEntity)(Object)this);
    }

    // Copying, modifying and cancelling the original tick logic with our custom conditions added.
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void injectedTick(CallbackInfo ci) {
        this.setCustomTickLogic();
        ci.cancel();
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void injectedInitDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(NEUTERED, false);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void injectedWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (this.dataTracker.get(NEUTERED)) {
            nbt.putBoolean("neutered", true);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void injectedReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.getBoolean("neutered")) {
            this.setNeutered();
        }
    }

    // Add a drop on death with a chance, instead of modifying the loot table.
    @Inject(method = "dropEquipment", at = @At("TAIL"))
    private void onDropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        boolean shouldDrop = random.nextInt(3) == 0 && !isNeutered();
        if (!shouldDrop) return;
        this.dropItem(BTWR_Items.CREEPER_OYSTERS, 1);
    }

    // Makes creeper explosion get calculated from the eyes instead of its feet
    @ModifyArg(method = "explode", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"), index = 2)
    private double injected(double y) {
        boolean shouldModify = BTWRMod.getInstance().settings.shouldChangeCreeperExplosionPos();
        return shouldModify ? this.getEyeY() : y;
    }

    // Creeper makes a hiss sound sometimes if neutered - he crying :(
    @Override
    public void playAmbientSound() {
        if (!isNeutered()) return;
        this.playSound(this.getAmbientSound(), 0.25F, this.getSoundPitch() + 0.25F);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    @Override public boolean isNeutered() {
        return this.dataTracker.get(NEUTERED);
        //return Boolean.TRUE.equals(this.getAttached(CreeperModificationManager.NEUTERED));
    }

    @Override
    public void setNeutered() {
        if (!canBeNeutered()) return;
        this.dataTracker.set(NEUTERED, true);
        //this.setAttached(CreeperModificationManager.NEUTERED, value);
    }

    @Override public boolean getIsDeterminedToExplode() {
        return determinedToExplode;
    }

    // Only vanilla creeper types can be neutered
    @Override
    public boolean canBeNeutered() {
      return this.getType() == EntityType.CREEPER;
    }

    @Unique
    private void setCustomTickLogic() {
        if (this.isAlive()) {
            this.lastFuseTime = this.currentFuseTime;
            if (this.isIgnited()) {
                this.setFuseSpeed(1);
            }

            if (this.getFuseSpeed() > 0 && this.currentFuseTime == 0 && !isNeutered()) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0f, 0.5f);
                this.emitGameEvent(GameEvent.PRIME_FUSE);
            }

            this.currentFuseTime += this.getFuseSpeed();

            if (this.currentFuseTime < 0) {
                this.currentFuseTime = 0;
            }

            // Check if the creeper is not neutered
            if (!isNeutered()) {
                if (this.currentFuseTime >= this.fuseTime) {
                    this.currentFuseTime = this.fuseTime;
                    this.explode();
                }
            } else {
                // Reset fuse time when neutered
                this.currentFuseTime = 0;
            }
        }
        super.tick();
    }
}



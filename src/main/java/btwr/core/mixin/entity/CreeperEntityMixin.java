package btwr.core.mixin.entity;

import btwr.core.entity.ai.goal.CreeperSwellBehavior;
import btwr.core.entity.interfaces.CreeperEntityAdded;
import btwr.core.item.BTWR_Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity implements CreeperEntityAdded {
    @Shadow
    @Final
    private static TrackedData<Boolean> CHARGED;
    @Shadow
    private int fuseTime;
    @Shadow
    private int explosionRadius;

    @Shadow
    public abstract boolean isIgnited();

    @Shadow public abstract void ignite();

    @Shadow public abstract boolean shouldRenderOverlay();

    @Shadow protected abstract void spawnEffectsCloud();

    @Shadow private int lastFuseTime;

    @Shadow private int currentFuseTime;

    @Shadow public abstract void setFuseSpeed(int fuseSpeed);

    @Shadow public abstract int getFuseSpeed();

    @Shadow protected abstract void explode();

    private boolean determinedToExplode = false;

    // TODO: Fix into a better injection method than an override

    /**
    @Override
    public void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperSwellBehavior((CreeperEntity)(Object)this));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0f, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0f, 1.0, 1.2));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));

    }
    **/

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    private void injectedInitGoals(CallbackInfo ci) {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperSwellBehavior((CreeperEntity)(Object)this));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0f, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0f, 1.0, 1.2));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
        ci.cancel();
    }
    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

        @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
        private void injectedTick(CallbackInfo ci) {
            if (this.isAlive()) {
                int i;
                this.lastFuseTime = this.currentFuseTime;
                if (this.isIgnited()) {
                    this.setFuseSpeed(1);
                }
                    if ((i = this.getFuseSpeed()) > 0 && this.currentFuseTime == 0 && !this.isNeutered()) {
                        this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0f, 0.5f);
                        this.emitGameEvent(GameEvent.PRIME_FUSE);
                    }

                    this.currentFuseTime += i;

                    if (this.currentFuseTime < 0) {
                        this.currentFuseTime = 0;
                    }

                // Check if the creeper is not neutered
                if (!this.isNeutered()) {
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
            ci.cancel();
        }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void injectedInteractMob(PlayerEntity player2, Hand hand, CallbackInfoReturnable<ActionResult> cir) {

        ItemStack itemStack = player2.getStackInHand(hand);
        ItemStack creeperOysters = new ItemStack(BTWR_Items.CREEPER_OYSTERS);

        if (itemStack.isOf(Items.SHEARS) && !this.isNeutered()) {
            player2.getWorld().playSound(null, player2.getBlockPos(), SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            player2.getWorld().playSound(null ,player2.getBlockPos(),SoundEvents.ENTITY_SLIME_ATTACK,SoundCategory.HOSTILE, 1.0F, (player2.getWorld().random.nextFloat() - player2.getWorld().random.nextFloat()) * 0.1F + 0.7F);

            if(!this.world.isClient) {
                this.neuter();

                ParticleEffect particleEffect = ParticleTypes.ITEM_SNOWBALL;
                for (int i = 0; i < 50; i++) {
                    double particleX = this.getX() + world.random.nextDouble() - 0.5D;
                    double particleY = this.getY() - 0.45D + world.random.nextDouble() * 0.5D;  // Adjusted the Y coordinate
                    double particleZ = this.getZ() + world.random.nextDouble() - 0.5D;

                    double particleVelX = (world.random.nextDouble() - 0.5D) * 0.5D;
                    double particleVelY = world.random.nextDouble() * 0.25D;
                    double particleVelZ = (world.random.nextDouble() - 0.5D) * 0.5D;

                    ((ServerWorld) this.world).spawnParticles(particleEffect, particleX, particleY, particleZ, 1, particleVelX, particleVelY, particleVelZ, 0.0);
                }

                itemStack.damage(10, player2, player -> player.sendToolBreakStatus(hand));
                this.dropStack(creeperOysters);

            }
        }

        if (itemStack.isOf(Items.FLINT_AND_STEEL)) {
            this.world.playSound(player2, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0f, this.random.nextFloat() * 0.4f + 0.8f);
            if (!this.world.isClient) {
                this.ignite();
                itemStack.damage(1, player2, player -> player.sendToolBreakStatus(hand));
            }
            cir.setReturnValue(ActionResult.success(this.world.isClient));
        }

        cir.setReturnValue(super.interactMob(player2, hand));

    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void injectedInitDataTracker(CallbackInfo ci) {
        this.getDataTracker().startTracking(NEUTERED, false);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"), cancellable = true)
    private void injectedWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {

        super.writeCustomDataToNbt(nbt);
        if (this.dataTracker.get(CHARGED).booleanValue()) {
            nbt.putBoolean("powered", true);
        }

        if (this.dataTracker.get(NEUTERED).booleanValue()) {
            nbt.putBoolean("neutered", true);
        }

        nbt.putShort("Fuse", (short) this.fuseTime);
        nbt.putByte("ExplosionRadius", (byte) this.explosionRadius);
        nbt.putBoolean("ignited", this.isIgnited());

    ci.cancel();
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"), cancellable = true)
    private void injectedReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {

        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(CHARGED, nbt.getBoolean("powered"));
        if (nbt.contains("Fuse", NbtElement.NUMBER_TYPE)) {
            this.fuseTime = nbt.getShort("Fuse");
        }
        if (nbt.contains("ExplosionRadius", NbtElement.NUMBER_TYPE)) {
            this.explosionRadius = nbt.getByte("ExplosionRadius");
        }
        if (nbt.getBoolean("ignited")) {
            this.ignite();
        }

        if (nbt.getBoolean("neutered")) {
            this.neuter();

        }

        ci.cancel();

    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (isNeutered()) {
            SoundEvent sound = SoundEvents.ENTITY_CREEPER_HURT;

            if (sound != null) {
                this.playSound(sound, 0.25F, this.getSoundPitch() + 0.25F);
            }
        }
            return super.getAmbientSound();
    }

    @Unique public void neuter() {
        this.dataTracker.set(NEUTERED, true);
    }
    @Override public boolean isNeutered() {
        return this.dataTracker.get(NEUTERED);
    }

    @Override public boolean getIsDeterminedToExplode() {
        return determinedToExplode;
    }
}



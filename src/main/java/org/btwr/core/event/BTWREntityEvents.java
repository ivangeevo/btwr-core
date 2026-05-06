package org.btwr.core.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.btwr.core.data.BTWRDataAttachments;
import org.btwr.core.item.BTWR_Items;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import org.btwr.core.sound.BTWRSoundEvents;
import org.btwr.core.tag.BTWRTags;
import org.jetbrains.annotations.Nullable;

public class BTWREntityEvents {

    public static void register() {
        UseEntityCallback.EVENT.register(BTWREntityEvents::tryShearingCreeper);
    }

    /** Registers an event to modify creepers when used on with shears **/
    private static ActionResult tryShearingCreeper(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        if (entity instanceof CreeperEntity creeperEntity) {
            var creeperData = creeperEntity.getAttached(BTWRDataAttachments.CREEPER_DATA);

            if (creeperData == null) return ActionResult.PASS;

            if (creeperData.canNeuter() && entity.getType().isIn(BTWRTags.EntityTypes.NEUTERABLE_CREEPERS)) {
                ItemStack handStack = player.getStackInHand(hand);

                if (handStack.getItem() instanceof ShearsItem && !creeperData.isNeutered()) {
                    float pitch2 = (player.getWorld().random.nextFloat() - player.getWorld().random.nextFloat()) * 0.1F + 0.7F;
                    player.getWorld().playSound(null, player.getBlockPos(), BTWRSoundEvents.CREEPER_SHEARED,
                            SoundCategory.NEUTRAL, 1.0F, 1.0F
                    );
                    player.getWorld().playSound(null, player.getBlockPos(), BTWRSoundEvents.CREEPER_SHEARED_LAYER,
                            SoundCategory.HOSTILE, 1.0F, pitch2
                    );

                    if(!creeperEntity.getWorld().isClient) {
                        creeperData.setNeutered(true);
                        creeperData.markDirty();

                        ParticleEffect particleEffect = ParticleTypes.ITEM_SNOWBALL;
                        for (int i = 0; i < 50; i++) {
                            double particleX = creeperEntity.getX() + world.random.nextDouble() - 0.5D;
                            double particleY = creeperEntity.getY() - 0.45D + world.random.nextDouble() * 0.5D;  // Adjusted the Y coordinate
                            double particleZ = creeperEntity.getZ() + world.random.nextDouble() - 0.5D;

                            double particleVelX = (world.random.nextDouble() - 0.5D) * 0.5D;
                            double particleVelY = world.random.nextDouble() * 0.25D;
                            double particleVelZ = (world.random.nextDouble() - 0.5D) * 0.5D;

                            ((ServerWorld) world).spawnParticles(particleEffect, particleX, particleY, particleZ, 1, particleVelX, particleVelY, particleVelZ, 0.0);
                        }

                        handStack.damage(10, player, LivingEntity.getSlotForHand(hand));
                        creeperEntity.dropStack(new ItemStack(BTWR_Items.CREEPER_OYSTERS));

                        return ActionResult.SUCCESS;
                    }
                }
            }
        }

        return ActionResult.PASS;
    }

}
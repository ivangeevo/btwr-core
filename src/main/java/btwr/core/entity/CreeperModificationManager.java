package btwr.core.entity;

import btwr.core.item.BTWR_Items;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

public class CreeperModificationManager {

    //private static final Identifier NEUTERED_ID = Identifier.of(BTWRMod.MOD_ID, "neutered");
    //public static final AttachmentType<Boolean> NEUTERED = AttachmentRegistry.createPersistent(NEUTERED_ID, Codec.BOOL);

    private static CreeperModificationManager INSTANCE;

    private CreeperModificationManager() {}

    public static CreeperModificationManager getInstance() {
        return INSTANCE;
    }

    /** Registers the "interact" event to modify creepers when used on with shears **/
    public static void registerUseEvent() {
        UseEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {

            if (!(entity instanceof CreeperEntity creeperEntity) || !creeperEntity.canBeNeutered()) {
                return ActionResult.PASS;
            }

            ItemStack handStack = player.getStackInHand(hand);

            if (handStack.getItem() instanceof ShearsItem && !creeperEntity.isNeutered()) {
                float pitch2 = (player.getWorld().random.nextFloat() - player.getWorld().random.nextFloat()) * 0.1F + 0.7F;
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_SHEEP_SHEAR,
                        SoundCategory.NEUTRAL, 1.0F, 1.0F);
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_SLIME_ATTACK,
                        SoundCategory.HOSTILE, 1.0F, pitch2);

                if(!creeperEntity.getWorld().isClient) {
                    creeperEntity.setNeutered();

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
                }
            }

            return ActionResult.PASS;
        }));

    }
}

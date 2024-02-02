package btwr.core.client.fx;


import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class BTWREffectManager {

    public static final int CREEPER_SNIP_EFFECT_ID = 2258;


    public static void initEffects() {

        EffectHandler.effectMap.put(CREEPER_SNIP_EFFECT_ID, (mcInstance, world, player, pos, data) -> {
            world.playSound(null,pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            world.playSound(null ,pos,SoundEvents.ENTITY_SLIME_ATTACK,SoundCategory.HOSTILE, 1.0F, (world.random.nextFloat() - world.random.nextFloat()) * 0.1F + 0.7F);

            for (int i = 0; i < 50; i++) {
                double particleX = pos.getX() + world.random.nextDouble() - 0.5D;
                double particleY = pos.getY() - 0.45D;
                double particleZ = pos.getZ() + world.random.nextDouble() - 0.5D;

                double particleVelX = (world.random.nextDouble() - 0.5D) * 0.5D;
                double particleVelY = world.random.nextDouble() * 0.25D;
                double particleVelZ = (world.random.nextDouble() - 0.5D) * 0.5D;

                world.addParticle(ParticleTypes.ITEM_SNOWBALL, particleX, particleY, particleZ, particleVelX, particleVelY, particleVelZ);
            }
        });


    }
}

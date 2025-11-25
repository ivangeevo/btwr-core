package org.btwr.core.config;

import com.google.common.reflect.Reflection;
import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;
import net.minecraft.text.Text;
import org.btwr.core.BTWRMod;

import java.util.function.Supplier;

public class BTWRModConfig {

    public static void register() {
        Reflection.initialize(Settings.class);
    }

    public static class Settings {
        public static final Supplier<Boolean> knockbackRestrictions;
        public static final Supplier<Boolean> spawnBabyZombies;
        public static final Supplier<Boolean> spawnMobsOnWood;
        public static final Supplier<Boolean> increasedMonsterSpawnsPerChunk;
        public static final Supplier<Boolean> changedCreeperExplosionPos;

        static {
            // construct a new config builder
            IConfigBuilder builder = ConfigBuilders.newTomlConfig(BTWRMod.MOD_ID, "btwr_core", true);

            // Boolean checks
            knockbackRestrictions = builder
                    .comment("Disables knockback if not using a suitable weapon")
                    .define("knockbackRestrictions", true);
            spawnBabyZombies = builder
                    .comment("Can baby zombies spawn naturally?")
                    .define("spawnBabyZombies", false);
            spawnMobsOnWood = builder
                    .comment("Can mobs spawn on wooden blocks?")
                    .define("spawnMobsOnWood", false);
            increasedMonsterSpawnsPerChunk = builder
                    .comment("Slightly increase the amount of mobs that can spawn per chunk")
                    .define("increasedMonsterSpawnsPerChunk", true);
            changedCreeperExplosionPos = builder
                    .comment("Changes the location of creeper's explosion origin to\\n be calculated from their eyes instead of their feet")
                    .define("changedCreeperExplosionPos", true);

            /**
             // values can be put into categories
             builder.push("client").categoryComment("this is a comment for the 'client' category");
             // a value in the 'client' category
             exampleClientCategoryValue = builder.comment("this value is in the 'client' category").define("clientValue", true);
             // end the 'client' category
             builder.pop();
            **/

            // build the config
            builder.build();
        }
    }
}
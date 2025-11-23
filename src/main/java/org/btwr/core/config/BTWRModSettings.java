package org.btwr.core.config;

import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;

import java.util.function.Supplier;

public class BTWRModSettings {
    public static Supplier<Boolean> knockbackRestrictions;
    public static Supplier<Boolean> spawnBabyZombies;
    public static Supplier<Boolean> spawnMobsOnWood;
    public static Supplier<Boolean> increasedMonsterSpawnsPerChunk;
    public static Supplier<Boolean> changedCreeperExplosionPos;

    static {
        // construct a new config builder
        IConfigBuilder builder = ConfigBuilders.newTomlConfig("btwr", "btwr/core", false);

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
                .comment("Slightly increase the number of mobs that can spawn per chunk")
                .define("increasedMonsterSpawnsPerChunk", true);
        changedCreeperExplosionPos = builder
                .comment("Changed Creeper Explosion Origin")
                .define("changedCreeperExplosionPos", true);

        // build the config
        builder.build();
    }
}
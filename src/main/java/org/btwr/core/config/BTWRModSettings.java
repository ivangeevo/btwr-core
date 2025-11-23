package org.btwr.core.config;

import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;
import net.minecraft.text.Text;

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
                .comment(String.valueOf(Text.translatable("config.btwr.knockbackRestrictions")))
                .define("knockbackRestrictions", true);
        spawnBabyZombies = builder
                .comment(String.valueOf(Text.translatable("config.btwr.spawnBabyZombies")))
                .define("spawnBabyZombies", false);
        spawnMobsOnWood = builder
                .comment(String.valueOf(Text.translatable("config.btwr.spawnMobsOnWood")))
                .define("spawnMobsOnWood", false);
        increasedMonsterSpawnsPerChunk = builder
                .comment(String.valueOf(Text.translatable("config.btwr.increasedMonsterSpawnsPerChunk")))
                .define("increasedMonsterSpawnsPerChunk", true);
        changedCreeperExplosionPos = builder
                .comment(String.valueOf(Text.translatable("config.btwr.changedCreeperExplosionPos")))
                .define("changedCreeperExplosionPos", true);

        // build the config
        builder.build();
    }
}
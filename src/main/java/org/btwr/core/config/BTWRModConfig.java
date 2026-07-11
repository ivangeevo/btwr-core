package org.btwr.core.config;

import org.btwr.core.BTWRMod;
import org.btwr.shared_library.api.config.ConfigBuilder;
import org.btwr.shared_library.api.config.ConfigGroup;
import org.btwr.shared_library.api.config.ConfigSetting;
import org.btwr.shared_library.api.config.TomlConfigManager;

public class BTWRModConfig {

    /** Replace with your MOD_ID for easy adaptation **/
    private static final String MOD_ID = BTWRMod.MOD_ID;

    public static final ConfigGroup CONFIG;

    /** Call this method in your mod initializer so the class can initialize **/
    public static void register() {}

    public static final ConfigSetting<Boolean> knockbackRestrictions =
            ConfigBuilder.booleanSetting("knockbackRestrictions")
                    .defaultValue(true)
                    .comment("Disables knockback if not using a suitable weapon")
                    .build();

    public static final ConfigSetting<Boolean> spawnBabyZombies =
            ConfigBuilder.booleanSetting("spawnBabyZombies")
                    .defaultValue(false)
                    .comment("Can baby zombies spawn naturally?")
                    .build();

    public static final ConfigSetting<Boolean> spawnMobsOnWood =
            ConfigBuilder.booleanSetting("spawnMobsOnWood")
                    .defaultValue(false)
                    .comment("Can mobs spawn on wooden blocks?")
                    .build();

    public static final ConfigSetting<Boolean> increasedMonsterSpawnsPerChunk =
            ConfigBuilder.booleanSetting("increasedMonsterSpawnsPerChunk")
                    .defaultValue(true)
                    .comment("Slightly increase the number of mobs that can spawn per chunk")
                    .build();

    public static final ConfigSetting<Boolean> changedCreeperExplosionPos =
            ConfigBuilder.booleanSetting("changedCreeperExplosionPos")
                    .defaultValue(true)
                    .comment("Changes the location of creeper's explosion origin to\n be calculated from their eyes instead of their feet")
                    .build();

    public static final ConfigSetting<Boolean> increasedLightningStrikeChance =
            ConfigBuilder.booleanSetting("increasedLightningStrikeChance")
                    .defaultValue(true)
                    .comment("Changes lightning strike chance to be two times as common")
                    .build();

    static {
        CONFIG = new ConfigGroup(String.format("%s/%s_common.toml", MOD_ID, MOD_ID));

        CONFIG.add(knockbackRestrictions);
        CONFIG.add(spawnBabyZombies);
        CONFIG.add(spawnMobsOnWood);
        CONFIG.add(increasedMonsterSpawnsPerChunk);
        CONFIG.add(changedCreeperExplosionPos);
        CONFIG.add(increasedLightningStrikeChance);

        TomlConfigManager.registerGroup(CONFIG); // auto init/load/save
    }

}
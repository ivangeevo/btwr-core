package org.btwr.core.config;

import com.google.common.reflect.Reflection;
import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.Requirement;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.btwr.core.BTWRMod;

import java.util.function.Supplier;

public class BTWRModClientConfig {

    public static void register() {
        Reflection.initialize(Settings.class);
    }

    public static class Settings {
        public static Supplier<Boolean> exampleClientSetting;

        static {
            // construct a new config builder
            String modId = BTWRMod.MOD_ID;
            IConfigBuilder builder = ConfigBuilders.newTomlConfig(modId, modId + FabricLoader.getInstance().getEnvironmentType(), true);

            /**
            exampleClientSetting = builder
                    .comment(String.valueOf(Text.translatable("config.btwr.exampleClientSetting")))
                    .define("exampleClientSetting", true);
             **/

            // build the config
            builder.build();
        }
    }

    // Handles config screen creation with Cloth Config API
    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.btwr.config"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.btwr.category.general"));

        // Client Settings
        general.addEntry(entryBuilder.startTextDescription(Text.translatable("config.btwr.text.clientSettingsText")).build());
        general.addEntry(entryBuilder.startTextDescription(Text.translatable("config.btwr.text.emptyClientConfigText")).build());

        // Server Settings
        general.addEntry(entryBuilder.startTextDescription(Text.translatable("config.btwr.text.serverSettingsText")).build());
        general.addEntry(entryBuilder
                .startTextDescription(Text.translatable("config.btwr.text.serverSettingsNoAccessText"))
                .setDisplayRequirement(displayWhenRemoteOrLAN())
                .build()
        );
        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.knockbackRestrictions"), BTWRModConfig.Settings.knockbackRestrictions.get())
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> BTWRModConfig.Settings.knockbackRestrictions.get())
                .setTooltip(Text.translatable("config.btwr.tooltip.knockbackRestrictions"))
                .setDisplayRequirement(displayWhenTrueSingleplayer())
                .build()
        );
        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.spawnBabyZombies"), BTWRModConfig.Settings.spawnBabyZombies.get())
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> BTWRModConfig.Settings.spawnBabyZombies.get())
                .setTooltip(Text.translatable("config.btwr.tooltip.spawnBabyZombies"))
                .setDisplayRequirement(displayWhenTrueSingleplayer())
                .build()
        );
        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.spawnMobsOnWood"), BTWRModConfig.Settings.spawnMobsOnWood.get())
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> BTWRModConfig.Settings.spawnMobsOnWood.get())
                .setTooltip(Text.translatable("config.btwr.tooltip.spawnMobsOnWood"))
                .setDisplayRequirement(displayWhenTrueSingleplayer())
                .build()
        );
        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.increasedMonsterSpawnsPerChunk"), BTWRModConfig.Settings.increasedMonsterSpawnsPerChunk.get())
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> BTWRModConfig.Settings.increasedMonsterSpawnsPerChunk.get())
                .setTooltip(Text.translatable("config.btwr.tooltip.increasedMonsterSpawnsPerChunk"))
                .setDisplayRequirement(displayWhenTrueSingleplayer())
                .build()
        );
        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.changedCreeperExplosionPos"), BTWRModConfig.Settings.changedCreeperExplosionPos.get())
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> BTWRModConfig.Settings.changedCreeperExplosionPos.get())
                .setTooltip(Text.translatable("config.btwr.tooltip.changedCreeperExplosionPos"))
                .setDisplayRequirement(displayWhenTrueSingleplayer())
                .build()
        );

        return builder.build();
    }

    /* -----------------------------------------------------------
       Detection helpers
       ----------------------------------------------------------- */

    private static boolean isNotTrueSingleplayer() {
        return !isTrueSingleplayer();
    }

    private static Requirement displayWhenTrueSingleplayer() {
        return BTWRModClientConfig::isTrueSingleplayer;
    }

    private static Requirement hideWhenNotTrueSingleplayer() {
        return () -> !isTrueSingleplayer();
    }

    private static Requirement displayWhenRemoteOrLAN() {
        return BTWRModClientConfig::isNotTrueSingleplayer;
    }

    private static boolean isWorldLoaded() {
        return MinecraftClient.getInstance().world != null;
    }

    private static boolean isTrueSingleplayer() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return false;
        if (client.getCurrentServerEntry() != null) return false;

        // Integrated server exists in SP and LAN host
        if (client.getServer() == null) return false;

        // Only SP has isRemote == false
        return !client.getServer().isRemote();
    }
}
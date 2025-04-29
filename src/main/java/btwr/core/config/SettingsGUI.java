package btwr.core.config;

import btwr.core.BTWRMod;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class SettingsGUI
{
    static BTWRSettings settingsCommon = BTWRMod.getInstance().settings;

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.btwr.config"));

        builder.setSavingRunnable(() -> { BTWRMod.getInstance().saveSettings(); });

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.btwr.category.general"));
        ConfigCategory entity = builder.getOrCreateCategory(Text.translatable("config.btwr.category.entity"));

        /** General Category**/

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.knockbackRestriction"), settingsCommon.knockbackRestrictions)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> settingsCommon.knockbackRestrictions = newValue)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.btwHoeFunctionality"), settingsCommon.btwHoeFunctionality)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> settingsCommon.btwHoeFunctionality = newValue)
                .build());

        /** Entity Category**/

        entity.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.increasedMonsterSpawnsPerChunk"), settingsCommon.increasedMonsterSpawnsPerChunk)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> settingsCommon.increasedMonsterSpawnsPerChunk = newValue)
                .build());

        entity.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.spawnBabyZombies"), settingsCommon.spawnBabyZombies)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> settingsCommon.spawnBabyZombies = newValue)
                .build());

        entity.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.spawnMobsOnWood"), settingsCommon.spawnMobsOnWood)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> settingsCommon.spawnMobsOnWood = newValue)
                .build());

        return builder.build();
    }



}

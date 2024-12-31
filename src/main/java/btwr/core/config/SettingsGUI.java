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

        /** Gameplay Category**/

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.knockbackRestriction"), settingsCommon.knockbackRestrictions)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> settingsCommon.knockbackRestrictions = newValue)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.babyZombiesSpawn"), settingsCommon.babyZombiesSpawn)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> settingsCommon.babyZombiesSpawn = newValue)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.mobsSpawnOnWood"), settingsCommon.mobsSpawnOnWood)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> settingsCommon.mobsSpawnOnWood = newValue)
                .build());

        return builder.build();
    }



}

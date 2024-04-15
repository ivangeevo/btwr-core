package btwr.core.config;

import btwr.core.BTWRMod;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsGUI
{
    static BTWRSettings settingsCommon = BTWRMod.getInstance().settings;

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Text.translatable("title.btwr.config"));
        builder.setSavingRunnable(() -> {

            BTWRMod.getInstance().saveSettings();

        });

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.btwr.category.general"));

        /** Gameplay Category**/

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.knockbackRestriction"), settingsCommon.knockbackRestriction)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> settingsCommon.knockbackRestriction = newValue)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.btwr.disableBabyZombies"), settingsCommon.dontSpawnBabyZombies)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> settingsCommon.dontSpawnBabyZombies = newValue)
                .build());

        return builder.build();
    }



}

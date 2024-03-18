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

public class BTWRSettingsGUI {

    private static final Map<String, Boolean> configValues = new HashMap<>();

    public static void initConfig() {
        loadConfig();
    }
    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Text.translatable("title.btwr.config"));
        builder.setSavingRunnable(() -> {
            saveConfig();
        });

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.btwr.category.general"));

        /** Gameplay Category**/

        addConfigEntry(general, entryBuilder, BTWRSettings.KNOCKBACK_RESTRICTION_KEY, true);
        addConfigEntry(general, entryBuilder, BTWRSettings.DISABLE_BABY_ZOMBIES_KEY, true);
        //addConfigEntry(general, entryBuilder, BTWRSettings.HARDCORE_MATERIAL_DURABILITY_KEY, true);
        //addConfigEntry(general, entryBuilder, BTWRSettings.HARDCORE_MATERIAL_SPEED_KEY, true);

        return builder.build();
    }

    private static void addConfigEntry(ConfigCategory category, ConfigEntryBuilder entryBuilder, String key,
                                       boolean defaultValue) {
        boolean value = configValues.getOrDefault(key, defaultValue);
        System.out.println("Key: " + key + ", Value: " + value); // Add this line for debugging
        category.addEntry(entryBuilder.startBooleanToggle(Text.translatable("config.btwr." + key), value)
                .setDefaultValue(defaultValue)
                .setSaveConsumer(newValue -> configValues.put(key, newValue))
                .build());
    }


    public static boolean getConfigValue(String key) {
        return configValues.getOrDefault(key, false);
    }




    public static Text[] wrapLines(Text text){
        List<StringVisitable> lines = MinecraftClient.getInstance().textRenderer.getTextHandler()
                .wrapLines(text,Math.max(MinecraftClient.getInstance().getWindow().getScaledWidth()/2 - 43,170),
                        Style.EMPTY);
        lines.get(0).getString();
        Text[] textLines = new Text[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            textLines[i]=Text.literal(lines.get(i).getString());
        }
        return textLines;
    }

    private static void saveConfig() {
        File configDir = new File("config/btwr");
        configDir.mkdirs();

        try (FileWriter writer = new FileWriter(new File(configDir, "config.txt"))) {
            // Write your configuration values to the file
            for (Map.Entry<String, Boolean> entry : configValues.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadConfig() {
        File configFile = new File("config/btwr/config.txt");
        try {
            if (configFile.exists()) {
                List<String> lines = Files.readAllLines(configFile.toPath());
                for (String line : lines) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String key = parts[0];
                        boolean value = Boolean.parseBoolean(parts[1]);
                        configValues.put(key, value);
                    } else {
                        System.out.println("Invalid line in config file: " + line);
                    }
                }
                System.out.println("Loaded Config: " + configValues);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

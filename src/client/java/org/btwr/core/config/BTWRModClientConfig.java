package org.btwr.core.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.Requirement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class BTWRModClientConfig {

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
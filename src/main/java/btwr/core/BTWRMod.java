package btwr.core;

import btwr.core.block.BTWR_Blocks;
import btwr.core.config.BTWRSettingsGUI;
import btwr.core.config.BTWRSettings;
import btwr.core.item.BTWR_Items;
import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BTWRMod implements ModInitializer
{
    public static final String MOD_ID = "btwr";
    public static final Logger LOGGER = LoggerFactory.getLogger("btwr");
    public BTWRSettings settings;
    private static BTWRMod instance;
    public static BTWRMod getInstance()
    {
        return instance;
    }

    @Override
    public void onInitialize()
    {
        instance = this;
        LOGGER.info("Initializing BTWR-Core.");
        loadSettings();

        BTWRItemGroup.registerItemGroups();
        BTWR_Blocks.registerModBlocks();
        BTWR_Items.registerModItems();

    }

    public void loadSettings() {
        File file = new File("./config/btwr/btwrCommon.json");
        Gson gson = new Gson();
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                settings = gson.fromJson(fileReader, BTWRSettings.class);
                fileReader.close();
            } catch (IOException e) {
                LOGGER.warn("Could not load BTWR settings: " + e.getLocalizedMessage());
            }
        } else {
            settings = new BTWRSettings();
        }
    }

    public void saveSettings() {
        Gson gson = new Gson();
        File file = new File("./config/btwr/btwrCommon.json");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(settings));
            fileWriter.close();
        } catch (IOException e) {
            LOGGER.warn("Could not save BTWR settings: " + e.getLocalizedMessage());
        }
    }

}

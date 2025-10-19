package btwr.core;

import btwr.core.block.BTWR_Blocks;
import btwr.core.config.BTWRSettings;
import btwr.core.event.BTWREntityEvents;
import btwr.core.item.BTWR_Items;
import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BTWRMod implements ModInitializer {

    public static final String MOD_ID = "btwr";
    public static final Logger LOGGER = LoggerFactory.getLogger("btwr");

    public BTWRSettings settings;
    private static final String CONFIG_FILE_PATH = "./config/btwr/btwrCommon.json";

    private static BTWRMod instance;
    public static BTWRMod getInstance() {
        return instance;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing BTWR-Core.");
        this.loadSettings();
        instance = this;

        BTWRItemGroup.register();
        BTWR_Blocks.register();
        BTWR_Items.register();

        BTWREntityEvents.register();
    }

    public void loadSettings() {
        File file = new File(CONFIG_FILE_PATH);
        Gson gson = new Gson();
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                settings = gson.fromJson(fileReader, BTWRSettings.class);
                fileReader.close();
            } catch (IOException e) {
                LOGGER.warn("Could not load BTWR: Core settings: " + e.getLocalizedMessage());
            }
        } else {
            settings = new BTWRSettings();
        }
    }

    public void saveSettings() {
        Gson gson = new Gson();
        File file = new File(CONFIG_FILE_PATH);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(settings));
            fileWriter.close();
        }

        catch (IOException e) {
            LOGGER.warn("Could not save BTWR: Core settings: " + e.getLocalizedMessage());
        }
    }

}

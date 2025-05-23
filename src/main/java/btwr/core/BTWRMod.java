package btwr.core;

import btwr.core.block.BlockTillingManager;
import btwr.core.config.BTWRSettings;
import btwr.core.entity.BTWR_EntityTypes;
import btwr.core.entity.CreeperModificationManager;
import btwr.core.event.ModLootTableEvents;
import btwr.core.item.BTWR_Items;
import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class BTWRMod implements ModInitializer {

    public static final String MOD_ID = "btwr";

    public static final Logger LOGGER = LoggerFactory.getLogger("btwr");

    public BTWRSettings settings;

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
        BTWR_Items.register();

        BTWR_EntityTypes.Blocks.registerBlockEntities();

        ModLootTableEvents.initialize();

        // Registers all tilling based interactions/modifications
        BlockTillingManager.registerNormalTillable();

        CreeperModificationManager.registerUseEvent();
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
                LOGGER.warn("Could not load btwr settings: " + e.getLocalizedMessage());
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
        }

        catch (IOException e) {
            LOGGER.warn("Could not save btwr settings: " + e.getLocalizedMessage());
        }
    }

}

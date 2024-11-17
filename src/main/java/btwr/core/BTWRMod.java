package btwr.core;

import btwr.core.block.BTWR_Blocks;
import btwr.core.config.BTWRSettings;
import btwr.core.entity.BTWR_EntityTypes;
import btwr.core.item.BTWR_Items;
import btwr.core.registry.ModFuelItems;
import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Blocks;
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
    public static BTWRMod getInstance() {
        return instance;
    }

    @Override
    public void onInitialize()
    {
        LOGGER.info("Initializing BTWR-Core.");
        loadSettings();
        instance = this;

        BTWRItemGroup.registerItemGroups();
        BTWR_Blocks.registerModBlocks();
        BTWR_Items.registerModItems();

        //ModRecipesRegistry.registerModRecipes();
        ModFuelItems.register();

        BTWR_EntityTypes.Blocks.registerBlockEntities();

        //modifyLeavesLootTables();

    }

    public void loadSettings()
    {
        File file = new File("./config/btwr/btwrCommon.json");
        Gson gson = new Gson();
        if (file.exists())
        {
            try
            {
                FileReader fileReader = new FileReader(file);
                settings = gson.fromJson(fileReader, BTWRSettings.class);
                fileReader.close();
            }
            catch (IOException e)
            {
                LOGGER.warn("Could not load btwr settings: " + e.getLocalizedMessage());
            }
        }
        else
        {
            settings = new BTWRSettings();
        }
    }

    public void saveSettings()
    {
        Gson gson = new Gson();
        File file = new File("./config/btwr/btwrCommon.json");
        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdir();
        }
        try
        {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(settings));
            fileWriter.close();
        }
        catch (IOException e)
        {
            LOGGER.warn("Could not save btwr settings: " + e.getLocalizedMessage());
        }
    }


    // TODO: Change loot tables modification that add conventional shears tag, to instead be handled with Events if possible
    /**
    private void modifyLeavesLootTables()
    {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            // Check if the loot table is for leaves
            if (id.equals(Blocks.OAK_LEAVES. ||
                    id.equals(Blocks.SPRUCE_LEAVES.getLootTableId()) ||
                    id.equals(Blocks.BIRCH_LEAVES.getLootTableId()) ||
                    id.equals(Blocks.JUNGLE_LEAVES.getLootTableId()) ||
                    id.equals(Blocks.ACACIA_LEAVES.getLootTableId()) ||
                    id.equals(Blocks.DARK_OAK_LEAVES.getLootTableId())) {

                // Modify drops only if the tool is the custom shears
                tableBuilder.pool(builder -> builder.rolls(1).conditionally((lootContext) -> {
                    return lootContext.get(LootContextParameters.TOOL).getItem() instanceof CustomShearsItem;
                }).with(ItemStack.of(Blocks.OAK_LEAVES)));
            }
        });
    }
     **/

}

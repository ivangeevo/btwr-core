package btwr.core;

import btwr.core.block.BTWR_Blocks;
import btwr.core.config.BTWRSettings;
import btwr.core.entity.BTWR_EntityTypes;
import btwr.core.item.BTWR_Items;
import btwr.core.registry.ModFuelItems;
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
        loadSettings();
        instance = this;

        BTWRItemGroup.registerItemGroups();
        BTWR_Blocks.registerModBlocks();
        BTWR_Items.registerModItems();

        //ModRecipesRegistry.registerModRecipes();
        ModFuelItems.register();

        BTWR_EntityTypes.Blocks.registerBlockEntities();

        //replaceLeavesLootTables();

        ModLootTableEvents.initialize();

        // Registers all tilling based interactions/modifications
        BlockTillingManager.registerAll();


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

    private void modifyLootTables() {
        Map<Block, Item> blockToolMap = Map.ofEntries(
                Map.entry(Blocks.VINE, BTWR_Items.DIAMOND_SHEARS),
                Map.entry(Blocks.OAK_LEAVES, BTWR_Items.DIAMOND_SHEARS),
                Map.entry(Blocks.BIRCH_LEAVES, BTWR_Items.DIAMOND_SHEARS),
                Map.entry(Blocks.SPRUCE_LEAVES, BTWR_Items.DIAMOND_SHEARS),
                Map.entry(Blocks.JUNGLE_LEAVES, BTWR_Items.DIAMOND_SHEARS),
                Map.entry(Blocks.ACACIA_LEAVES, BTWR_Items.DIAMOND_SHEARS),
                Map.entry(Blocks.DARK_OAK_LEAVES, BTWR_Items.DIAMOND_SHEARS),
                Map.entry(Blocks.MANGROVE_LEAVES, BTWR_Items.DIAMOND_SHEARS),
                Map.entry(Blocks.CHERRY_LEAVES, BTWR_Items.DIAMOND_SHEARS)
        );

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!source.isBuiltin()) return; // Only modify built-in loot tables

            blockToolMap.forEach((block, tool) -> {
                Identifier blockLootTableId = block.getLootTableKey().getValue(); // The loot table ID of the block

                // Compare the Identifier of the RegistryKey to the block's loot table ID
                if (key.getValue().equals(blockLootTableId)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .bonusRolls(ConstantLootNumberProvider.create(0f))
                            .rolls(ConstantLootNumberProvider.create(1))
                            .with(ItemEntry.builder(block))
                            .conditionally(MatchToolLootCondition.builder(
                                    ItemPredicate.Builder.create().items(tool))
                            ); // Add the block itself to the loot pool

                    tableBuilder.pool(poolBuilder);
                }
            });
        });
    }





}

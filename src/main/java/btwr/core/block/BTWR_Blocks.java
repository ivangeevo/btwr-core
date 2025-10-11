package btwr.core.block;

import btwr.core.BTWRMod;
import btwr.core.block.blocks.BlightBlock;
import btwr.core.block.blocks.BlightRootsBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BTWR_Blocks {

    // Registering blight without an item for now because it still doesn't work properly
    public static final Block BLIGHT = registerWithoutItem("blight", new BlightBlock(AbstractBlock.Settings.create()
            .strength(0.6F)
            .ticksRandomly()
            .sounds(BlockSoundGroup.GRAVEL))
    );

    public static final Block BLIGHT_ROOTS = registerWithoutItem("blight_roots", new BlightRootsBlock(
            AbstractBlock.Settings.copy(BTWR_Blocks.BLIGHT))
    );

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(BTWRMod.MOD_ID, name), block);
    }

    private static Block registerWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(BTWRMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(BTWRMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void register() {
        BTWRMod.LOGGER.debug("Registering ModBlocks for " + BTWRMod.MOD_ID);
        addToItemGroups();
    }

    private static void addToItemGroups() {
        /**
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
            content.addAfter(Blocks.GRASS_BLOCK, BLIGHT);
        });
         **/
    }
}

package btwr.core.block;

import btwr.btwrsl.lib.util.PlaceableAsBlock;
import btwr.core.BTWRMod;
import btwr.core.block.blocks.*;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BTWR_Blocks {


    // Block and Block Item declarations
    public static final Block CROP_HEMP = registerWithoutItem("crop_hemp", new HempCropBlock(AbstractBlock.Settings.create().noCollision().ticksRandomly().nonOpaque().strength(0.5f).sounds(BlockSoundGroup.GRASS)));
    public static final Block BRICK_UNFIRED = registerWithoutItem("brick_unfired", new UnfiredBrickBlock(AbstractBlock.Settings.create().breakInstantly().nonOpaque().sounds(BlockSoundGroup.STONE)));
    public static final Block BRICK = registerWithoutItem("brick", new BrickBlock(Items.BRICK, AbstractBlock.Settings.create().breakInstantly().nonOpaque().sounds(BlockSoundGroup.STONE)));


    private static Block register(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(BTWRMod.MOD_ID, name), block);
    }

    private static Block registerWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(BTWRMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(BTWRMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));

    }

    public static void registerModBlocks() {
        BTWRMod.LOGGER.debug("Registering ModBlocks for " + BTWRMod.MOD_ID);
    }

    // Vanilla items that don't have blocks by default; We associate a block with them and make it placeable
    public static void registerItemsPlaceableAsBlocks() {
        PlaceableAsBlock.getInstance().registerPlaceable(Items.BRICK, BTWR_Blocks.BRICK);
    }
}

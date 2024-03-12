package btwr.core.block;

import btwr.core.BTWRMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BTWR_Blocks
{

    // Methods for registering blocks.

    private static Block registerBlock(String name, Block block)
    {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(BTWRMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block)
    {
        Item item = Registry.register(Registries.ITEM, new Identifier(BTWRMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        return item;
    }

    public static void registerModBlocks()
    {
        BTWRMod.LOGGER.debug("Registering ModBlocks for " + BTWRMod.MOD_ID);
    }

}

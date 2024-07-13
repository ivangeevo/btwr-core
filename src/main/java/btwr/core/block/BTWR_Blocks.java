package btwr.core.block;

import btwr.core.BTWRMod;
import btwr.core.block.blocks.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BTWR_Blocks
{

    // Methods for registering blocks.
    public static final Block CROP_HEMP = registerBlock("crop_hemp", new HempCropBlock(AbstractBlock.Settings.create().noCollision().ticksRandomly().nonOpaque().strength(0.5f).sounds(BlockSoundGroup.GRASS)));
    public static final Block LIGHTBLOCK = registerBlock("lightblock", new LightBlock(AbstractBlock.Settings.create().hardness(1.8f).requiresTool().luminance(state -> state.get(LightBlock.LIT) ? 15 : 0).sounds(BlockSoundGroup.GLASS).nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)));
    public static final Block ROPE_COIL = registerBlock("rope_coil", new PillarBlock(AbstractBlock.Settings.create().strength(1.2f).sounds(BlockSoundGroup.WOOD)));


    // Blocks with no items registered.
    public static final Block BRICK_UNFIRED = registerBlockNoItem("brick_unfired", new UnfiredBrickBlock(AbstractBlock.Settings.create().breakInstantly().nonOpaque().sounds(BlockSoundGroup.STONE), ParticleTypes.CLOUD));
    public static final Block BRICK = registerBlockNoItem("brick", new BrickBlock(AbstractBlock.Settings.create().breakInstantly().nonOpaque().sounds(BlockSoundGroup.STONE)));



    //  TO BE ADDED:
    //public static final Block COMPANIONCUBE = registerBlock("companioncube", new CompanionCube(AbstractBlock.Settings.create().strength(1.2f).ticksRandomly()));


    private static Block registerBlock(String name, Block block)
    {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(BTWRMod.MOD_ID, name), block);
    }

    private static Block registerBlockNoItem(String name, Block block)
    {
        return Registry.register(Registries.BLOCK, new Identifier(BTWRMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block)
    {
        return Registry.register(Registries.ITEM, new Identifier(BTWRMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }



    public static void registerModBlocks()
    {
        BTWRMod.LOGGER.debug("Registering ModBlocks for " + BTWRMod.MOD_ID);
    }

}

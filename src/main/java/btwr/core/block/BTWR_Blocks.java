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

public class BTWR_Blocks {

    // Block and Block Item declarations
    public static final Block CROP_HEMP = registerBlockWithItem("crop_hemp", new HempCropBlock(AbstractBlock.Settings.create().noCollision().ticksRandomly().nonOpaque().strength(0.5f).sounds(BlockSoundGroup.GRASS)));
    public static final Block LIGHTBLOCK = registerBlockWithItem("lightblock", new LightBlock(AbstractBlock.Settings.create().hardness(1.8f).requiresTool().luminance(state -> state.get(LightBlock.LIT) ? 15 : 0).sounds(BlockSoundGroup.GLASS).nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)));
    public static final Block ROPE_COIL = registerBlockWithItem("rope_coil", new PillarBlock(AbstractBlock.Settings.create().strength(1.2f).sounds(BlockSoundGroup.WOOD)));
    public static final Block BRICK_UNFIRED = registerBlockWithItem("brick_unfired", new UnfiredBrickBlock(AbstractBlock.Settings.create().breakInstantly().nonOpaque().sounds(BlockSoundGroup.STONE), ParticleTypes.CLOUD));
    public static final Block BRICK = registerBlockWithItem("brick", new BrickBlock(AbstractBlock.Settings.create().breakInstantly().nonOpaque().sounds(BlockSoundGroup.STONE)));

    // Method for registering blocks
    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(BTWRMod.MOD_ID, name), block);
    }

    // Method for registering block items
    private static Block registerBlockWithItem(String name, Block block) {
        Block registeredBlock = registerBlock(name, block);
        Registry.register(Registries.ITEM, Identifier.of(BTWRMod.MOD_ID, name), new BlockItem(registeredBlock, new Item.Settings()));
        return registeredBlock;
    }

    public static void registerModBlocks() {
        BTWRMod.LOGGER.debug("Registering ModBlocks for " + BTWRMod.MOD_ID);
    }
}

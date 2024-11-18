package btwr.core.block;

import btwr.core.BTWRMod;
import btwr.core.block.blocks.*;
import btwr.core.block.blocks.LightBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BTWR_Blocks {


    // Block and Block Item declarations
    //public static final Block LIGHTBLOCK = register("lightblock", createLightBlock());
    //public static final Block ROPE_COIL = register("rope_coil", new PillarBlock(AbstractBlock.Settings.create().strength(1.2f).sounds(BlockSoundGroup.WOOD)));

    public static final Block CROP_HEMP = registerWithoutItem("crop_hemp", new HempCropBlock(AbstractBlock.Settings.create().noCollision().ticksRandomly().nonOpaque().strength(0.5f).sounds(BlockSoundGroup.GRASS)));
    public static final Block BRICK_UNFIRED = registerWithoutItem("brick_unfired", new UnfiredBrickBlock(AbstractBlock.Settings.create().breakInstantly().nonOpaque().sounds(BlockSoundGroup.STONE)));
    public static final Block BRICK = registerWithoutItem("brick", new BrickBlock(AbstractBlock.Settings.create().breakInstantly().nonOpaque().sounds(BlockSoundGroup.STONE)));

    // tried making non-lit light blocks to not see through other LIT blocks, but didn't work
    //private static Block createLightBlock() {
        //AbstractBlock.ContextPredicate contextPredicate = (state, world, pos) -> state.get(LightBlock.LIT);
       // return new LightBlock(AbstractBlock.Settings.create().hardness(1.8f).requiresTool().luminance(state -> state.get(LightBlock.LIT) ? 15 : 0).sounds(BlockSoundGroup.GLASS).nonOpaque().blockVision(contextPredicate));
    //}

    private static Block register(String name, Block block)
    {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(BTWRMod.MOD_ID, name), block);
    }

    private static Block registerWithoutItem(String name, Block block)
    {
        return Registry.register(Registries.BLOCK, Identifier.of(BTWRMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block)
    {
        Registry.register(Registries.ITEM, Identifier.of(BTWRMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));

    }

    public static void registerModBlocks() {
        BTWRMod.LOGGER.debug("Registering ModBlocks for " + BTWRMod.MOD_ID);
    }
}

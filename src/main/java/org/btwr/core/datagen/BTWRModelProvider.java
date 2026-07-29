package org.btwr.core.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import org.btwr.core.block.BTWR_Blocks;
import org.btwr.core.block.blocks.BlightBlock;
import org.btwr.core.block.blocks.BlightRootsBlock;
import org.btwr.core.item.BTWR_Items;
import org.btwr.shared_library.util.utils.IdUtils;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.data.client.BlockStateModelGenerator.createSlabBlockState;

public class BTWRModelProvider extends FabricModelProvider {

    public BTWRModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        this.registerBlight(generator);
        this.registerBlightRoots(generator);
        generator.registerSimpleCubeAll(BTWR_Blocks.FLINT_BLOCK);
        generator.registerSimpleCubeAll(BTWR_Blocks.DIAMOND_INGOT_BLOCK);
        generator.registerSimpleCubeAll(BTWR_Blocks.SPIDER_EYE_BLOCK);
        this.registerSimpleSlab(generator, BTWR_Blocks.SPIDER_EYE_BLOCK, BTWR_Blocks.SPIDER_EYE_SLAB);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(BTWR_Items.GROUP_BTWR, Models.GENERATED);
        generator.register(BTWR_Items.DIAMOND_INGOT, Models.GENERATED);
        generator.register(BTWR_Items.DIAMOND_PLATE, Models.GENERATED);
        generator.register(BTWR_Items.LEATHER_CUT, Models.GENERATED);
        generator.register(BTWR_Items.LEATHER_SCOURED, Models.GENERATED);
        generator.register(BTWR_Items.LEATHER_SCOURED_CUT, Models.GENERATED);
        generator.register(BTWR_Items.LEATHER_TANNED, Models.GENERATED);
        generator.register(BTWR_Items.LEATHER_TANNED_CUT, Models.GENERATED);
        generator.register(BTWR_Items.CLUB_WOOD, Models.HANDHELD);
        generator.register(BTWR_Items.CLUB_BONE, Models.HANDHELD);
        generator.register(BTWR_Items.DIAMOND_SHEARS, Models.GENERATED);
        generator.register(BTWR_Items.LEATHER_TANNED_HELMET, Models.GENERATED);
        generator.register(BTWR_Items.LEATHER_TANNED_CHESTPLATE, Models.GENERATED);
        generator.register(BTWR_Items.LEATHER_TANNED_LEGGINGS, Models.GENERATED);
        generator.register(BTWR_Items.LEATHER_TANNED_BOOTS, Models.GENERATED);
        generator.register(BTWR_Items.EGG_SCRAMBLED_RAW, Models.GENERATED);
        generator.register(BTWR_Items.MUSHROOM_OMELETTE_RAW, Models.GENERATED);
        generator.register(BTWR_Items.EGG_SCRAMBLED_COOKED, Models.GENERATED);
        generator.register(BTWR_Items.MUSHROOM_OMELETTE_COOKED, Models.GENERATED);
        generator.register(BTWR_Items.SANDWICH, Models.GENERATED);
        generator.register(BTWR_Items.HAM_AND_EGGS, Models.GENERATED);
        generator.register(BTWR_Items.CHOWDER, Models.GENERATED);
        generator.register(BTWR_Items.STEAK_AND_POTATOES, Models.GENERATED);
        generator.register(BTWR_Items.RAW_KEBAB, Models.GENERATED);
        generator.register(BTWR_Items.COOKED_KEBAB, Models.GENERATED);
        generator.register(BTWR_Items.STEAK_DINNER, Models.GENERATED);
        generator.register(BTWR_Items.PORK_DINNER, Models.GENERATED);
        generator.register(BTWR_Items.WOLF_DINNER, Models.GENERATED);
        generator.register(BTWR_Items.CHICKEN_SOUP, Models.GENERATED);
        generator.register(BTWR_Items.HEARTY_STEW, Models.GENERATED);
        generator.register(BTWR_Items.BEAST_LIVER_RAW, Models.GENERATED);
        generator.register(BTWR_Items.BEAST_LIVER_COOKED, Models.GENERATED);
        generator.register(BTWR_Items.OCULAR_OF_ENDER, Models.GENERATED);
        generator.register(BTWR_Items.ENDER_SPECTACLES, Models.GENERATED);
    }

    private void registerBlight(BlockStateModelGenerator gen) {
        BlockStateVariantMap.SingleProperty<Integer> variantMap = BlockStateVariantMap.create(BlightBlock.LEVEL);
        for (int i = 0; i <= 3; i++) {
            TextureMap textureMap = new TextureMap()
                    .put(TextureKey.BOTTOM, TextureMap.getSubId(BTWR_Blocks.BLIGHT, "_bottom_" + i))
                    .inherit(TextureKey.BOTTOM, TextureKey.PARTICLE)
                    .put(TextureKey.TOP, TextureMap.getSubId(BTWR_Blocks.BLIGHT, "_top_" + i))
                    .put(TextureKey.SIDE, TextureMap.getSubId(BTWR_Blocks.BLIGHT, "_side_" + i));
            Identifier modelId = Models.CUBE_BOTTOM_TOP.upload(BTWR_Blocks.BLIGHT, "_" + i, textureMap, gen.modelCollector);
            variantMap.register(i, BlockStateVariant.create().put(VariantSettings.MODEL, modelId));
        }
        gen.blockStateCollector.accept(VariantsBlockStateSupplier.create(BTWR_Blocks.BLIGHT).coordinate(variantMap));
        gen.registerParentedItemModel(BTWR_Blocks.BLIGHT, IdUtils.ofBTWR("block/blight_0"));
    }

    private void registerBlightRoots(BlockStateModelGenerator gen) {
        BlockStateVariantMap.SingleProperty<Integer> variantMap = BlockStateVariantMap.create(BlightRootsBlock.LEVEL);

        for (int i = 0; i <= 1; i++) {
            Identifier rootsTex = TextureMap.getSubId(BTWR_Blocks.BLIGHT_ROOTS, "_" + i);
            TextureMap textureMap = new TextureMap()
                    .put(TextureKey.BOTTOM, TextureMap.getSubId(BTWR_Blocks.BLIGHT, "_bottom_0"))
                    .inherit(TextureKey.BOTTOM, TextureKey.PARTICLE)
                    .put(TextureKey.TOP, rootsTex)
                    .put(TextureKey.SIDE, rootsTex);

            Identifier modelId = Models.CUBE_BOTTOM_TOP.upload(BTWR_Blocks.BLIGHT_ROOTS, "_" + i, textureMap, gen.modelCollector);
            variantMap.register(i, BlockStateVariant.create().put(VariantSettings.MODEL, modelId));
        }

        gen.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(BTWR_Blocks.BLIGHT_ROOTS).coordinate(variantMap)
        );
        gen.registerParentedItemModel(BTWR_Blocks.BLIGHT_ROOTS, IdUtils.ofBTWR("block/blight_roots_0"));
    }

    private void registerSimpleSlab(BlockStateModelGenerator gen, Block mainBlock, Block slabBlock) {
        Identifier identifier = ModelIds.getBlockModelId(mainBlock);
        TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(mainBlock);
        Identifier identifier2 = Models.SLAB.upload(slabBlock, texturedModel.getTextures(), gen.modelCollector);
        Identifier identifier3 = Models.SLAB_TOP.upload(slabBlock, texturedModel.getTextures(), gen.modelCollector);
        gen.blockStateCollector.accept(createSlabBlockState(slabBlock, identifier2, identifier3, identifier));
    }

}
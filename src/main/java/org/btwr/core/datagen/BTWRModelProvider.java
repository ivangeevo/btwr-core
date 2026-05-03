package org.btwr.core.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import org.btwr.core.block.BTWR_Blocks;

import static net.minecraft.data.client.BlockStateModelGenerator.createSlabBlockState;

public class BTWRModelProvider extends FabricModelProvider {

    public BTWRModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerSimpleCubeAll(BTWR_Blocks.FLINT_BLOCK);
        generator.registerSimpleCubeAll(BTWR_Blocks.DIAMOND_INGOT_BLOCK);
        generator.registerSimpleCubeAll(BTWR_Blocks.CREEPER_OYSTER_BLOCK);
        this.registerSimpleSlab(generator, BTWR_Blocks.CREEPER_OYSTER_BLOCK, BTWR_Blocks.CREEPER_OYSTER_SLAB);
        generator.registerSimpleCubeAll(BTWR_Blocks.SPIDER_EYE_BLOCK);
        this.registerSimpleSlab(generator, BTWR_Blocks.SPIDER_EYE_BLOCK, BTWR_Blocks.SPIDER_EYE_SLAB);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
    }

    private void registerSimpleSlab(BlockStateModelGenerator gen, Block mainBlock, Block slabBlock) {
        Identifier identifier = ModelIds.getBlockModelId(mainBlock);
        TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(mainBlock);
        Identifier identifier2 = Models.SLAB.upload(slabBlock, texturedModel.getTextures(), gen.modelCollector);
        Identifier identifier3 = Models.SLAB_TOP.upload(slabBlock, texturedModel.getTextures(), gen.modelCollector);
        gen.blockStateCollector.accept(createSlabBlockState(slabBlock, identifier2, identifier3, identifier));
    }

}
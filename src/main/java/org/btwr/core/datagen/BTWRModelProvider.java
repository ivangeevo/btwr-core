package org.btwr.core.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import org.btwr.core.block.BTWR_Blocks;

public class BTWRModelProvider extends FabricModelProvider {

    public BTWRModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(BTWR_Blocks.FLINT_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(BTWR_Blocks.DIAMOND_INGOT_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }

}
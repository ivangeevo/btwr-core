package org.btwr.core.datagen;

import net.minecraft.registry.tag.BlockTags;
import org.btwr.core.block.BTWR_Blocks;
import org.btwr.core.tag.BTWRTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BTWRBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public BTWRBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        addToModTags();
        addToVanillaTags();
        addToConventionalTags();
    }

    private void addToVanillaTags() {
        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(BTWR_Blocks.BLIGHT)
                .add(BTWR_Blocks.BLIGHT_ROOTS)
                .add(BTWR_Blocks.CREEPER_OYSTER_BLOCK)
                .add(BTWR_Blocks.CREEPER_OYSTER_SLAB)
                .add(BTWR_Blocks.SPIDER_EYE_BLOCK)
                .add(BTWR_Blocks.SPIDER_EYE_SLAB);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(BTWR_Blocks.FLINT_BLOCK)
                .add(BTWR_Blocks.DIAMOND_INGOT_BLOCK);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(BTWR_Blocks.DIAMOND_INGOT_BLOCK);
    }

    private void addToModTags() {
    }

    private void addToConventionalTags() {
    }

}
package btwr.core.datagen;

import btwr.core.block.BTWR_Blocks;
import btwr.core.tag.BTWRTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

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


    }

    private void addToModTags() {
        getOrCreateTagBuilder(BTWRTags.Blocks.SHEARS_EFFICIENT)
                .forceAddTag(BlockTags.LEAVES)
                .add(Blocks.VINE)
                .add(Blocks.GLOW_LICHEN)
                .add(BTWR_Blocks.CROP_HEMP);
    }

    private void addToConventionalTags() {

    }
}

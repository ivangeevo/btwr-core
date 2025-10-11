package btwr.core.datagen;

import btwr.core.tag.BTWRTags;
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
    }

    private void addToModTags() {

        getOrCreateTagBuilder(BTWRTags.Blocks.BLIGHT_SPREADS_TO)
                .add(Blocks.DIRT)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.FARMLAND);

    }

    private void addToConventionalTags() {

    }
}

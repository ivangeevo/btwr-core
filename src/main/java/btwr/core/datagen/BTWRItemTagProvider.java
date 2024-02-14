package btwr.core.datagen;

import btwr.core.tag.BTWRTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BTWRItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public BTWRItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BTWRTags.Items.AXES_MAKE_PLANKS)
                .add(Items.IRON_AXE)
                .add(Items.DIAMOND_AXE)
                .add(Items.GOLDEN_AXE)
                .add(Items.NETHERITE_AXE);

        getOrCreateTagBuilder(BTWRTags.Items.PRIMITIVE_AXES)
                .add(Items.WOODEN_AXE)
                .add(Items.STONE_AXE);
    }
}
package org.btwr.core.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.btwr.core.item.BTWR_Items;
import org.btwr.core.tag.BTWRTags;
import org.btwr.shared_library.tag.BTWRConventionalTags;

import java.util.concurrent.CompletableFuture;

public class BTWREntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider
{
    public BTWREntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
    {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        addToVanilla();
        addToModTags();
        addToConventionalTags();
    }

    private void addToVanilla() {

    }

    private void addToModTags() {
        // Add the vanilla creeper by default
        getOrCreateTagBuilder(BTWRTags.EntityTypes.NEUTERABLE_CREEPERS)
                .add(EntityType.CREEPER);
    }

    private void addToConventionalTags() {
    }

}
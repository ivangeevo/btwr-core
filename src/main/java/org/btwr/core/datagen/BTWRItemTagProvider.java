package org.btwr.core.datagen;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import org.btwr.api.api.tag.BTWRConventionalTags;
import org.btwr.core.item.ModItems;
import org.btwr.core.tag.BTWRTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class BTWRItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public BTWRItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        addToVanilla();
        addToModTags();
        addToConventionalTags();
    }

    private void addToVanilla() {
        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR)
                .add(ModItems.LEATHER_TANNED_HELMET);

        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                .add(ModItems.LEATHER_TANNED_CHESTPLATE);

        getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                .add(ModItems.LEATHER_TANNED_LEGGINGS);

        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
                .add(ModItems.LEATHER_TANNED_BOOTS);

    }

    private void addToModTags() {
        getOrCreateTagBuilder(BTWRTags.Items.CLAY_ITEMS)
                .add(Items.CLAY_BALL);
                //.add(BTWR_Items.BRICK_UNFIRED);

        getOrCreateTagBuilder(BTWRTags.Items.NORMAL_LEATHERS)
                .add(Items.LEATHER)
                .add(ModItems.LEATHER_CUT);

        getOrCreateTagBuilder(BTWRTags.Items.SCOURED_LEATHERS)
                .add(ModItems.LEATHER_SCOURED)
                .add(ModItems.LEATHER_SCOURED_CUT);

        getOrCreateTagBuilder(BTWRTags.Items.TANNED_LEATHERS)
                .add(ModItems.LEATHER_TANNED)
                .add(ModItems.LEATHER_TANNED_CUT);

        getOrCreateTagBuilder(BTWRTags.Items.CUT_LEATHERS)
                .add(ModItems.LEATHER_CUT)
                .add(ModItems.LEATHER_SCOURED_CUT)
                .add(ModItems.LEATHER_TANNED_CUT);
    }

    private void addToConventionalTags() {
        // Fabric Conventional Tags
        getOrCreateTagBuilder(ConventionalItemTags.SHEAR_TOOLS)
                .add(ModItems.DIAMOND_SHEARS);

        // BTWR Added Conventional Tags
        getOrCreateTagBuilder(BTWRConventionalTags.Items.DO_KNOCKBACK_ITEMS)
                .add(ModItems.CLUB_WOOD)
                .add(ModItems.CLUB_BONE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.DIAMOND_TOOLS)
                .add(ModItems.DIAMOND_SHEARS);

        // Crafting sound tags
        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_WOODEN_TOOL_SOUND)
                .forceAddTag(BTWRConventionalTags.Items.WOODEN_TOOLS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_STONE_TOOL_SOUND)
                .forceAddTag(BTWRConventionalTags.Items.STONE_TOOLS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_METALLIC_TOOL_SOUND)
                .forceAddTag(BTWRConventionalTags.Items.IRON_TOOLS)
                .forceAddTag(BTWRConventionalTags.Items.GOLDEN_TOOLS)
                .forceAddTag(BTWRConventionalTags.Items.DIAMOND_TOOLS)
                .forceAddTag(BTWRConventionalTags.Items.NETHERITE_TOOLS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_SLIME_SOUND)
                .addTag(BTWRTags.Items.CLAY_ITEMS)
                .add(ModItems.DIAMOND_INGOT);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_SHEARS_CUT_SOUND)
                .addTag(BTWRTags.Items.CUT_LEATHERS);
    }

}
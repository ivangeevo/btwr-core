package btwr.core.datagen;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import btwr.core.item.BTWR_Items;
import btwr.core.tag.BTWRTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class BTWRItemTagProvider extends FabricTagProvider.ItemTagProvider
{
    public BTWRItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
    {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        addToModTags();
        addToConventionalTags();
    }

    private void addToModTags() {
        getOrCreateTagBuilder(BTWRTags.Items.CLAY_ITEMS)
                .add(Items.CLAY_BALL);
                //.add(BTWR_Items.BRICK_UNFIRED);

        getOrCreateTagBuilder(BTWRTags.Items.NORMAL_LEATHERS)
                .add(Items.LEATHER)
                .add(BTWR_Items.LEATHER_CUT);

        getOrCreateTagBuilder(BTWRTags.Items.SCOURED_LEATHERS)
                .add(BTWR_Items.LEATHER_SCOURED)
                .add(BTWR_Items.LEATHER_SCOURED_CUT);

        getOrCreateTagBuilder(BTWRTags.Items.TANNED_LEATHERS)
                .add(BTWR_Items.LEATHER_TANNED)
                .add(BTWR_Items.LEATHER_TANNED_CUT);

        getOrCreateTagBuilder(BTWRTags.Items.CUT_LEATHERS)
                .add(BTWR_Items.LEATHER_CUT)
                .add(BTWR_Items.LEATHER_SCOURED_CUT)
                .add(BTWR_Items.LEATHER_TANNED_CUT);
    }

    private void addToConventionalTags() {

        // Fabric Conventional Tags
        getOrCreateTagBuilder(ConventionalItemTags.SHEAR_TOOLS)
                .add(BTWR_Items.DIAMOND_SHEARS);

        // BTWR Added Conventional Tags
        getOrCreateTagBuilder(BTWRConventionalTags.Items.DO_KNOCKBACK_ITEMS)
                .add(BTWR_Items.CLUB_WOOD)
                .add(BTWR_Items.CLUB_BONE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.DIAMOND_TOOLS)
                .add(BTWR_Items.DIAMOND_SHEARS);

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
                .add(BTWR_Items.DIAMOND_INGOT);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_SHEARS_CUT_SOUND)
                .addTag(BTWRTags.Items.CUT_LEATHERS);
    }

}
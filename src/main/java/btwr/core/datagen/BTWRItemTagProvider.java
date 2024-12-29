package btwr.core.datagen;

import btwr.btwrsl.tag.BTWRConventionalTags;
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
        getOrCreateTagBuilder(ConventionalItemTags.STRINGS)
                .add(BTWR_Items.HEMP_FIBERS);

        getOrCreateTagBuilder(ConventionalItemTags.SHEAR_TOOLS)
                .add(BTWR_Items.DIAMOND_SHEARS);

        // BTWR Added Conventional Tags
        getOrCreateTagBuilder(BTWRConventionalTags.Items.STRING_TOOL_MATERIALS)
                .add(BTWR_Items.HEMP_FIBERS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .add(BTWR_Items.BOILED_POTATO);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.DO_KNOCKBACK_ITEMS)
                .add(BTWR_Items.CLUB_WOOD)
                .add(BTWR_Items.CLUB_BONE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.CHICKEN_TEMPT_ITEMS)
                .add(BTWR_Items.HEMP_SEEDS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_SLIME_SOUND)
                .addTag(BTWRTags.Items.CLAY_ITEMS)
                .add(BTWR_Items.DIAMOND_INGOT);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_SHEARS_CUT_SOUND)
                .addTag(BTWRTags.Items.CUT_LEATHERS);

        }

}
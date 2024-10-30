package btwr.core.datagen;

import btwr.core.item.BTWR_Items;
import btwr.core.tag.BTWRConventionalTags;
import btwr.core.tag.BTWRTags;
import com.bwt.items.BwtItems;
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
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {

        addToModTags();
        addToConventionalTags();
        
    }

    private void addToModTags()
    {


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

        getOrCreateTagBuilder(BTWRTags.Items.MEATS_FOR_SANDWICH)
                .add(Items.COOKED_CHICKEN)
                .add(Items.COOKED_BEEF)
                .add(Items.COOKED_PORKCHOP)
                .add(Items.COOKED_MUTTON)
                .add(Items.COOKED_RABBIT)
                .add(Items.COOKED_SALMON)
                .add(Items.COOKED_COD);
    }
    
    
    private void addToConventionalTags()
    {

        // Fabric Conventional Tags
        getOrCreateTagBuilder(ConventionalItemTags.STRINGS)
                .add(BTWR_Items.HEMP_FIBERS);

        // BTWR Added Conventional Tags
        getOrCreateTagBuilder(BTWRConventionalTags.Items.STRING_TOOL_MATERIALS)
                .add(Items.STRING)
                .add(BTWR_Items.HEMP_FIBERS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.GEARS)
                .add(BTWR_Items.GEAR);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.COOKED_EGG_FOODS)
                .add(BwtItems.friedEggItem)
                .add(BwtItems.poachedEggItem);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .add(Items.BAKED_POTATO)
                .add(BTWR_Items.BOILED_POTATO);


        getOrCreateTagBuilder(BTWRConventionalTags.Items.DO_KNOCKBACK_ITEMS)

                // Clubs do knockback
                .add(BTWR_Items.CLUB_WOOD)
                .add(BTWR_Items.CLUB_BONE)

                // All swords too
                .forceAddTag(ItemTags.SWORDS)

                // Special weapon items
                .add(Items.TRIDENT)
                .add(Items.BOW)
                .add(Items.CROSSBOW)

                // Axes do knockback only if iron or above
                .add(Items.IRON_AXE)
                .add(Items.DIAMOND_AXE)
                .add(Items.NETHERITE_AXE);


        getOrCreateTagBuilder(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES)
                .add(Items.WOODEN_PICKAXE)
                .add(Items.STONE_PICKAXE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.MODERN_PICKAXES)
                .add(Items.IRON_PICKAXE)
                .add(Items.GOLDEN_PICKAXE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                .add(Items.NETHERITE_PICKAXE)
                .add(Items.DIAMOND_PICKAXE);


        getOrCreateTagBuilder(BTWRConventionalTags.Items.PRIMITIVE_AXES)
                .add(Items.WOODEN_AXE)
                .add(Items.STONE_AXE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.MODERN_AXES)
                .add(Items.IRON_AXE)
                .add(Items.GOLDEN_AXE)
                .add(Items.DIAMOND_AXE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ADVANCED_AXES)
                .add(Items.NETHERITE_AXE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)
                .add(Items.IRON_AXE)
                .add(Items.DIAMOND_AXE)
                .addTag(BTWRConventionalTags.Items.ADVANCED_AXES);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.PRIMITIVE_HOES)
                .add(Items.WOODEN_HOE)
                .add(Items.STONE_HOE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.MODERN_HOES)
                .add(Items.IRON_HOE)
                .add(Items.GOLDEN_HOE)
                .add(Items.DIAMOND_HOE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ADVANCED_HOES)
                .add(Items.NETHERITE_HOE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.SHEARS)
                .add(Items.SHEARS)
                .add(BTWR_Items.DIAMOND_SHEARS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.CHICKEN_TEMPT_ITEMS)
                .add(BTWR_Items.HEMP_SEEDS);
        }
}
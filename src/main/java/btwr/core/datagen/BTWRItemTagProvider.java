package btwr.core.datagen;

import btwr.core.item.BTWR_Items;
import btwr.core.tag.BTWRTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
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
        getOrCreateTagBuilder(BTWRTags.Items.SHEARS)
                .add(Items.SHEARS)
                .add(BTWR_Items.DIAMOND_SHEARS);

        getOrCreateTagBuilder(BTWRTags.Items.AXES_MAKE_PLANKS)
                .add(Items.IRON_AXE)
                .add(Items.DIAMOND_AXE)
                .add(Items.GOLDEN_AXE)
                .add(Items.NETHERITE_AXE);

        getOrCreateTagBuilder(BTWRTags.Items.PRIMITIVE_AXES)
                .add(Items.WOODEN_AXE)
                .add(Items.STONE_AXE);


        getOrCreateTagBuilder(BTWRTags.Items.CLAY_ITEMS)
                .add(Items.CLAY_BALL)
                .add(BTWR_Items.BRICK_UNFIRED);

        getOrCreateTagBuilder(BTWRTags.Items.STRING_TOOL_MATERIALS)
                .add(Items.STRING)
                .add(BTWR_Items.HEMP_FIBERS);

        getOrCreateTagBuilder(BTWRTags.Items.NORMAL_LEATHERS)
                .add(Items.LEATHER)
                .add(BTWR_Items.LEATHER_CUT);

        getOrCreateTagBuilder(BTWRTags.Items.SCOURED_LEATHERS)
                .add(BTWR_Items.LEATHER_SCOURED)
                .add(BTWR_Items.LEATHER_SCOURED_CUT);

        getOrCreateTagBuilder(BTWRTags.Items.TANNED_LEATHERS)
                .add(BTWR_Items.LEATHER_TANNED)
                .add(BTWR_Items.LEATHER_TANNED_CUT);
    }
    
    
    private void addToConventionalTags()
    {
        getOrCreateTagBuilder(BTWRTags.Conventional.Items.DO_KNOCKBACK_ITEMS)

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


        getOrCreateTagBuilder(BTWRTags.Conventional.Items.PRIMITIVE_PICKAXES)
                .add(Items.WOODEN_PICKAXE)
                .add(Items.STONE_PICKAXE);

        getOrCreateTagBuilder(BTWRTags.Conventional.Items.MODERN_PICKAXES)
                .add(Items.IRON_PICKAXE)
                .add(Items.GOLDEN_PICKAXE)
                .add(Items.DIAMOND_PICKAXE);

        getOrCreateTagBuilder(BTWRTags.Conventional.Items.ADVANCED_PICKAXES)
                .add(Items.NETHERITE_PICKAXE);

        getOrCreateTagBuilder(BTWRTags.Conventional.Items.PRIMITIVE_AXES)
                .add(Items.WOODEN_AXE)
                .add(Items.STONE_AXE);

        getOrCreateTagBuilder(BTWRTags.Conventional.Items.MODERN_AXES)
                .add(Items.IRON_AXE)
                .add(Items.GOLDEN_AXE)
                .add(Items.DIAMOND_AXE);

        getOrCreateTagBuilder(BTWRTags.Conventional.Items.ADVANCED_AXES)
                .add(Items.NETHERITE_AXE);

        getOrCreateTagBuilder(BTWRTags.Conventional.Items.PRIMITIVE_SHOVELS)
                .add(Items.WOODEN_SHOVEL)
                .add(Items.STONE_SHOVEL);

        getOrCreateTagBuilder(BTWRTags.Conventional.Items.MODERN_SHOVELS)
                .add(Items.IRON_SHOVEL)
                .add(Items.GOLDEN_SHOVEL)
                .add(Items.DIAMOND_SHOVEL)
                .add(Items.NETHERITE_SHOVEL);

        getOrCreateTagBuilder(BTWRTags.Conventional.Items.ADVANCED_SHOVELS)
                .add(Items.NETHERITE_SHOVEL);

        getOrCreateTagBuilder(BTWRTags.Conventional.Items.PRIMITIVE_HOES)
                .add(Items.WOODEN_HOE)
                .add(Items.STONE_HOE);

        getOrCreateTagBuilder(BTWRTags.Conventional.Items.MODERN_HOES)
                .add(Items.IRON_HOE)
                .add(Items.GOLDEN_HOE)
                .add(Items.DIAMOND_HOE);

        getOrCreateTagBuilder(BTWRTags.Conventional.Items.ADVANCED_HOES)
                .add(Items.NETHERITE_HOE);
        }
}
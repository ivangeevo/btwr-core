package btwr.core.datagen;

import btwr.core.item.BTWR_Items;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class BTWRRecipeProvider extends FabricRecipeProvider {


    public BTWRRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerThreeInputShapelessRecipe(exporter, BTWR_Items.DIAMOND_INGOT, Items.IRON_INGOT, Items.DIAMOND, BTWR_Items.CREEPER_OYSTERS, "group_btwr",1);

        ShapedRecipeJsonBuilder.create(BTWR_Items.CLUB_BONE, 1).input('#', Items.BONE).pattern("#").pattern("#").criterion("has_bone", RecipeProvider.conditionsFromItem(Items.BONE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(BTWR_Items.CLUB_WOOD, 1).input('#', Items.STICK).pattern("#").pattern("#").criterion("has_stick", RecipeProvider.conditionsFromItem(Items.STICK)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(BTWR_Items.DIAMOND_SHEARS).input('#', BTWR_Items.DIAMOND_INGOT).pattern(" #").pattern("# ").criterion("has_diamond_ingot", RecipeProvider.conditionsFromItem(BTWR_Items.DIAMOND_INGOT)).offerTo(exporter);

    }

    public static void offerTwoInputShapelessRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input,  ItemConvertible input2, @Nullable String group, int outputCount) {
        ShapelessRecipeJsonBuilder.create(output, outputCount)
                .input(input).input(input2)
                .group(group)
                .criterion(RecipeProvider.hasItem(input), RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, RecipeProvider.convertBetween(output, input));
    }

    public static void offerThreeInputShapelessRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input,  ItemConvertible input2, ItemConvertible input3, @Nullable String group, int outputCount) {
        ShapelessRecipeJsonBuilder.create(output, outputCount)
                .input(input).input(input2).input(input3)
                .group(group)
                .criterion(RecipeProvider.hasItem(input), RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, RecipeProvider.convertBetween(output, input));
    }

    public static void offerFourInputShapelessRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input,  ItemConvertible input2, ItemConvertible input3,ItemConvertible input4, @Nullable String group, int outputCount) {
        ShapelessRecipeJsonBuilder.create(output, outputCount)
                .input(input).input(input2).input(input3).input(input4)
                .group(group)
                .criterion(RecipeProvider.hasItem(input), RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, RecipeProvider.convertBetween(output, input));
    }

}
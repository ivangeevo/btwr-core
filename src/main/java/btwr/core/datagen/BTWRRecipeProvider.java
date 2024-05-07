package btwr.core.datagen;

import btwr.core.item.BTWR_Items;
import btwr.core.tag.BTWRConventionalTags;
import btwr.core.tag.BTWRTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class BTWRRecipeProvider extends FabricRecipeProvider
{

    private static final List<ItemConvertible> NORMAL_LEATHERS = List.of(Items.LEATHER,BTWR_Items.LEATHER_CUT);

    private static final List<ItemConvertible> SCOURED_LEATHERS = List.of(BTWR_Items.LEATHER_SCOURED,BTWR_Items.LEATHER_SCOURED_CUT);

    private static final List<ItemConvertible> TANNED_LEATHERS = List.of(BTWR_Items.LEATHER_TANNED,BTWR_Items.LEATHER_TANNED_CUT);


    public BTWRRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter)
    {

        generateShapelessRecipes(exporter);
        generateShapedRecipes(exporter);

    }




    public static void generateShapelessRecipes(Consumer<RecipeJsonProvider> exporter)
    {
        // Shears cutting recipes
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_CUT,2).input(Items.LEATHER).input(BTWRConventionalTags.Items.SHEARS).criterion("has_leather", RecipeProvider.conditionsFromItem(Items.LEATHER)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_SCOURED_CUT,2).input(BTWR_Items.LEATHER_SCOURED).input(BTWRConventionalTags.Items.SHEARS).criterion("has_leather_scoured", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_SCOURED)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_TANNED_CUT,2).input(BTWR_Items.LEATHER_TANNED).input(BTWRConventionalTags.Items.SHEARS).criterion("has_leather_tanned", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_TANNED)).offerTo(exporter);


        // Other recipes
        offerShapelessRecipe(exporter, BTWR_Items.BRICK_UNFIRED, Items.CLAY_BALL, "group_btwr", 1);
        offerThreeInputShapelessRecipe(exporter, BTWR_Items.DIAMOND_INGOT, Items.IRON_INGOT, Items.DIAMOND, BTWR_Items.CREEPER_OYSTERS, "group_btwr",1);

        offerShapelessRecipe(exporter, BTWR_Items.HEMP_FIBERS, BTWR_Items.ROPE, "group_btwr", 6);
        offerShapelessRecipe(exporter, BTWR_Items.HEMP_FIBERS, BTWR_Items.HEMP_FABRIC, "group_btwr", 9);


    }
    public static void generateShapedRecipes(Consumer<RecipeJsonProvider> exporter)
    {
        // Tools
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.CLUB_BONE).input('#', Items.BONE).pattern("#").pattern("#").criterion("has_bone", RecipeProvider.conditionsFromItem(Items.BONE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.CLUB_WOOD).input('#', Items.STICK).pattern("#").pattern("#").criterion("has_stick", RecipeProvider.conditionsFromItem(Items.STICK)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, BTWR_Items.DIAMOND_SHEARS).input('#', BTWR_Items.DIAMOND_INGOT).pattern(" #").pattern("# ").criterion("has_diamond_ingot", RecipeProvider.conditionsFromItem(BTWR_Items.DIAMOND_INGOT)).offerTo(exporter);

        // Armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.LEATHER_HELMET).input('X', BTWR_Items.LEATHER_CUT).pattern("XXX").pattern("X X").criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.LEATHER_CHESTPLATE).input('X', BTWR_Items.LEATHER_CUT).pattern("X X").pattern("XXX").pattern("XXX").criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.LEATHER_LEGGINGS).input('X', BTWR_Items.LEATHER_CUT).pattern("XXX").pattern("X X").pattern("X X").criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.LEATHER_BOOTS).input('X', BTWR_Items.LEATHER_CUT).pattern("X X").pattern("X X").criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);

        // Misc
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.ROPE).input('#', BTWR_Items.HEMP_FIBERS).pattern("##").pattern("##").pattern("##").criterion("has_hemp_fibers", RecipeProvider.conditionsFromItem(BTWR_Items.HEMP_FIBERS)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.HEMP_FABRIC).input('#', BTWR_Items.HEMP_FIBERS).pattern("###").pattern("###").pattern("###").criterion("has_hemp_fibers", RecipeProvider.conditionsFromItem(BTWR_Items.HEMP_FIBERS)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.BELT).input('#', BTWR_Items.STRAP).pattern(" # ").pattern("# #").pattern(" # ").criterion("has_strap", RecipeProvider.conditionsFromItem(BTWR_Items.STRAP)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.GEAR,2).input('#', ItemTags.PLANKS).input('I', Items.STICK).pattern(" I ").pattern("I#I").pattern(" I ").criterion("has_stick", RecipeProvider.conditionsFromItem(Items.STICK)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LEATHER_HORSE_ARMOR).input('#', BTWR_Items.LEATHER_CUT).pattern("# #").pattern("###").pattern("# #").criterion("has_leather_cut", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.ITEM_FRAME).input('#', BTWR_Items.LEATHER_CUT).input('S', Items.STICK).pattern("SSS").pattern("S#S").pattern("SSS").criterion("has_leather_cut", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);
    }






    public static void offerTwoInputShapelessRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input,  ItemConvertible input2, @Nullable String group, int outputCount) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, outputCount)
                .input(input).input(input2)
                .group(group)
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, convertBetween(output, input));
    }
    public static void offerThreeInputShapelessRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input,  ItemConvertible input2, ItemConvertible input3, @Nullable String group, int outputCount) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, outputCount)
                .input(input).input(input2).input(input3)
                .group(group)
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, convertBetween(output, input));
    }
    public static void offerFourInputShapelessRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input,  ItemConvertible input2, ItemConvertible input3,ItemConvertible input4, @Nullable String group, int outputCount) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, outputCount)
                .input(input).input(input2).input(input3).input(input4)
                .group(group)
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter,  convertBetween(output, input));
    }

}
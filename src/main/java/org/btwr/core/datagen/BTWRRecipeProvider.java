package org.btwr.core.datagen;

import org.btwr.core.BTWRMod;
import org.btwr.core.block.BTWR_Blocks;
import org.btwr.core.item.BTWR_Items;
import org.btwr.core.tag.BTWRTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.btwr.shared_library.recipe.ExtendedShapelessRecipe;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.server.recipe.CookingRecipeJsonBuilder.*;

public class BTWRRecipeProvider extends FabricRecipeProvider
{

    public BTWRRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        // Food recipes
        this.registerFoodRecipes(exporter);

        // Tools
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.CLUB_BONE).input('#', Items.BONE).pattern("#").pattern("#").criterion("has_bone", RecipeProvider.conditionsFromItem(Items.BONE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.CLUB_WOOD).input('#', Items.STICK).pattern("#").pattern("#").criterion("has_stick", RecipeProvider.conditionsFromItem(Items.STICK)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, BTWR_Items.DIAMOND_SHEARS).input('#', BTWR_Items.DIAMOND_INGOT).pattern(" #").pattern("# ").criterion("has_diamond_ingot", RecipeProvider.conditionsFromItem(BTWR_Items.DIAMOND_INGOT)).offerTo(exporter);

        // Armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.LEATHER_HELMET).input('X', BTWR_Items.LEATHER_CUT).pattern("XXX").pattern("X X").criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.LEATHER_CHESTPLATE).input('X', BTWR_Items.LEATHER_CUT).pattern("X X").pattern("XXX").pattern("XXX").criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.LEATHER_LEGGINGS).input('X', BTWR_Items.LEATHER_CUT).pattern("XXX").pattern("X X").pattern("X X").criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.LEATHER_BOOTS).input('X', BTWR_Items.LEATHER_CUT).pattern("X X").pattern("X X").criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.DIAMOND_PLATE,4).input('X', BTWR_Items.DIAMOND_INGOT).input('L', BTWR_Items.LEATHER_TANNED).input('W', ItemTags.WOOL).pattern("LXL").pattern(" W ").criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.DIAMOND_PLATE,4).input('X', BTWR_Items.DIAMOND_INGOT).input('L', BTWR_Items.LEATHER_TANNED_CUT).input('W', ItemTags.WOOL).pattern("LXL").pattern(" W ").criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT)).offerTo(exporter, Identifier.of(BTWRMod.MOD_ID, "diamond_plate_from_tanned_leather_cut"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_HELMET).input('X', BTWR_Items.DIAMOND_INGOT).pattern("XXX").pattern("X X").criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_CHESTPLATE).input('X', BTWR_Items.DIAMOND_INGOT).input('P', BTWR_Items.DIAMOND_PLATE).pattern("P P").pattern("XXX").pattern("XXX").criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_LEGGINGS).input('X', BTWR_Items.DIAMOND_INGOT).input('P', BTWR_Items.DIAMOND_PLATE).pattern("XXX").pattern("P P").pattern("P P").criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_BOOTS).input('X', BTWR_Items.DIAMOND_INGOT).pattern("X X").pattern("X X").criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT)).offerTo(exporter);

        // Misc
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LEATHER_HORSE_ARMOR).input('#', BTWR_Items.LEATHER_CUT).pattern("# #").pattern("###").pattern("# #").criterion("has_leather_cut", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.ITEM_FRAME).input('#', BTWR_Items.LEATHER_CUT).input('S', Items.STICK).pattern("SSS").pattern("S#S").pattern("SSS").criterion("has_leather_cut", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.BOOK).input(BTWR_Items.LEATHER_CUT).input(Items.PAPER).input(Items.PAPER).input(Items.PAPER).criterion("has_paper", conditionsFromItem(Items.PAPER)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.LEATHER_TANNED_HELMET).input('X', BTWRTags.Items.TANNED_LEATHERS).pattern("XXX").pattern("X X").criterion("has_leather_tanned", conditionsFromItem(BTWR_Items.LEATHER_TANNED)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.LEATHER_TANNED_CHESTPLATE).input('X', BTWRTags.Items.TANNED_LEATHERS).pattern("X X").pattern("XXX").pattern("XXX").criterion("has_leather_tanned", conditionsFromItem(BTWR_Items.LEATHER_TANNED)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.LEATHER_TANNED_LEGGINGS).input('X', BTWRTags.Items.TANNED_LEATHERS).pattern("XXX").pattern("X X").pattern("X X").criterion("has_leather_tanned", conditionsFromItem(BTWR_Items.LEATHER_TANNED)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.LEATHER_TANNED_BOOTS).input('X', BTWRTags.Items.TANNED_LEATHERS).pattern("X X").pattern("X X").criterion("has_leather_tanned", conditionsFromItem(BTWR_Items.LEATHER_TANNED)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.OCULAR_OF_ENDER).input('E', Items.ENDER_EYE).input('N', Items.GOLD_NUGGET).pattern("NNN").pattern("NEN").pattern("NNN").criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.ENDER_SPECTACLES).input('O', BTWR_Items.OCULAR_OF_ENDER).input('L', ConventionalItemTags.LEATHERS).pattern("OLO").criterion(hasItem(BTWR_Items.OCULAR_OF_ENDER), conditionsFromItem(BTWR_Items.OCULAR_OF_ENDER)).offerTo(exporter);

        // Shears cutting recipes
        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_CUT,2).withToolDamage().input(Items.LEATHER).input(ConventionalItemTags.SHEAR_TOOLS).criterion("has_leather", conditionsFromItem(Items.LEATHER)).offerTo(exporter);
        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_SCOURED_CUT,2).withToolDamage().input(BTWR_Items.LEATHER_SCOURED).input(ConventionalItemTags.SHEAR_TOOLS).criterion("has_leather_scoured", conditionsFromItem(BTWR_Items.LEATHER_SCOURED)).offerTo(exporter);
        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_TANNED_CUT,2).withToolDamage().input(BTWR_Items.LEATHER_TANNED).input(ConventionalItemTags.SHEAR_TOOLS).criterion("has_leather_tanned", conditionsFromItem(BTWR_Items.LEATHER_TANNED)).offerTo(exporter);

        // Other recipes
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.DIAMOND_INGOT).input(Items.IRON_INGOT).input(Items.DIAMOND).input(BTWR_Items.CREEPER_OYSTERS).criterion("has_diamond", RecipeProvider.conditionsFromItem(Items.DIAMOND)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_SCOURED).input(Items.LEATHER).input(Items.WATER_BUCKET).criterion("has_water_bucket", RecipeProvider.conditionsFromItem(Items.WATER_BUCKET)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_TANNED).input(BTWR_Items.LEATHER_SCOURED).input(ItemTags.LOGS).criterion("has_leather_scoured", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_SCOURED)).offerTo(exporter);

        // Blocks
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Blocks.FLINT_BLOCK).input('F', Items.FLINT).pattern("FFF").pattern("FFF").pattern("FFF").criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Blocks.DIAMOND_INGOT_BLOCK).input('I', BTWR_Items.DIAMOND_INGOT).pattern("III").pattern("III").pattern("III").criterion(hasItem(BTWR_Items.DIAMOND_INGOT), conditionsFromItem(BTWR_Items.DIAMOND_INGOT)).offerTo(exporter);
    }

    private void registerFoodRecipes(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.EGG_SCRAMBLED_RAW,2).input(Items.EGG).input(Items.MILK_BUCKET).criterion("has_egg", RecipeProvider.conditionsFromItem(Items.EGG)).offerTo(exporter);
        createSmelting(Ingredient.ofItems(BTWR_Items.EGG_SCRAMBLED_RAW), RecipeCategory.FOOD, BTWR_Items.EGG_SCRAMBLED_COOKED, 0.2f, 200).criterion("has_egg", conditionsFromItem(Items.EGG)).offerTo(exporter);
        createCampfireCooking(Ingredient.ofItems(BTWR_Items.EGG_SCRAMBLED_RAW), RecipeCategory.FOOD, BTWR_Items.EGG_SCRAMBLED_COOKED, 0.30f, 600).criterion("has_egg", conditionsFromItem(Items.EGG)).offerTo(exporter, Identifier.ofVanilla("egg_scrambled_cooked_from_campfire_cooking"));
        createSmoking(Ingredient.ofItems(BTWR_Items.EGG_SCRAMBLED_RAW), RecipeCategory.FOOD, BTWR_Items.EGG_SCRAMBLED_COOKED, 0.30f, 100).criterion("has_egg", conditionsFromItem(Items.EGG)).offerTo(exporter, Identifier.ofVanilla("egg_scrambled_cooked_from_smoking"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.MUSHROOM_OMELETTE_RAW,2).input('E', Items.EGG).input('M', Items.BROWN_MUSHROOM).pattern("EM").pattern("MM").criterion("has_brown_mushroom", RecipeProvider.conditionsFromItem(Items.BROWN_MUSHROOM)).offerTo(exporter);
        createSmelting(Ingredient.ofItems(BTWR_Items.MUSHROOM_OMELETTE_RAW), RecipeCategory.FOOD, BTWR_Items.MUSHROOM_OMELETTE_COOKED, 0.2f, 200).criterion("has_raw_omelette", conditionsFromItem(BTWR_Items.MUSHROOM_OMELETTE_RAW)).offerTo(exporter);
        createCampfireCooking(Ingredient.ofItems(BTWR_Items.MUSHROOM_OMELETTE_RAW), RecipeCategory.FOOD, BTWR_Items.MUSHROOM_OMELETTE_COOKED, 0.30f, 600).criterion("has_raw_omelette", conditionsFromItem(BTWR_Items.MUSHROOM_OMELETTE_RAW)).offerTo(exporter, Identifier.ofVanilla("mushroom_omelette_cooked_from_campfire_cooking"));
        createSmoking(Ingredient.ofItems(BTWR_Items.MUSHROOM_OMELETTE_RAW), RecipeCategory.FOOD, BTWR_Items.MUSHROOM_OMELETTE_COOKED, 0.30f, 100).criterion("has_raw_omelette", conditionsFromItem(BTWR_Items.MUSHROOM_OMELETTE_RAW)).offerTo(exporter, Identifier.ofVanilla("mushroom_omelette_cooked_from_smoking"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.SANDWICH,2).input(Items.BREAD).input(BTWRConventionalTags.Items.COOKED_MEATS_FOR_SANDWICH).criterion("has_bread", RecipeProvider.conditionsFromItem(Items.BREAD)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.HAM_AND_EGGS,2).input(Items.COOKED_PORKCHOP).input(BTWRConventionalTags.Items.COOKED_EGG_FOODS).criterion("has_cooked_egg", RecipeProvider.conditionsFromTag(BTWRConventionalTags.Items.COOKED_EGG_FOODS)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.STEAK_AND_POTATOES,2).input(Items.COOKED_BEEF).input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS).criterion("has_cooked_potato", RecipeProvider.conditionsFromTag(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.CHOWDER).input(ConventionalItemTags.COOKED_FISH_FOODS).input(Items.MILK_BUCKET).input(Items.BOWL).criterion("has_cooked_potato", RecipeProvider.conditionsFromTag(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)).offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.RAW_KEBAB,3).input(Items.CARROT).input(Items.BROWN_MUSHROOM).input(Items.MUTTON).input(Items.STICK).criterion("has_carrot", RecipeProvider.conditionsFromItem(Items.CARROT)).offerTo(exporter);
        createSmelting(Ingredient.ofItems(BTWR_Items.RAW_KEBAB), RecipeCategory.FOOD, BTWR_Items.COOKED_KEBAB, 0.30f, 200).criterion("has_raw_kebab", conditionsFromItem(BTWR_Items.RAW_KEBAB)).offerTo(exporter);
        createSmoking(Ingredient.ofItems(BTWR_Items.RAW_KEBAB), RecipeCategory.FOOD, BTWR_Items.COOKED_KEBAB, 0.40f, 100).criterion("has_raw_kebab", conditionsFromItem(BTWR_Items.RAW_KEBAB)).offerTo(exporter, Identifier.ofVanilla("cooked_kebab_from_smoking"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.STEAK_DINNER,3).input(Items.COOKED_BEEF).input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS).input(Items.CARROT).criterion("has_carrot", conditionsFromItem(Items.CARROT)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.PORK_DINNER,3).input(Items.COOKED_PORKCHOP).input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS).input(Items.CARROT).criterion("has_carrot", RecipeProvider.conditionsFromItem(Items.CARROT)).offerTo(exporter);
        //ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.WOLF_DINNER,3).input(BwtItems.cookedWolfChopItem).input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS).input(Items.CARROT).criterion("has_carrot", RecipeProvider.conditionsFromItem(Items.CARROT)).offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.CHICKEN_SOUP,1).input(Items.COOKED_CHICKEN).input(Items.CARROT).input(Items.BAKED_POTATO).input(Items.BOWL).criterion("has_baked_potato", RecipeProvider.conditionsFromItem(Items.BAKED_POTATO)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.HEARTY_STEW,1).input(ConventionalItemTags.COOKED_MEAT_FOODS).input(Items.CARROT).input(Items.BAKED_POTATO).input(Items.BROWN_MUSHROOM).input(Items.BOWL).criterion("has_carrot", conditionsFromItem(Items.CARROT)).offerTo(exporter);
    }

}
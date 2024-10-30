package btwr.core.datagen;

import btwr.core.BTWRMod;
import btwr.core.block.BTWR_Blocks;
import btwr.core.item.BTWR_Items;
import btwr.core.tag.BTWRConventionalTags;
import btwr.core.tag.BTWRTags;
import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class BTWRRecipeProvider extends FabricRecipeProvider
{

    public BTWRRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    public void generate(RecipeExporter exporter)
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
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.DIAMOND_PLATE).input('S', BTWR_Items.STRAP).input('I', BTWR_Items.DIAMOND_INGOT).pattern("SIS").criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT)).offerTo(exporter);

        // Misc
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.ROPE).input('#', BTWR_Items.HEMP_FIBERS).pattern("##").pattern("##").pattern("##").criterion("has_hemp_fibers", RecipeProvider.conditionsFromItem(BTWR_Items.HEMP_FIBERS)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.HEMP_FABRIC).input('#', BTWR_Items.HEMP_FIBERS).pattern("###").pattern("###").pattern("###").criterion("has_hemp_fibers", RecipeProvider.conditionsFromItem(BTWR_Items.HEMP_FIBERS)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.BELT).input('#', BTWR_Items.STRAP).pattern(" # ").pattern("# #").pattern(" # ").criterion("has_strap", RecipeProvider.conditionsFromItem(BTWR_Items.STRAP)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.GEAR,2).input('#', ItemTags.PLANKS).input('I', Items.STICK).pattern(" I ").pattern("I#I").pattern(" I ").criterion("has_stick", RecipeProvider.conditionsFromItem(Items.STICK)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LEATHER_HORSE_ARMOR).input('#', BTWR_Items.LEATHER_CUT).pattern("# #").pattern("###").pattern("# #").criterion("has_leather_cut", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.ITEM_FRAME).input('#', BTWR_Items.LEATHER_CUT).input('S', Items.STICK).pattern("SSS").pattern("S#S").pattern("SSS").criterion("has_leather_cut", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_CUT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Blocks.ROPE_COIL).input('#', BTWR_Items.ROPE).pattern("###").pattern("###").pattern("###").criterion("has_rope", RecipeProvider.conditionsFromItem(BTWR_Items.ROPE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Blocks.LIGHTBLOCK).input('#', Blocks.GLASS_PANE).input('F', BTWR_Items.FILAMENT).input('R', Items.REDSTONE).pattern(" # ").pattern("#F#").pattern(" R ").criterion("has_filament", RecipeProvider.conditionsFromItem(BTWR_Items.FILAMENT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.LEATHER_TANNED_HELMET).input('X', BTWRTags.Items.TANNED_LEATHERS).pattern("XXX").pattern("X X").criterion("has_leather_tanned", conditionsFromItem(BTWR_Items.LEATHER_TANNED)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.LEATHER_TANNED_CHESTPLATE).input('X', BTWRTags.Items.TANNED_LEATHERS).pattern("X X").pattern("XXX").pattern("XXX").criterion("has_leather_tanned", conditionsFromItem(BTWR_Items.LEATHER_TANNED)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.LEATHER_TANNED_LEGGINGS).input('X', BTWRTags.Items.TANNED_LEATHERS).pattern("XXX").pattern("X X").pattern("X X").criterion("has_leather_tanned", conditionsFromItem(BTWR_Items.LEATHER_TANNED)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BTWR_Items.LEATHER_TANNED_BOOTS).input('X', BTWRTags.Items.TANNED_LEATHERS).pattern("X X").pattern("X X").criterion("has_leather_tanned", conditionsFromItem(BTWR_Items.LEATHER_TANNED)).offerTo(exporter);

        // Shears cutting recipes
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_CUT,2).input(Items.LEATHER).input(BTWRConventionalTags.Items.SHEARS).criterion("has_leather", RecipeProvider.conditionsFromItem(Items.LEATHER)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_SCOURED_CUT,2).input(BTWR_Items.LEATHER_SCOURED).input(BTWRConventionalTags.Items.SHEARS).criterion("has_leather_scoured", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_SCOURED)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_TANNED_CUT,2).input(BTWR_Items.LEATHER_TANNED).input(BTWRConventionalTags.Items.SHEARS).criterion("has_leather_tanned", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_TANNED)).offerTo(exporter);

        // Food recipes
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.BOOK).input(BTWR_Items.LEATHER_CUT).input(Items.PAPER).input(Items.PAPER).input(Items.PAPER).criterion("has_paper", conditionsFromItem(Items.PAPER)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.SANDWICH,2).input(Items.BREAD).input(BTWRTags.Items.MEATS_FOR_SANDWICH).criterion("has_bread", RecipeProvider.conditionsFromItem(Items.BREAD)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.HAM_AND_EGGS,2).input(Items.COOKED_PORKCHOP).input(BTWRConventionalTags.Items.COOKED_EGG_FOODS).criterion("has_cooked_egg", RecipeProvider.conditionsFromTag(BTWRConventionalTags.Items.COOKED_EGG_FOODS)).offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.EGG_SCRAMBLED_RAW,2).input(BwtItems.rawEggItem).input(Items.MILK_BUCKET).criterion("has_raw_egg", RecipeProvider.conditionsFromItem(BwtItems.rawEggItem)).offerTo(exporter);
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(BTWR_Items.EGG_SCRAMBLED_RAW), RecipeCategory.FOOD, BTWR_Items.EGG_SCRAMBLED_COOKED, 0.2f, 200).criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem)).offerTo(exporter);
        CookingRecipeJsonBuilder.createCampfireCooking(Ingredient.ofItems(BTWR_Items.EGG_SCRAMBLED_RAW), RecipeCategory.FOOD, BTWR_Items.EGG_SCRAMBLED_COOKED, 0.30f, 600).criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem)).offerTo(exporter, Identifier.ofVanilla("egg_scrambled_cooked_from_campfire_cooking"));
        CookingRecipeJsonBuilder.createSmoking(Ingredient.ofItems(BTWR_Items.EGG_SCRAMBLED_RAW), RecipeCategory.FOOD, BTWR_Items.EGG_SCRAMBLED_COOKED, 0.30f, 100).criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem)).offerTo(exporter, Identifier.ofVanilla("egg_scrambled_cooked_from_smoking"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.MUSHROOM_OMELETTE_RAW,2).input('E', BwtItems.rawEggItem).input('M', Items.BROWN_MUSHROOM).pattern("EM").pattern("MM").criterion("has_brown_mushroom", RecipeProvider.conditionsFromItem(Items.BROWN_MUSHROOM)).offerTo(exporter);
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(BTWR_Items.MUSHROOM_OMELETTE_RAW), RecipeCategory.FOOD, BTWR_Items.MUSHROOM_OMELETTE_COOKED, 0.2f, 200).criterion("has_raw_omelette", conditionsFromItem(BTWR_Items.MUSHROOM_OMELETTE_RAW)).offerTo(exporter);
        CookingRecipeJsonBuilder.createCampfireCooking(Ingredient.ofItems(BTWR_Items.MUSHROOM_OMELETTE_RAW), RecipeCategory.FOOD, BTWR_Items.MUSHROOM_OMELETTE_COOKED, 0.30f, 600).criterion("has_raw_omelette", conditionsFromItem(BTWR_Items.MUSHROOM_OMELETTE_RAW)).offerTo(exporter, Identifier.ofVanilla("mushroom_omelette_cooked_from_campfire_cooking"));
        CookingRecipeJsonBuilder.createSmoking(Ingredient.ofItems(BTWR_Items.MUSHROOM_OMELETTE_RAW), RecipeCategory.FOOD, BTWR_Items.MUSHROOM_OMELETTE_COOKED, 0.30f, 100).criterion("has_raw_omelette", conditionsFromItem(BTWR_Items.MUSHROOM_OMELETTE_RAW)).offerTo(exporter, Identifier.ofVanilla("mushroom_omelette_cooked_from_smoking"));

        // Other recipes
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.DIAMOND_INGOT).input(Items.IRON_INGOT).input(Items.DIAMOND).input(BTWR_Items.CREEPER_OYSTERS).criterion("has_diamond", RecipeProvider.conditionsFromItem(Items.DIAMOND)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.FILAMENT).input(Items.GLOWSTONE_DUST).input(Items.REDSTONE).input(BTWRConventionalTags.Items.STRING_TOOL_MATERIALS).criterion("has_filament", RecipeProvider.conditionsFromItem(BTWR_Items.FILAMENT)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_SCOURED).input(Items.LEATHER).input(Items.WATER_BUCKET).criterion("has_water_bucket", RecipeProvider.conditionsFromItem(Items.WATER_BUCKET)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_TANNED).input(BTWR_Items.LEATHER_SCOURED).input(ItemTags.LOGS).criterion("has_leather_scoured", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_SCOURED)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.STRAP,4).input(BTWR_Items.LEATHER_TANNED_CUT).input(BTWRConventionalTags.Items.SHEARS).criterion("has_leather_tanned_cut", RecipeProvider.conditionsFromItem(BTWR_Items.LEATHER_TANNED_CUT)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.BRICK_UNFIRED).input(Items.CLAY_BALL).criterion("has_clay_ball", conditionsFromItem(Items.CLAY_BALL)).offerTo(exporter);

        // Special export condition
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.HEMP_FIBERS, 6).input(BTWR_Items.ROPE).criterion("has_rope", RecipeProvider.conditionsFromItem(BTWR_Items.ROPE)).offerTo(exporter, Identifier.of(BTWRMod.MOD_ID, "hemp_fibers_from_rope"));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.HEMP_FIBERS, 9).input(BTWR_Items.HEMP_FABRIC).criterion("has_rope", RecipeProvider.conditionsFromItem(BTWR_Items.ROPE)).offerTo(exporter, Identifier.of(BTWRMod.MOD_ID, "hemp_fibers_from_hemp_fabric"));

    }



}
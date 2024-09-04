package btwr.core.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.collection.DefaultedList;

public class ExtendedShapelessRecipeFactory {
    public static ShapelessRecipe create(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients, DefaultedList<ItemStack> additionalDrops) {
        return new ExtendedShapelessRecipe(group, category, result, ingredients, additionalDrops);
    }
}

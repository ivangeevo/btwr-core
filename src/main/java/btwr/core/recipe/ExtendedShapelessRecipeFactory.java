package btwr.core.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class ExtendedShapelessRecipeFactory {
    public static ExtendedShapelessRecipe create(String group, CraftingRecipeCategory category, ItemStack result, List<Ingredient> ingredients, List<Ingredient> additionalDrops) {
        return new ExtendedShapelessRecipe(group, category, result, DefaultedList.copyOf(Ingredient.EMPTY, ingredients.toArray(new Ingredient[0])), DefaultedList.copyOf(Ingredient.EMPTY, additionalDrops.toArray(new Ingredient[0])));
    }
}

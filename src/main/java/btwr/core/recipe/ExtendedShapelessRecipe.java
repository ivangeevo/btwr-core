package btwr.core.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.recipe.Ingredient;

public class ExtendedShapelessRecipe extends ShapelessRecipe
{
    private final DefaultedList<Ingredient> additionalDrops;

    public ExtendedShapelessRecipe(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients, DefaultedList<Ingredient> additionalDrops) {
        super(group, category, result, ingredients);
        this.additionalDrops = additionalDrops;
    }

    public DefaultedList<Ingredient> getAdditionalDrops() {
        return this.additionalDrops;
    }

}

package btwr.core.recipe;

import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.collection.DefaultedList;

public class ExtendedShapelessRecipe extends ShapelessRecipe implements ShapelessRecipeAdded
{
    private DefaultedList<Ingredient> additionalDrops;

    public ExtendedShapelessRecipe(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients, DefaultedList<Ingredient> additionalDrops) {
        super(group, category, result, ingredients);
        this.additionalDrops = additionalDrops;
    }

    @Override
    public DefaultedList<Ingredient> getAdditionalDrops() {
        return this.additionalDrops;
    }

    @Override
    public void setAdditionalDrops(DefaultedList<Ingredient> additionalDrops) {
        this.additionalDrops = additionalDrops;
    }


}

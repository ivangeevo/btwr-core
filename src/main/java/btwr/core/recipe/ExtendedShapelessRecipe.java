package btwr.core.recipe;

import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.collection.DefaultedList;

public class ExtendedShapelessRecipe extends ShapelessRecipe implements ShapelessRecipeAdded
{
    private DefaultedList<ItemStack> additionalDrops;

    public ExtendedShapelessRecipe(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients, DefaultedList<ItemStack> additionalDrops) {
        super(group, category, result, ingredients);
        this.additionalDrops = additionalDrops;
    }

    @Override
    public DefaultedList<ItemStack> getAdditionalDrops() {
        return this.additionalDrops;
    }

    @Override
    public void setAdditionalDrops(DefaultedList<ItemStack> additionalDrops) {
        this.additionalDrops = additionalDrops;
    }


}

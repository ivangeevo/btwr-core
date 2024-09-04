package btwr.core.recipe.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;

public interface ShapelessRecipeAdded
{

    DefaultedList<ItemStack> getAdditionalDrops();

    void setAdditionalDrops(DefaultedList<ItemStack> secondaryOutput);



}

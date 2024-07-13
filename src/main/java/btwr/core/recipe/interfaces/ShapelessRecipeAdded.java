package btwr.core.recipe.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;

public interface ShapelessRecipeAdded {

    DefaultedList<ItemStack> getSecondaryOutput();

    void setSecondaryOutput(DefaultedList<ItemStack> secondaryOutput);

}

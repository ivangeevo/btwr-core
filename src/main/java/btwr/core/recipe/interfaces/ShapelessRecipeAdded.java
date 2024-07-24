package btwr.core.recipe.interfaces;

import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;

public interface ShapelessRecipeAdded
{

    DefaultedList<Ingredient> getAdditonalDrops();

    void setAdditionalDrops(DefaultedList<Ingredient> secondaryOutput);

}

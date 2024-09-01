package btwr.core.recipe.interfaces;

import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.recipe.Ingredient;

public interface ShapelessRecipeJsonBuilderAdded
{

     ShapelessRecipeJsonBuilder additionalDrop(Ingredient ingredient);

     ShapelessRecipeJsonBuilder additionalDrop(Ingredient ingredient, int size);



}

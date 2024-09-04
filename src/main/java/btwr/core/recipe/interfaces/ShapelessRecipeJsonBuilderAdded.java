package btwr.core.recipe.interfaces;

import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemStack;

public interface ShapelessRecipeJsonBuilderAdded
{

     ShapelessRecipeJsonBuilder additionalDrop(ItemStack ingredient);

     ShapelessRecipeJsonBuilder additionalDrop(ItemStack ingredient, int size);



}

package btwr.core.recipe;

import btwr.core.BTWRMod;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipesRegistry
{
    public static void registerModRecipes()
    {

        Registry.register(Registries.RECIPE_SERIALIZER, ShapelessRecipeWithDrops.Serializer.ID,
                ShapelessRecipeWithDrops.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(BTWRMod.MOD_ID,
                ShapelessRecipeWithDrops.Type.ID), ShapelessRecipeWithDrops.Type.INSTANCE);

    }
}

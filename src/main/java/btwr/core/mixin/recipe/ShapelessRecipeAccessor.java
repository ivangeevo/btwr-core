package btwr.core.mixin.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ShapelessRecipe.class)
public interface ShapelessRecipeAccessor {
    @Accessor("result")
    ItemStack getResult();

    @Accessor("result")
    void setResult(ItemStack result);

    @Accessor("ingredients")
    DefaultedList<Ingredient> getIngredients();

    @Accessor("ingredients")
    void setIngredients(DefaultedList<Ingredient> ingredients);}

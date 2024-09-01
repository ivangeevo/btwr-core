package btwr.core.mixin.recipe;

import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ShapelessRecipe.class)
public abstract class ShapelessRecipeMixin implements ShapelessRecipeAdded
{
    @Unique
    DefaultedList<Ingredient> additionalDrops;

    @Override
    public DefaultedList<Ingredient> getAdditionalDrops() {
        return this.additionalDrops;
    }

    @Override
    public void setAdditionalDrops(DefaultedList<Ingredient> drops)
    {
        this.additionalDrops = drops;
    }
}

package btwr.core.mixin.recipe;

import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ShapelessRecipe.class)
public abstract class ShapelessRecipeMixin implements ShapelessRecipeAdded {

     @Unique private DefaultedList<ItemStack> secondaryDrops;
    @Override
    public void setSecondaryOutput(DefaultedList<ItemStack> secondaryOutput) {
        this.secondaryDrops = secondaryOutput;
    }
    @Override
    public DefaultedList<ItemStack> getSecondaryOutput() {
        return this.secondaryDrops;
    }


}

package btwr.core.mixin.recipe;

import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShapelessRecipe.class)
public abstract class ShapelessRecipeMixin implements ShapelessRecipeAdded
{
    @Unique
    DefaultedList<ItemStack> additionalDrops;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectedConstructor(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<ShapelessRecipe> ingredients, CallbackInfo ci)
    {
        this.additionalDrops = getAdditionalDrops();
    }


    @Override
    public DefaultedList<ItemStack> getAdditionalDrops() {
        return this.additionalDrops;
    }

    @Override
    public void setAdditionalDrops(DefaultedList<ItemStack> drops)
    {
        this.additionalDrops = drops;
    }
}

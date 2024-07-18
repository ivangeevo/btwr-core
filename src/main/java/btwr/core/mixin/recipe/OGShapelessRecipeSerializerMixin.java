package btwr.core.mixin.recipe;

import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapelessRecipe.Serializer.class)
public abstract class OGShapelessRecipeSerializerMixin
{

    /**

    @Inject(method = "read(Lnet/minecraft/network/RegistryByteBuf;)Lnet/minecraft/recipe/ShapelessRecipe;", at = @At("RETURN"), cancellable = true)
    private static void read(RegistryByteBuf buf, CallbackInfoReturnable<ShapelessRecipe> cir)
    {
        ShapelessRecipe recipe = cir.getReturnValue();
        DefaultedList<ItemStack> defaultedList = getSecondaryDrops(buf);
        ((ShapelessRecipeAdded) recipe).setSecondaryOutput(defaultedList);

        cir.setReturnValue(recipe);

    }

    @Inject(method = "write(Lnet/minecraft/network/RegistryByteBuf;Lnet/minecraft/recipe/ShapelessRecipe;)V", at = @At("TAIL"))
    private static void writeBuf(RegistryByteBuf buf, ShapelessRecipe recipe, CallbackInfo ci) {
        DefaultedList<ItemStack> secondaryDrops = ((ShapelessRecipeAdded) recipe).getSecondaryOutput();
        for (ItemStack droppedStack: secondaryDrops)
        {
            ItemStack.PACKET_CODEC.encode(buf, droppedStack);
        }
    }

    @Unique
    private static DefaultedList<ItemStack> getSecondaryDrops(RegistryByteBuf buf) {
        DefaultedList<ItemStack> ingredients = DefaultedList.of();

        int ingredientCount = buf.readVarInt();
        for (int i = 0; i < ingredientCount; ++i) {
            ingredients.add(ItemStack.PACKET_CODEC.decode(buf));
        }

        return ingredients;
    }
    **/

}

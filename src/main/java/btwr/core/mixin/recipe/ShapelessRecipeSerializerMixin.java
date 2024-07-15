package btwr.core.mixin.recipe;

import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapelessRecipe.Serializer.class)
public abstract class ShapelessRecipeSerializerMixin {

    @Inject(method = "read(Lnet/minecraft/network/RegistryByteBuf;)Lnet/minecraft/recipe/ShapelessRecipe;", at = @At("RETURN"), cancellable = true)
    private static void read(RegistryByteBuf buf, CallbackInfoReturnable<ShapelessRecipe> cir) {
        ShapelessRecipe shapelessRecipe = cir.getReturnValue();
        buf.readString();
        DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(buf.readVarInt(), Ingredient.EMPTY);
        defaultedList.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));


        ((ShapelessRecipeAdded) shapelessRecipe).setSecondaryOutput(defaultedList);

        cir.setReturnValue(shapelessRecipe);

    }

    @Inject(method = "write(Lnet/minecraft/network/RegistryByteBuf;Lnet/minecraft/recipe/ShapelessRecipe;)V", at = @At("TAIL"))
    private static void writeBuf(RegistryByteBuf buf, ShapelessRecipe recipe, CallbackInfo ci) {
        DefaultedList<Ingredient> secondaryDrops = ((ShapelessRecipeAdded) recipe).getSecondaryOutput();

        buf.writeString("secondaryResult");
        buf.writeVarInt(secondaryDrops.size());

        for (Ingredient drop : secondaryDrops) {
            Ingredient.PACKET_CODEC.encode(buf, drop);
        }
        Ingredient.PACKET_CODEC.encode(buf, getSecondaryDrops(buf).getFirst() );
    }

    @Unique
    private static DefaultedList<Ingredient> getSecondaryDrops(RegistryByteBuf buf) {
        DefaultedList<Ingredient> ingredients = DefaultedList.of();

        int ingredientCount = buf.readVarInt();
        for (int i = 0; i < ingredientCount; ++i) {
            ingredients.add(Ingredient.PACKET_CODEC.decode(buf));
        }

        return ingredients;
    }

}

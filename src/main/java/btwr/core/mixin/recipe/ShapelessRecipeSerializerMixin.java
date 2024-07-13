package btwr.core.mixin.recipe;

import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapelessRecipe.Serializer.class)
public abstract class ShapelessRecipeSerializerMixin {

    /**
    @Inject(method = "read(Lnet/minecraft/network/RegistryByteBuf;)Lnet/minecraft/recipe/ShapelessRecipe;",
            at = @At("RETURN"), cancellable = true)
    protected static void read(RegistryByteBuf buf, CallbackInfoReturnable<ShapelessRecipe> cir)
    {
        handleRecipeSerialization(cir, buf);
    }

    @Inject(method = "write(Lnet/minecraft/network/RegistryByteBuf;Lnet/minecraft/recipe/ShapelessRecipe;)V", at = @At("TAIL"))
    protected static void writeBuf(RegistryByteBuf buf, ShapelessRecipe recipe, CallbackInfo ci) {
        DefaultedList<ItemStack> secondaryDrops = ((ShapelessRecipeAdded) recipe).getSecondaryOutput();
        buf.writeVarInt(secondaryDrops.size());
        for (ItemStack secondary : secondaryDrops)
        {
            Ingredient.PACKET_CODEC.encode(buf, Ingredient.ofStacks(secondary));
        }
    }

    private static void handleRecipeSerialization(CallbackInfoReturnable<ShapelessRecipe> cir, RegistryByteBuf buf) {
        ShapelessRecipe shapelessRecipe = cir.getReturnValue();
        DefaultedList<ItemStack> defaultedList;

        if (parameter instanceof JsonObject)
        {
            try
            {
                defaultedList = getSecondaryDrops(JsonHelper.getArray((JsonObject) parameter, "secondaryResult"));
            }
            catch (JsonSyntaxException exception)
            {
                defaultedList = DefaultedList.of();
            }
        }
        else if (parameter instanceof PacketByteBuf)
        {
            int k = buf.readVarInt();

            defaultedList = DefaultedList.ofSize(k, ItemStack.EMPTY);
            defaultedList.replaceAll(empty -> (Ingredient)Ingredient.PACKET_CODEC.decode(buf));
        }
        else
        {
            return;
        }

        ((ShapelessRecipeAdded) shapelessRecipe).setSecondaryOutput(defaultedList);
        cir.setReturnValue(shapelessRecipe);
    }

    private static DefaultedList<ItemStack> getSecondaryDrops(JsonArray json) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();

        for (int i = 0; i < json.size(); ++i) {
            ItemStack itemStack = ShapedRecipe.Serializer.DR(JsonObject) json.get(i));
            if (!itemStack.isEmpty()) {
                defaultedList.add(itemStack);
            }
        }

        return defaultedList;
    }
    **/
}

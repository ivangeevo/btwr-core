package btwr.core.mixin.recipe;

import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
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

    @Inject(method = "read(Lnet/minecraft/util/Identifier;Lcom/google/gson/JsonObject;)Lnet/minecraft/recipe/ShapelessRecipe;",
            at = @At("RETURN"), cancellable = true)
    protected void read(Identifier identifier, JsonObject jsonObject, CallbackInfoReturnable<ShapelessRecipe> cir)
    {
        handleRecipeSerialization(cir, jsonObject);
    }

    @Inject(method = "read(Lnet/minecraft/util/Identifier;Lnet/minecraft/network/PacketByteBuf;)Lnet/minecraft/recipe/ShapelessRecipe;",
            at = @At("RETURN"), cancellable = true)
    protected void readBuf(Identifier identifier, PacketByteBuf packetByteBuf, CallbackInfoReturnable<ShapelessRecipe> cir) {
        handleRecipeSerialization(cir, packetByteBuf);
    }

    @Inject(method = "write(Lnet/minecraft/network/PacketByteBuf;Lnet/minecraft/recipe/ShapelessRecipe;)V", at = @At("TAIL"))
    protected void writeBuf(PacketByteBuf packetByteBuf, ShapelessRecipe shapelessRecipe, CallbackInfo ci) {
        DefaultedList<ItemStack> secondaryDrops = ((ShapelessRecipeAdded) shapelessRecipe).getSecondaryOutput();
        packetByteBuf.writeVarInt(secondaryDrops.size());
        for (ItemStack itemStack : secondaryDrops) {
            packetByteBuf.writeItemStack(itemStack);
        }
    }

    private static void handleRecipeSerialization(CallbackInfoReturnable<ShapelessRecipe> cir, Object parameter) {
        ShapelessRecipe shapelessRecipe = cir.getReturnValue();
        DefaultedList<ItemStack> defaultedList;

        if (parameter instanceof JsonObject) {
            try {
                defaultedList = getSecondaryDrops(JsonHelper.getArray((JsonObject) parameter, "secondaryResult"));
            } catch (JsonSyntaxException exception) {
                defaultedList = DefaultedList.of();
            }
        } else if (parameter instanceof PacketByteBuf) {
            int k = ((PacketByteBuf) parameter).readVarInt();
            defaultedList = DefaultedList.ofSize(k, ItemStack.EMPTY);
            defaultedList.replaceAll(ignored -> ((PacketByteBuf) parameter).readItemStack());
        } else {
            // Handle unsupported parameter type
            return;
        }

        ((ShapelessRecipeAdded) shapelessRecipe).setSecondaryOutput(defaultedList);
        cir.setReturnValue(shapelessRecipe);
    }

    private static DefaultedList<ItemStack> getSecondaryDrops(JsonArray json) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();

        for (int i = 0; i < json.size(); ++i) {
            ItemStack itemStack = ShapedRecipe.outputFromJson((JsonObject) json.get(i));
            if (!itemStack.isEmpty()) {
                defaultedList.add(itemStack);
            }
        }

        return defaultedList;
    }
}

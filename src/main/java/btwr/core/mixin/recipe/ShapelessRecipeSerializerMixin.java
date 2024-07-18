package btwr.core.mixin.recipe;

import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapelessRecipe.Serializer.class)
public abstract class ShapelessRecipeSerializerMixin implements RecipeSerializer<ShapelessRecipe> {

    /**
    @Shadow public abstract MapCodec<ShapelessRecipe> codec();

    @Unique
    private static final MapCodec<ShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter(ShapelessRecipe::getGroup),
                    CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(ShapelessRecipe::getCategory),
                    ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter((recipe) -> ((ShapelessRecipeAccessor)recipe).getResult()),
                    Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").flatXmap((ingredients) -> {
                        Ingredient[] ingredients2 = ingredients.stream().filter((ingredient) -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
        if (ingredients2.length == 0) { return DataResult.error(() -> "No ingredients for shapeless recipe"); }
        else { return ingredients2.length > 9 ? DataResult.error(() -> "Too many ingredients for shapeless recipe") : DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients2)); }
        },

                    DataResult::success).forGetter(ShapelessRecipe::getIngredients),
                    ItemStack.VALIDATED_CODEC.fieldOf("secondaryResult").forGetter((recipe) -> ((ShapelessRecipeAccessor)recipe).getResult())
                    ).apply(instance, ShapelessRecipe::new));

    @Inject(method = "codec", at = @At("RETURN"), cancellable = true)
    private void injectCodec(CallbackInfoReturnable<MapCodec<ShapelessRecipe>> cir) {
        cir.setReturnValue(CODEC);
    }

    @Inject(method = "read",
            at = @At("HEAD"), cancellable = true)
    protected static void onRead(RegistryByteBuf buf, CallbackInfoReturnable<ShapelessRecipe> cir)
    {
        ShapelessRecipe shapelessRecipe = cir.getReturnValue();
        DefaultedList<Ingredient> defaultedList;

            int k = buf.readVarInt();
            defaultedList = DefaultedList.ofSize(k, Ingredient.EMPTY);
            defaultedList.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));


        ((ShapelessRecipeAdded) shapelessRecipe).setSecondaryOutput(defaultedList);
        cir.setReturnValue(shapelessRecipe);
    }


    @Inject(method = "write", at = @At("TAIL"))
    protected static void onWrite(RegistryByteBuf buf, ShapelessRecipe recipe, CallbackInfo ci) {
        DefaultedList<Ingredient> secondaryDrops = ((ShapelessRecipeAdded) recipe).getSecondaryOutput();
        buf.writeVarInt(secondaryDrops.size());
        for (Ingredient drop : ((ShapelessRecipeAdded) recipe).getSecondaryOutput()) {
            Ingredient.PACKET_CODEC.encode(buf, drop);
        }
    }

    private static DefaultedList<Ingredient> getSecondaryDrops(RegistryByteBuf buf) {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();

        for (int i = 0; i < buf.readVarInt(); ++i) {
            Ingredient itemStack = Ingredient.PACKET_CODEC.encode(buf, );
            if (!itemStack.isEmpty()) {
                defaultedList.add(itemStack);
            }
        }

        return defaultedList;
    }
    **/
}

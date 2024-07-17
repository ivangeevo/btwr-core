package btwr.core.mixin.recipe;

import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
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
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapelessRecipe.Serializer.class)
public abstract class ShapelessRecipeSerializerMixin implements RecipeSerializer<ShapelessRecipe> {

    @Unique
    private static final MapCodec<ShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.STRING.optionalFieldOf("group", "")

                    .forGetter(ShapelessRecipe::getGroup),
            CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC)

                    .forGetter(ShapelessRecipe::getCategory),
            ItemStack.VALIDATED_CODEC.fieldOf("result")

                    .forGetter((recipe) -> ((ShapelessRecipeAccessor) recipe).getResult()),
            Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")

                    .flatXmap((ingredients) ->
                    {
                        Ingredient[] filteredIngredients = ingredients.stream()
                                .filter((ingredient) -> !ingredient.isEmpty())
                                .toArray(Ingredient[]::new);

                        if (filteredIngredients.length == 0)
                        {
                            return DataResult.error(() -> "No ingredients for shapeless recipe");
                        }
                        else
                        {
                            return filteredIngredients.length > 9
                                    ? DataResult.error(() -> "Too many ingredients for shapeless recipe")
                                    : DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, filteredIngredients));
                        }

                    }, DataResult::success)

                    .forGetter(ShapelessRecipe::getIngredients),
            ItemStack.VALIDATED_CODEC.listOf().optionalFieldOf("secondaryResult", DefaultedList.of())

                    .forGetter((recipe) -> ((ShapelessRecipeAdded) recipe).getSecondaryOutput())
    )
            .apply(instance, (group, category, result, ingredients, secondaryResult) -> {
        ShapelessRecipe recipe = new ShapelessRecipe(group, category, result, ingredients);
        ((ShapelessRecipeAdded) recipe).setSecondaryOutput((DefaultedList<ItemStack>) secondaryResult);
        return recipe;
    }));

    @Inject(method = "codec", at = @At("RETURN"), cancellable = true)
    private void injectCodec(CallbackInfoReturnable<MapCodec<ShapelessRecipe>> cir) {
        cir.setReturnValue(CODEC);
    }

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
}

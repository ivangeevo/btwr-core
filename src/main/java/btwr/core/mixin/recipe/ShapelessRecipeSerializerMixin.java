package btwr.core.mixin.recipe;

import btwr.core.recipe.ExtendedShapelessRecipe;
import btwr.core.recipe.ExtendedShapelessRecipeFactory;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Function;

@Mixin(ShapelessRecipe.Serializer.class)
public abstract class ShapelessRecipeSerializerMixin
{

    @Inject(method = "codec", at = @At("HEAD"), cancellable = true)
    public void injectGetCodec(CallbackInfoReturnable<MapCodec<ShapelessRecipe>> cir) {
        MapCodec<ShapelessRecipe> codec = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                Codec.STRING.optionalFieldOf("group", "")
                        .forGetter(ShapelessRecipe::getGroup),
                CraftingRecipeCategory.CODEC.fieldOf("category")
                        .orElse(CraftingRecipeCategory.MISC)
                        .forGetter(ShapelessRecipe::getCategory),
                ItemStack.CODEC.fieldOf("result")
                        .forGetter(recipe -> recipe.getResult(null)),
                Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")
                        .flatXmap(INGREDIENTS_VALIDATOR, DataResult::success)
                        .forGetter(ShapelessRecipe::getIngredients),
                Ingredient.ALLOW_EMPTY_CODEC.listOf()
                        .optionalFieldOf("additionalDrops", DefaultedList.of())
                        .flatXmap(ADDITIONAL_DROPS_VALIDATOR, DataResult::success)
                        .forGetter(recipe -> ((ExtendedShapelessRecipe)recipe).getAdditionalDrops())
            ).apply(instance, ExtendedShapelessRecipeFactory::create)
        );

        cir.setReturnValue(codec);
    }

    @Inject(method = "read", at = @At("RETURN"))
    private static void onRead(RegistryByteBuf buf, CallbackInfoReturnable<ShapelessRecipe> cir)
    {

    }

    // separated the ingredients and additional drops code for improved readability.
    @Unique
    private static final Function<List<Ingredient>, DataResult<DefaultedList<Ingredient>>>
            INGREDIENTS_VALIDATOR = ingredients ->
    {
        Ingredient[] ingredientsArray = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
        if (ingredientsArray.length == 0) {
            return DataResult.error(() -> "No ingredients for custom shapeless recipe");
        } else {
            return ingredientsArray.length > 9 ? DataResult.error(() -> "Too many ingredients for custom shapeless recipe") : DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredientsArray));
        }
    };
    @Unique
    private static final Function<List<Ingredient>, DataResult<DefaultedList<Ingredient>>>
            ADDITIONAL_DROPS_VALIDATOR = drops ->
    {
        Ingredient[] dropsArray = drops.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
        if (dropsArray.length == 0) {
            return DataResult.error(() -> "No additional drops specified for this shapeless recipe");
        } else {
            return dropsArray.length > 9 ? DataResult.error(() -> "Too many additional drops for this shapeless recipe") : DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, dropsArray));
        }
    };
}

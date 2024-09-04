package btwr.core.mixin.recipe;

import btwr.core.recipe.ExtendedShapelessRecipe;
import btwr.core.recipe.ExtendedShapelessRecipeFactory;
import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Function;

@Mixin(ShapelessRecipe.Serializer.class)
public abstract class ShapelessRecipeSerializerMixin {

    @Inject(method = "codec", at = @At("HEAD"), cancellable = true)
    public void injectGetCodec(CallbackInfoReturnable<MapCodec<ShapelessRecipe>> cir) {
        MapCodec<ShapelessRecipe> extendedCodec = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Codec.STRING.optionalFieldOf("group", "")
                                .forGetter(ShapelessRecipe::getGroup),
                        CraftingRecipeCategory.CODEC
                                .fieldOf("category")
                                .orElse(CraftingRecipeCategory.MISC)
                                .forGetter(ShapelessRecipe::getCategory),
                        ItemStack.VALIDATED_CODEC
                                .fieldOf("result")
                                .forGetter(recipe -> recipe.getResult(null)),
                        Ingredient.DISALLOW_EMPTY_CODEC
                                .listOf()
                                .fieldOf("ingredients")
                                .flatXmap(INGREDIENTS_VALIDATOR, DataResult::success)
                                .forGetter(ShapelessRecipe::getIngredients),
                        ItemStack.CODEC
                                .listOf()
                                .optionalFieldOf("additionalDrops", DefaultedList.of())
                                .flatXmap(ADDITIONAL_DROPS_VALIDATOR, DataResult::success)
                                .forGetter(recipe -> ((ExtendedShapelessRecipe) recipe).getAdditionalDrops())
                ).apply(instance, ExtendedShapelessRecipeFactory::create)
        );

        cir.setReturnValue(extendedCodec);
    }

    @Inject(method = "read", at = @At("RETURN"), cancellable = true)
    private static void onRead(RegistryByteBuf buf, CallbackInfoReturnable<ShapelessRecipe> cir) {
        ShapelessRecipe recipe = cir.getReturnValue();
        DefaultedList<ItemStack> addedDropsList = getAdditionalDrops(buf);

        ((ShapelessRecipeAdded) recipe).setAdditionalDrops(addedDropsList);

        cir.setReturnValue(recipe);
    }

    @Inject(method = "write", at = @At("TAIL"))
    private static void onWrite(RegistryByteBuf buf, ShapelessRecipe recipe, CallbackInfo ci) {
        DefaultedList<ItemStack> addedDropsList = ((ShapelessRecipeAdded) recipe).getAdditionalDrops();

        buf.writeVarInt(addedDropsList.size());
        for (ItemStack droppedStack : addedDropsList) {
            ItemStack.PACKET_CODEC.encode(buf, droppedStack);
        }
    }

    @Unique
    private static DefaultedList<ItemStack> getAdditionalDrops(RegistryByteBuf buf) {
        DefaultedList<ItemStack> ingredients = DefaultedList.of();

        int ingredientCount = buf.readVarInt();
        for (int i = 0; i < ingredientCount; ++i) {
            ingredients.add(ItemStack.PACKET_CODEC.decode(buf));
        }
        return ingredients;
    }

    @Unique
    private static final Function<List<Ingredient>, DataResult<DefaultedList<Ingredient>>>
            INGREDIENTS_VALIDATOR = ingredients -> {
        Ingredient[] ingredientsArray = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
        if (ingredientsArray.length == 0) {
            return DataResult.error(() -> "No ingredients for custom shapeless recipe");
        } else {
            return ingredientsArray.length > 9 ? DataResult.error(() -> "Too many ingredients for custom shapeless recipe") : DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredientsArray));
        }
    };

    @Unique
    private static final Function<List<ItemStack>, DataResult<DefaultedList<ItemStack>>>
            ADDITIONAL_DROPS_VALIDATOR = drops -> {
        ItemStack[] dropsArray = drops.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(ItemStack[]::new);
        return DataResult.success(DefaultedList.copyOf(ItemStack.EMPTY, dropsArray));
    };
}

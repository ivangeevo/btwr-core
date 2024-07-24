/*
 * Decompiled with CFR 0.2.2 (FabricMC 7c48b8c4).
 */
package btwr.core.recipe;

import btwr.core.BTWRMod;
import com.mojang.datafixers.types.Func;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class ShapelessRecipeWithDrops
        implements CraftingRecipe
{
    final String group;
    final CraftingRecipeCategory category;
    final ItemStack result;
    final DefaultedList<Ingredient> ingredients;
    final DefaultedList<Ingredient> additionalDrops;


    public ShapelessRecipeWithDrops(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients,  DefaultedList<Ingredient> additionalDrops) {
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
        this.additionalDrops = additionalDrops;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }
    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public CraftingRecipeCategory getCategory() {
        return this.category;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.result;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public DefaultedList<Ingredient> getAdditionalDrops()
    {
        return this.additionalDrops;
    }

    @Override
    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;
        for (int j = 0; j < recipeInputInventory.size(); ++j) {
            ItemStack itemStack = recipeInputInventory.getStack(j);
            if (itemStack.isEmpty()) continue;
            ++i;
            recipeMatcher.addInput(itemStack, 1);
        }
        return i == this.ingredients.size() && recipeMatcher.match(this, null);
    }

    @Override
    public ItemStack craft(RecipeInputInventory recipeInputInventory, RegistryWrapper.WrapperLookup wrapperLookup) {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= this.ingredients.size();
    }


    public static class Type implements RecipeType<ShapelessRecipeWithDrops>
    {
        public static final Type INSTANCE = new Type();
        public static final String ID = "crafting_shapeless_with_drops";
    }
    public static class Serializer
            implements RecipeSerializer<ShapelessRecipeWithDrops>
    {

        private Serializer() {
        }

        public static final Serializer INSTANCE = new Serializer();

        public static final Identifier ID = new Identifier(BTWRMod.MOD_ID + ":" + Type.ID);


        // separated the ingredients and additional drops code for improved readability.
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

        private static final MapCodec<ShapelessRecipeWithDrops> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        Codec.STRING.optionalFieldOf("group", "")
                                .forGetter(recipe -> recipe.group),
                        CraftingRecipeCategory.CODEC.fieldOf("category")
                                .orElse(CraftingRecipeCategory.MISC)
                                .forGetter(recipe -> recipe.category),
                        ItemStack.VALIDATED_CODEC.fieldOf("result")
                                .forGetter(recipe -> recipe.result),
                        Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")
                                .flatXmap(INGREDIENTS_VALIDATOR, DataResult::success)
                                .forGetter(recipe -> recipe.ingredients),
                        Ingredient.ALLOW_EMPTY_CODEC.listOf().fieldOf("additionalDrops")
                                .flatXmap(ADDITIONAL_DROPS_VALIDATOR, DataResult::success)
                                .forGetter(recipe -> recipe.additionalDrops)
                ).apply(instance, ShapelessRecipeWithDrops::new)
        );


        public static final PacketCodec<RegistryByteBuf, ShapelessRecipeWithDrops> PACKET_CODEC = PacketCodec.ofStatic(Serializer::write, Serializer::read);

        @Override
        public MapCodec<ShapelessRecipeWithDrops> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ShapelessRecipeWithDrops> packetCodec() {
            return PACKET_CODEC;
        }

        private static ShapelessRecipeWithDrops read(RegistryByteBuf buf)
        {
            String string = buf.readString();
            CraftingRecipeCategory craftingRecipeCategory = buf.readEnumConstant(CraftingRecipeCategory.class);

            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll((empty) -> Ingredient.PACKET_CODEC.decode(buf));

            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);

            DefaultedList<Ingredient> addedDropsList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            addedDropsList.replaceAll((empty) -> Ingredient.PACKET_CODEC.decode(buf));
            return new ShapelessRecipeWithDrops(string, craftingRecipeCategory, itemStack, defaultedList, addedDropsList);
        }

        private static void write(RegistryByteBuf buf, ShapelessRecipeWithDrops recipe)
        {
            buf.writeString(recipe.group);
            buf.writeEnumConstant(recipe.category);
            buf.writeVarInt(recipe.ingredients.size());

            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            ItemStack.PACKET_CODEC.encode(buf, recipe.result);

            for (Ingredient ingredient : recipe.additionalDrops) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

        }

    }
}


package btwr.core.mixin.recipe;

import btwr.core.recipe.ExtendedShapelessRecipe;
import btwr.core.recipe.ExtendedShapelessRecipeFactory;
import btwr.core.recipe.interfaces.ShapelessRecipeJsonBuilderAdded;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Objects;

@Mixin(ShapelessRecipeJsonBuilder.class)
public abstract class ShapelessRecipeJsonBuilderMixin implements ShapelessRecipeJsonBuilderAdded {

    @Shadow @Final private RecipeCategory category;
    @Shadow @Final private Item output;
    @Shadow @Final private int count;
    @Shadow @Final private DefaultedList<Ingredient> inputs;
    @Shadow @Nullable private String group;
    @Shadow @Final private Map<String, AdvancementCriterion<?>> advancementBuilder;
    @Unique private DefaultedList<ItemStack> additionalDrops = DefaultedList.of();

    @Shadow protected abstract void validate(Identifier recipeId);

    // Modify the method to use an extended factory which provides access to the additionalDrops parameter.
    @Inject(method = "offerTo", at = @At("HEAD"), cancellable = true)
    public void offerToWithAdditionalDrops(RecipeExporter exporter, Identifier recipeId, CallbackInfo ci) {
        this.validate(recipeId);
        Advancement.Builder builder = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);

        this.advancementBuilder.forEach(builder::criterion);

        if (additionalDrops == null) {
            additionalDrops = DefaultedList.of();
        }

        ExtendedShapelessRecipe shapelessRecipe = (ExtendedShapelessRecipe) ExtendedShapelessRecipeFactory.create(
                Objects.requireNonNullElse(this.group, ""),
                CraftingRecipeJsonBuilder.toCraftingCategory(this.category),
                new ItemStack(this.output, this.count),
                this.inputs,
                this.additionalDrops
        );

        exporter.accept(recipeId, shapelessRecipe, builder.build(recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")));
        ci.cancel();
    }

    @Override
    public ShapelessRecipeJsonBuilder additionalDrop(ItemStack ingredient) {
        return this.additionalDrop(ingredient, 1);
    }

    @Override
    public ShapelessRecipeJsonBuilder additionalDrop(ItemStack ingredient, int size) {
        for (int i = 0; i < size; ++i) {
            this.additionalDrops.add(ingredient);
        }
        return (ShapelessRecipeJsonBuilder) (Object) this;
    }
}

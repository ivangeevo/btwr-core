package btwr.core.mixin.recipe;

import btwr.core.recipe.ExtendedShapelessRecipe;
import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
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

import java.util.Objects;

@Mixin(ShapelessRecipeJsonBuilder.class)
public abstract class ShapelessRecipeJsonBuilderMixin {

    @Shadow @Final
    private RecipeCategory category;
    @Shadow @Final private Item output;
    @Shadow @Final private int count;
    @Shadow @Final private DefaultedList<Ingredient> inputs;
    @Shadow @Nullable
    private String group;

    // Method to set additional drops
    @Unique
    public ShapelessRecipeJsonBuilder additionalDrops(DefaultedList<Ingredient> drops) {
        this.additionalDrops = drops;
        return (ShapelessRecipeJsonBuilder) (Object) this;
    }

    // Add a field for additional drops
    @Unique
    private DefaultedList<Ingredient> additionalDrops = DefaultedList.of();

    @Inject(method = "offerTo", at = @At("HEAD"), cancellable = true)
    public void offerToWithAdditionalDrops(RecipeExporter exporter, Identifier recipeId, CallbackInfo ci) {
        if (!additionalDrops.isEmpty()) {
            // Create the extended recipe with additional drops
            ExtendedShapelessRecipe extendedRecipe = new ExtendedShapelessRecipe(
                    Objects.requireNonNullElse(this.group, ""),
                    CraftingRecipeJsonBuilder.toCraftingCategory(this.category),
                    new ItemStack(this.output, this.count),
                    this.inputs,
                    this.additionalDrops
            );
            Advancement.Builder builder = exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(AdvancementRequirements.CriterionMerger.OR);

            // Pass the extended recipe to the exporter
            exporter.accept(recipeId, extendedRecipe, builder.build(recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")));
            ci.cancel(); // Prevent further execution to avoid conflicts
        }
    }

}

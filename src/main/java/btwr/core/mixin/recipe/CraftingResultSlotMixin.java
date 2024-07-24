package btwr.core.mixin.recipe;

import btwr.core.item.BTWR_Items;
import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import btwr.core.tag.BTWRTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(CraftingResultSlot.class)
public abstract class CraftingResultSlotMixin
{
    
    @Shadow @Final private RecipeInputInventory input;


    @Inject(method = "onTakeItem", at = @At("HEAD"))
    protected void setSecondaryDropsAndCraftSound(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        MinecraftServer server = player.getWorld().getServer();
        if (server != null) {
            dropAdditionalItemsOnTake(server, player);
        }

        if (player.getWorld().isClient) {
            handleSoundOnCraft(stack, player);
        }
    }

    @Inject(method = "onTakeItem", at = @At("TAIL"))
    protected void setTickCraftLogic(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        player.setTimesCraftedThisTick(player.timesCraftedThisTick() + 1);
    }

        // ---------- Class specific methods ---------- //

    // Method to create the secondary optional drop.
    @Unique
    private void dropAdditionalItemsOnTake(MinecraftServer server, PlayerEntity player) {
        Optional<RecipeEntry<CraftingRecipe>> optional = server.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, this.input, player.getWorld());
        CraftingRecipe craftingRecipe;
        if (optional.isPresent() && (craftingRecipe = optional.get().value()) instanceof ShapelessRecipe) {

            DefaultedList<Ingredient> drops = ((ShapelessRecipeAdded) craftingRecipe).getAdditionalDrops();
            if (!drops.isEmpty())
            {
                for (Ingredient itemStack : drops)
                {
                    ItemStack[] matchingStacks = itemStack.getMatchingStacks();

                    player.dropStack( matchingStacks.length > 0 ? matchingStacks[0] : ItemStack.EMPTY );
                }
            }

        }
    }

    // Plays a different sound depending on the item being crafted.
    @Unique
    private void handleSoundOnCraft(ItemStack stack, PlayerEntity player) {

        float higher = 1.25F + (player.getWorld().random.nextFloat() * 0.25F);
        float lower = (player.getWorld().random.nextFloat() - player.getWorld().random.nextFloat()) * 0.2F + 0.6F;

        if (stack.isOf(Items.STICK)) {
            player.playSound(SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 0.1F, higher);
        }
        else if (stack.isIn(ItemTags.PLANKS))
        {
            player.playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 0.1F, higher);
        }
        else if (stack.isOf(BTWR_Items.DIAMOND_INGOT) || stack.isIn(BTWRTags.Items.CLAY_ITEMS))
        {
            player.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 0.1F, lower);

        }

    }

}


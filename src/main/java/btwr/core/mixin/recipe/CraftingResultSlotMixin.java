package btwr.core.mixin.recipe;

import btwr.btwrds.lib.interfaces.added.PlayerEntityAdded;
import btwr.btwrds.tag.BTWRConventionalTags;
import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.CraftingRecipeInput;
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
        ((PlayerEntityAdded)player).setTimesCraftedThisTick(((PlayerEntityAdded)player).timesCraftedThisTick() + 1);
    }

        // ---------- Class specific methods ---------- //

    // Method to create the secondary optional drop.
    @Unique
    private void dropAdditionalItemsOnTake(MinecraftServer server, PlayerEntity player)
    {
        CraftingRecipeInput.Positioned positioned = this.input.createPositionedRecipeInput();
        CraftingRecipeInput craftingRecipeInput = positioned.input();

        Optional<RecipeEntry<CraftingRecipe>> optional = server.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingRecipeInput, player.getWorld());
        CraftingRecipe craftingRecipe;

        if (optional.isPresent() && (craftingRecipe = optional.get().value()) instanceof ShapelessRecipe) {

            DefaultedList<ItemStack> drops = ((ShapelessRecipeAdded) craftingRecipe).getAdditionalDrops();
            if (!drops.isEmpty())
            {
                for (ItemStack itemStack : drops)
                {
                    player.dropStack( itemStack.copy() );
                }
            }

        }
    }

    // Plays a different sound depending on the item being crafted.
    @Unique
    private void handleSoundOnCraft(ItemStack stack, PlayerEntity player) {

        float higher = 1.25F + (player.getWorld().random.nextFloat() * 0.25F);
        float lower = (player.getWorld().random.nextFloat() - player.getWorld().random.nextFloat()) * 0.2F + 0.6F;

        if (stack.isIn(BTWRConventionalTags.Items.ON_CRAFT_WOODEN_SOUND))
        {
            player.playSound(SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 0.1F, higher);
        }
        else if (stack.isIn(BTWRConventionalTags.Items.ON_CRAFT_SLIME_SOUND))
        {
            player.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 0.1F, lower);
        }

    }

}


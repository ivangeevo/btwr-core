package btwr.core.mixin.recipe;

import btwr.core.item.BTWR_Items;
import btwr.core.recipe.interfaces.ShapelessRecipeAdded;
import btwr.core.tag.BTWRTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(CraftingResultSlot.class)
public abstract class CraftingResultSlotMixin {

    @Shadow @Final private CraftingInventory input;

    @Inject(method = "onTakeItem", at = @At("HEAD"))
    protected void injectedOnTakeItem(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        MinecraftServer server = player.world.getServer();
        if (server != null) {
            setOptionalDrop(server, player);
        }

        if (player.getWorld().isClient) {
            handleSoundOnCraft(stack, player);
        }
    }

    // ---------- Class specific methods ---------- //

    // Method to create the secondary optional drop.
    private void setOptionalDrop(MinecraftServer server, PlayerEntity player) {
        Optional<CraftingRecipe> optional = server.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, this.input, player.world);
        CraftingRecipe craftingRecipe;
        if (optional.isPresent() && (craftingRecipe = optional.get()) instanceof ShapelessRecipe) {

            DefaultedList<ItemStack> drops = ((ShapelessRecipeAdded) craftingRecipe).getSecondaryOutput();
            if (!drops.isEmpty())
            {
                for (ItemStack itemStack : drops)
                {
                    player.dropStack(itemStack.copy());
                }
            }

        }
    }


    // Plays a different sound depending on the item being crafted.
    private void handleSoundOnCraft(ItemStack stack, PlayerEntity player) {

        float pitch = 1.25F + (player.getWorld().random.nextFloat() * 0.25F);

        if (stack.isOf(Items.STICK)) {
            player.playSound(SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 0.1F, pitch);
        }
        else if (stack.isIn(ItemTags.PLANKS) || stack.isIn(BTWRTags.Items.MEDIUM_VALUE_FUELS))
        {
            player.playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 0.1F, pitch);
        }
        else if (stack.isOf(BTWR_Items.CREEPER_OYSTERS) || stack.isOf(BTWR_Items.DIAMOND_INGOT))
        {
            player.playSound(SoundEvents.BLOCK_SLIME_BLOCK_HIT, 0.1F, pitch);
        }

    }

}


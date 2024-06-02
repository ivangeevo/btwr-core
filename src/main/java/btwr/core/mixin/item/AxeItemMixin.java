package btwr.core.mixin.item;


import com.google.common.collect.Multimap;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends MiningToolItem
{


    public AxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material,
                        TagKey<Block> effectiveBlocks, Settings settings)
    {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        super.onCraft(stack, world, player);
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack)
    {
            // Apply the remainder logic
            if (stack.getDamage() < stack.getMaxDamage() - 1)
            {
                ItemStack moreDamaged = stack.copy();
                moreDamaged.setDamage(stack.getDamage() + 1);
                return moreDamaged;
            }

            return ItemStack.EMPTY;
    }



    /**
    // TODO: Fix Axe items from duping in Create mixing recipes.
    // Probably needs a better remainder logic.
    @Override
    public ItemStack getRecipeRemainder(ItemStack stack)
    {
            // Check the current inventory and stack match any crafting recipe
            if ( stack.getDamage() < stack.getMaxDamage() - 1 )
            {
                ItemStack moreDamaged = stack.copy();
                moreDamaged.setDamage(stack.getDamage() + 1);
                return moreDamaged;
            }
            return ItemStack.EMPTY;
    }
     **/



    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
    {
        if (user instanceof ServerPlayerEntity serverPlayerEntity)
        {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (user instanceof PlayerEntity && !((PlayerEntity) user).getAbilities().creativeMode)
        {
            stack.decrement(1);
        }

        if (!world.isClient) {
            user.clearStatusEffects();
        }

        return stack;
    }


    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner)
    {
        boolean shouldDrainDurability = state.isReplaceable();

        // Check if the tool is used for the "wrong" activities

        if (shouldDrainDurability)
        {
            if (state.getHardness(world, pos) != 0.0f)
            {

                // Drain durability 2x faster
                stack.damage(2, miner, entity -> entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            }
        }

        return super.postMine(stack, world, state, pos, miner);

    }


}

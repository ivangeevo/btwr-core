package btwr.core.mixin.item;


import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends MiningToolItem
{
    //private final RecipeManager.MatchGetter<RecipeInputInventory, CraftingRecipe> matchGetter = RecipeManager.createCachedMatchGetter(RecipeType.CRAFTING);

    public AxeItemMixin(ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(material, effectiveBlocks, settings);
    }


    @Override
    public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
        super.onCraftByPlayer(stack, world, player);
    }



    // TODO: Fix Axe items from duping in Create mixing recipes.
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
                stack.damage(2, miner, EquipmentSlot.MAINHAND);
            }
        }

        return super.postMine(stack, world, state, pos, miner);

    }


}

package btwr.core.util;

import btwr.core.block.BTWR_Blocks;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

/** A manager class for mixin'd in logic for both Item and Tools (Shears, Axe, etc...).
 *
 * <p>If a method is not prefixed with a tool name ("onUseAxe"), then it's simply mixing in for the Item.class
 * **/

public class ItemAndToolMixinManager
{
    private static final ItemAndToolMixinManager instance = new ItemAndToolMixinManager();

    private ItemAndToolMixinManager() {}

    public static ItemAndToolMixinManager getInstance()
    {
        return instance;
    }


    // TODO: Abstract into separate methods for placement so it can be made to work for an API (eventually)
    // This placing code could be used by Tough Environment, BTWR and the Bind mod, for placing items as blocks in the world
    public void placeAsBlock(ItemUsageContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemStack heldStack = context.getStack();

        if (heldStack.isOf(Items.BRICK))
        {
            // Ensure the block is placed on the server side
            if (!world.isClient)
            {
                BlockPos placePos = pos.up(); // Position to place the new block

                // Get the block state of the block you're trying to place the brick on
                BlockState blockBelowState = world.getBlockState(pos);

                // Check if the block below can support a block on top of it
                if (!blockBelowState.isSolidBlock(world, pos))
                {
                    return;
                }

                // Create an ItemPlacementContext for the new block position
                ItemPlacementContext placementContext = new ItemPlacementContext(Objects.requireNonNull(context.getPlayer()), context.getHand(), heldStack, context.getHitResult());

                // Get the block state using the placement context
                BlockState brickBlockState = BTWR_Blocks.BRICK.getPlacementState(placementContext);

                // Check if the target position is air or a replaceable block
                if ((world.isAir(placePos) || world.getBlockState(placePos).canReplace(placementContext)) && brickBlockState != null)
                {
                    // Replace the block at the target position with the brick block
                    world.setBlockState(placePos, brickBlockState);
                    heldStack.decrement(1);

                    // Indicate the interaction was successful
                }
            }
        }

    }

    public ItemStack damageOnCrafting(ItemStack stack)
    {
        if (stack.getItem() instanceof ShearsItem || stack.getItem() instanceof AxeItem)
        {
            if (stack.getDamage() < stack.getMaxDamage() - 1)
            {
                ItemStack moreDamaged = stack.copy();
                moreDamaged.setDamage(stack.getDamage() + 1);
                return moreDamaged;
            }
        }

        return ItemStack.EMPTY;
    }

    public void onPostMineAxe(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner)
    {
        if (stack.getItem() instanceof AxeItem)
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

        }
    }

    public ItemStack onFinishUsingAxe(ItemStack stack, World world, LivingEntity user, Item axeItem)
    {
        if (stack.getItem() instanceof AxeItem)
        {
            if (user instanceof ServerPlayerEntity serverPlayerEntity)
            {
                Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(axeItem));
            }

            if (user instanceof PlayerEntity && !((PlayerEntity) user).getAbilities().creativeMode)
            {
                stack.decrement(1);
            }

            if (!world.isClient) {
                user.clearStatusEffects();
            }

        }

        return stack;
    }

    public static List<ToolComponent.Rule> MODIFIED_SHEARS_COMPONENT_LIST = List.of(
            ToolComponent.Rule.ofAlwaysDropping(List.of(Blocks.COBWEB), 15.0f),
            ToolComponent.Rule.of(BlockTags.LEAVES, 15.0f),
            ToolComponent.Rule.of(BlockTags.WOOL, 5.0f),
            ToolComponent.Rule.of(List.of(Blocks.VINE, Blocks.GLOW_LICHEN), 2.0f),

            // added crop hemp; all above are the original ones
            ToolComponent.Rule.of(List.of(BTWR_Blocks.CROP_HEMP),20.0f)
    );



}

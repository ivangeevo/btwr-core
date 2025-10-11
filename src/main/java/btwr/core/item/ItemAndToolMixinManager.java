package btwr.core.item;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** A manager class for mixin'd in logic for both Item and Tools (Shears, Axe, etc...).
 *
 * <p>If a method is not prefixed with a tool name ("onUseAxe"), then it's simply mixing in for the Item.class
 * **/
public class ItemAndToolMixinManager {

    private static final ItemAndToolMixinManager instance = new ItemAndToolMixinManager();

    private ItemAndToolMixinManager() {}

    public static ItemAndToolMixinManager getInstance()
    {
        return instance;
    }

    public void onPostMineAxe(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (isValidAxeItem(stack)) {
            boolean shouldDrainDurability = state.isReplaceable();

            // Check if the tool is used for the "wrong" activities

            if (shouldDrainDurability) {
                if (state.getHardness(world, pos) != 0.0f) {
                    // Drain durability 2x faster
                    stack.damage(2, miner, EquipmentSlot.MAINHAND);
                }
            }

        }
    }

    public ItemStack onFinishUsingAxe(ItemStack stack, World world, LivingEntity user, Item axeItem) {
        if (isValidAxeItem(stack)) {
            if (user instanceof ServerPlayerEntity serverPlayerEntity) {
                Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(axeItem));
            }

            if (user instanceof PlayerEntity && !((PlayerEntity) user).getAbilities().creativeMode) {
                stack.decrement(1);
            }

            if (!world.isClient) {
                user.clearStatusEffects();
            }

        }

        return stack;
    }

    private boolean isValidAxeItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || isBWTAxe(stack);
    }

    private boolean isBWTAxe(ItemStack stack) {
        // special case added originally for BWT's BattleAxe because it's a mining tool and it should be in this tag
        return (stack.getItem() instanceof MiningToolItem && stack.isIn(BTWRConventionalTags.Items.AXES_MAKE_PLANKS));
    }



}

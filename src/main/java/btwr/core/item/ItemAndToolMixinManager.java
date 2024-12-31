package btwr.core.item;

import btwr.btwr_sl.tag.BTWRConventionalTags;
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
public class ItemAndToolMixinManager {
    private static final ItemAndToolMixinManager instance = new ItemAndToolMixinManager();

    private ItemAndToolMixinManager() {}

    public static ItemAndToolMixinManager getInstance()
    {
        return instance;
    }

    public ItemStack damageOnCrafting(ItemStack stack) {
        if (stack.getItem() instanceof ShearsItem || isValidAxeItem(stack)) {
            if (stack.getDamage() < stack.getMaxDamage() - 1) {
                ItemStack moreDamaged = stack.copy();
                moreDamaged.setDamage(stack.getDamage() + 1);
                return moreDamaged;
            }
        }

        return ItemStack.EMPTY;
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

    public static List<ToolComponent.Rule> MODIFIED_SHEARS_COMPONENT_LIST = List.of(
            ToolComponent.Rule.ofAlwaysDropping(BTWRConventionalTags.Blocks.WEB_BLOCKS, 15.0f),
            ToolComponent.Rule.of(BlockTags.LEAVES, 15.0f),
            ToolComponent.Rule.of(BlockTags.WOOL, 5.0f),
            ToolComponent.Rule.of(List.of(Blocks.VINE, Blocks.GLOW_LICHEN), 2.0f),

            // added crop hemp; all above are the original ones
            ToolComponent.Rule.of(List.of(BTWR_Blocks.CROP_HEMP),20.0f)
    );

    private boolean isValidAxeItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || isBWTAxe(stack);
    }

    private boolean isBWTAxe(ItemStack stack) {
        // special case added originally for BWT's BattleAxe because it's a mining tool and it should be in this tag
        return (stack.getItem() instanceof MiningToolItem && stack.isIn(BTWRConventionalTags.Items.AXES_MAKE_PLANKS));
    }



}

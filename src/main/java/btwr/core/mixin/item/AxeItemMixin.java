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
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends MiningToolItem {

    protected AxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings, Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    // Adds remainder logic so the item doesn't get consumed on crafting.
    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        if (stack.getDamage() < stack.getMaxDamage() - 1) {
            ItemStack moreDamaged = stack.copy();
            moreDamaged.setDamage(stack.getDamage() + 1);
            return moreDamaged;
        }

        return ItemStack.EMPTY;

    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        if (user instanceof PlayerEntity && !((PlayerEntity) user).getAbilities().creativeMode) {
            stack.decrement(1);
        }
        if (!world.isClient) {
            user.clearStatusEffects();
        }
        return stack;
    }


    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean shouldDrainDurability = false;

        // Check if the tool is used for the "wrong" activities
        if (state.getMaterial().isReplaceable()) {
            shouldDrainDurability = true;
        }

        if (shouldDrainDurability) {
            if (state.getHardness(world, pos) != 0.0f) {

                // Drain durability 2x faster
                stack.damage(2, miner, entity -> entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }


}

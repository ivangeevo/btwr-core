package btwr.core.util;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.mixin.content.registry.HoeItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TillingDropUtil {

	public static boolean simulateTillingDrop(ServerWorld world, BlockPos pos, PlayerEntity player, Hand hand,
											  BiPredicate<BlockState, BlockState> shouldRevertBlockChange,
											  BlockState customTilledState) {
		BlockState original = world.getBlockState(pos);
		Block block = original.getBlock();

		Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> entry = HoeItemAccessor.getTillingActions().get(block);
		if (entry == null) return false;

		ItemUsageContext context = new ItemUsageContext(player, hand, new BlockHitResult(
				Vec3d.ofCenter(pos),
				Direction.UP,
				pos,
				false
		));

		if (entry.getFirst().test(context)) {
			// Run tilling action, may change block state & drop items
			entry.getSecond().accept(context);

			BlockState after = world.getBlockState(pos);

			// Override vanilla farmland block with custom block if specified
			if (customTilledState != null) {
				world.setBlockState(pos, customTilledState);
				after = customTilledState;
			}

			// Use the predicate to decide whether to revert the change or keep it
			if (shouldRevertBlockChange.test(original, after)) {
				world.setBlockState(pos, original);
			} else {
				// Play hoe till sound only if block was actually changed and not reverted
				player.getStackInHand(hand).damage(1, player, EquipmentSlot.MAINHAND);
				world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			return true;
		}

		return false;
	}

}


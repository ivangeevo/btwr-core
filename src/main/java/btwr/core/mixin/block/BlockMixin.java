package btwr.core.mixin.block;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import btwr.core.BTWRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

import static net.minecraft.block.Block.pushEntitiesUpBeforeBlockChange;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "afterBreak", at = @At("HEAD"))
    private void onAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity,
                              ItemStack tool, CallbackInfo ci)
    {
        if (!BTWRMod.getInstance().settings.shouldChangeHoesBTWStyle() || world.isClient()) return;
        if (!tool.isIn(ItemTags.HOES)) return;

        if (state.isIn(BTWRConventionalTags.Blocks.FARMLAND_VIABLE_GRASS)) {
            setState(world, pos, Blocks.DIRT.getDefaultState());
        }

        if (state.isIn(BTWRConventionalTags.Blocks.FARMLAND_VIABLE_DIRT)) {
            setState(world, pos, Blocks.FARMLAND.getDefaultState());
        }
    }

    private static Consumer<ItemUsageContext> tillToDirt(ItemConvertible droppedItem) {
        return context -> {
            context.getWorld().setBlockState(context.getBlockPos(), Blocks.DIRT.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
            context.getWorld().emitGameEvent(GameEvent.BLOCK_CHANGE, context.getBlockPos(), GameEvent.Emitter.of(context.getPlayer(), Blocks.DIRT.getDefaultState()));
            Block.dropStack(context.getWorld(), context.getBlockPos(), context.getSide(), new ItemStack(droppedItem));
        };
    }

    private static Consumer<ItemUsageContext> tillToFarmland(BlockState result, ItemConvertible droppedItem) {
        return context -> {
            context.getWorld().setBlockState(context.getBlockPos(), result, Block.NOTIFY_ALL_AND_REDRAW);
            context.getWorld().emitGameEvent(GameEvent.BLOCK_CHANGE, context.getBlockPos(), GameEvent.Emitter.of(context.getPlayer(), result));
            Block.dropStack(context.getWorld(), context.getBlockPos(), context.getSide(), new ItemStack(droppedItem));
        };
    }

    @Unique
    private void setState(World world, BlockPos pos, BlockState newState) {
        BlockState oldState = world.getBlockState(pos);
        BlockState updatedState = pushEntitiesUpBeforeBlockChange(oldState, newState, world, pos);
        world.setBlockState(pos, updatedState,0,0);
    }
}

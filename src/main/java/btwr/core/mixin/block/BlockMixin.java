package btwr.core.mixin.block;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import btwr.core.BTWRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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


    @Unique
    private void setState(World world, BlockPos pos, BlockState newState) {
        BlockState oldState = world.getBlockState(pos);
        BlockState updatedState = pushEntitiesUpBeforeBlockChange(oldState, newState, world, pos);
        world.setBlockState(pos, updatedState);
    }
}

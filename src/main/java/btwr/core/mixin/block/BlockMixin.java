package btwr.core.mixin.block;

import btwr.core.block.BlockManager;
import btwr.core.block.interfaces.BlockAdded;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin implements BlockAdded
{

    // Make waterlogged blocks to not retain water source block on break.
    @Inject(method = "afterBreak", at = @At("HEAD"))
    private void onBreakWaterloggedBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci)
    {
        BlockManager.getInstance().handleCustomWaterlogging(world, pos, state, player);
    }

    @Inject(method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private static void customDropStacks(BlockState state, World world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfo ci)
    {
        BlockManager.getInstance().dropStacksInDirectionOrElse(state, world, pos, blockEntity, entity, tool);
        ci.cancel();
    }

    @Override
    public void notifyOfFullStagePlantGrowthOn(World world, BlockPos pos, Block plantBlock)
    {
    }

    /**
     * This is used by old style non-daily plant growth
     */
    @Override
    public float getPlantGrowthOnMultiplier(World world, BlockPos pos, Block plantBlock)
    {
        return 1F;
    }
    @Override
    public boolean isBlockHydratedForPlantGrowthOn(World world, BlockPos pos)
    {
        return false;
    }
}

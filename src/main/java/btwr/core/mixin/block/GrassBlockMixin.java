package btwr.core.mixin.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GrassBlock.class)
public abstract class GrassBlockMixin extends SpreadableBlock implements Fertilizable
{
    protected GrassBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack)
    {

        if (stack.isIn(ItemTags.HOES)) {
            world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 4, 0);
        }

        super.afterBreak(world, player, pos, state, blockEntity, stack);

    }
}



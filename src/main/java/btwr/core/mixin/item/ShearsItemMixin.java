package btwr.core.mixin.item;

import btwr.core.item.BTWR_Items;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public abstract class ShearsItemMixin extends Item {

    public ShearsItemMixin(Settings settings) {
        super(settings);
    }

    // Injected logic to drop Grass item so that it doesn't overwrite the grass json here. We need to overwrite it later in Self Sustainable.
    @Inject(method = "postMine", at = @At("HEAD"))
    private void injectedGrassDrop(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
        if (!miner.getWorld().isClient) {
            if (state.isOf(Blocks.GRASS) && stack.isOf(BTWR_Items.DIAMOND_SHEARS)) {
                ItemStack grassStack = new ItemStack(Items.GRASS);

                // Create an ItemEntity for the grass and spawn it in the world
                ItemEntity grassEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, grassStack);
                world.spawnEntity(grassEntity);
            }
        }
    }

}

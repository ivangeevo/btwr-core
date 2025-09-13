package btwr.core.mixin.item;

import btwr.core.item.ItemAndToolMixinManager;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements ToggleableFeature, ItemConvertible, FabricItem
{
    @Unique
    private static final ItemAndToolMixinManager itemMixinManager = ItemAndToolMixinManager.getInstance();

    @Inject(method = "finishUsing", at = @At("RETURN"))
    private void onFinishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir)
    {
        itemMixinManager.onFinishUsingAxe(stack, world, user, (Item)(Object)this );
    }

    @Inject(method = "postMine", at = @At("RETURN"), cancellable = true)
    private void onPostMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir)
    {
        // save the original return (super call)
        boolean original = cir.getReturnValue();

        // set all without the super call
        itemMixinManager.onPostMineAxe(stack, world, state, pos, miner);

        // and here we return it
        cir.setReturnValue(original);
    }

}

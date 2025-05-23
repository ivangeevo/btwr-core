package btwr.core.mixin;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import btwr.core.BTWRMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(HoeItem.class)
public abstract class HoeItemMixin  {

    // Removes right-clicking for hoes.
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void injectedUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        BlockPos pos = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(pos);

        if (!BTWRMod.getInstance().settings.shouldChangeHoesBTWStyle()) return;

        if (state.isOf(Blocks.GRASS_BLOCK) || state.isIn(BTWRConventionalTags.Blocks.FARMLAND_VIABLE_DIRT)) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}

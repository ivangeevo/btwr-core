package btwr.core.mixin;

import btwr.core.BTWRMod;
import btwr.core.util.TillingDropUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
    @Shadow @Final protected ServerPlayerEntity player;
    @Shadow protected ServerWorld world;

    @Inject(method = "tryBreakBlock", at = @At("HEAD"), cancellable = true)
    private void onTryBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!BTWRMod.getInstance().settings.shouldChangeHoesBTWStyle()) return;

        if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof HoeItem) {
            BlockState currentState = world.getBlockState(pos);

            // List of blocks we don't want to convert
            if (currentState.isOf(Blocks.FARMLAND) || currentState.isOf(Blocks.DIRT)) {
                return; // Skip conversion
            }

            BlockState dirtState = Blocks.DIRT.getDefaultState();

            boolean result = TillingDropUtil.simulateTillingDrop(world, pos, player, Hand.MAIN_HAND,
                    (oldState, newState) -> false, dirtState);

            if (result) {
                cir.setReturnValue(false);
            }
        }
    }


}


package btwr.core.mixin.item;

import btwr.core.block.BTWR_Blocks;
import btwr.core.block.blocks.BrickBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Item.class)
public abstract class ItemMixin
{
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void injectedUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir)
    {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemStack heldStack = context.getStack();

        if (heldStack.isOf(Items.BRICK))
        {
            // Ensure the block is placed on the server side
            if (!world.isClient)
            {
                BlockPos placePos = pos.up(); // Position to place the new block

                // Check the block below the target position
                BlockState belowBlockState = world.getBlockState(pos);
                if (belowBlockState.getBlock() instanceof BrickBlock)
                {
                    // Prevent placing the block on top of itself
                    cir.setReturnValue(ActionResult.FAIL);
                    return;
                }

                //TODO:
                // Possibly not good idea to access widen getHitResult(the last parameter)
                // try something else if compatability issues arise.

                // Create an ItemPlacementContext for the new block position
                ItemPlacementContext placementContext = new ItemPlacementContext(Objects.requireNonNull(context.getPlayer()), context.getHand(), heldStack, context.getHitResult());

                // Get the block state using the placement context
                BlockState brickBlockState = BTWR_Blocks.BRICK.getPlacementState(placementContext);

                if (world.isAir(placePos) && brickBlockState != null) // Check if the position is air before placing the block
                {
                    world.setBlockState(placePos, brickBlockState);
                    heldStack.decrement(1);

                    // Indicate the interaction was successful
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }
}

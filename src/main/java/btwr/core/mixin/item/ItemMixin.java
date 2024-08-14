package btwr.core.mixin.item;

import btwr.core.block.BTWR_Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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

                // Get the block state of the block you're trying to place the brick on
                BlockState blockBelowState = world.getBlockState(pos);

                // Check if the block below can support a block on top of it
                if (!blockBelowState.isSolidBlock(world, pos))
                {
                    // Prevent placing the block on non-solid blocks like flowers, tall grass, etc.
                    cir.setReturnValue(ActionResult.FAIL);
                    return;
                }

                // Create an ItemPlacementContext for the new block position
                ItemPlacementContext placementContext = new ItemPlacementContext(Objects.requireNonNull(context.getPlayer()), context.getHand(), heldStack, context.getHitResult());

                // Get the block state using the placement context
                BlockState brickBlockState = BTWR_Blocks.BRICK.getPlacementState(placementContext);

                // Check if the target position is air or a replaceable block
                if ((world.isAir(placePos) || world.getBlockState(placePos).canReplace(placementContext)) && brickBlockState != null)
                {
                    // Replace the block at the target position with the brick block
                    world.setBlockState(placePos, brickBlockState);
                    heldStack.decrement(1);

                    // Indicate the interaction was successful
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }
}

package btwr.core.block;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import btwr.core.BTWRMod;
import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.Block.pushEntitiesUpBeforeBlockChange;

/** Contains code for managing modification of hoes usage for making farmland
 *
 */
public class BlockTillingManager {

    public static final Block[] dirtLikeBlocks = {Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT};

    /** Normal calls to the TillableBlockRegistry
     * <p>Making variations of dirt blocks to get set to farmland
     * **/
    public static void registerNormalTillable() {
        if (!BTWRMod.getInstance().settings.shouldChangeHoesBTWStyle()) return;

        for (Block dirt : dirtLikeBlocks) {
            TillableBlockRegistry.register(
                    dirt,
                    HoeItem::canTillFarmland,
                    context -> {
                        BlockState result = Blocks.FARMLAND.getDefaultState();
                        HoeItem.createTillAction(result).accept(context);
                    }
            );
        }
    }

    public static class MixinMod {

        private static final MixinMod INSTANCE = new MixinMod();
        private MixinMod() {}
        public static MixinMod getInstance() {
            return INSTANCE;
        }

        public void onUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
            if (!BTWRMod.getInstance().settings.shouldChangeHoesBTWStyle()) return;
            BlockPos pos = context.getBlockPos();
            BlockState state = context.getWorld().getBlockState(pos);


            if (state.isOf(Blocks.GRASS_BLOCK) || state.isIn(BTWRConventionalTags.Blocks.FARMLAND_VIABLE_DIRT)) {
                cir.setReturnValue(ActionResult.FAIL);
            }
        }

        public void onAfterBreak(World world, BlockPos pos, BlockState state, ItemStack tool) {
            if (!BTWRMod.getInstance().settings.shouldChangeHoesBTWStyle()) return;
            if (world.isClient() || !tool.isIn(ItemTags.HOES)) return;

            if (state.isIn(BTWRConventionalTags.Blocks.FARMLAND_VIABLE_GRASS) || state.getBlock() instanceof GrassBlock) {
                setState(world, pos, Blocks.DIRT.getDefaultState());
            }

            if (state.isIn(BTWRConventionalTags.Blocks.FARMLAND_VIABLE_DIRT)) {
                setState(world, pos, Blocks.FARMLAND.getDefaultState());
            }
        }
    }

    private static void setState(World world, BlockPos pos, BlockState newState) {
        BlockState oldState = world.getBlockState(pos);
        BlockState updatedState = pushEntitiesUpBeforeBlockChange(oldState, newState, world, pos);
        world.setBlockState(pos, updatedState);
    }
}

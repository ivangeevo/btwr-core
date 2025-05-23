package btwr.core.block;

import btwr.core.BTWRMod;
import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;

/** Contains code for managing modification of hoes usage for making farmland
 * It has both the register
 */
public class BlockTillingManager {

    public static final Block[] dirtLikeBlocks = {Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT};

    /** Normal calls to the TillableBlockRegistry **/
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

    private static void changeFromDirt() {

    }
}

package btwr.core.block;

import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Items;

public class BlockTillingManager {

    public static final Block[] dirtLikeBlocks = {Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT};

    public static void registerAll() {
        for (Block dirt : dirtLikeBlocks) {
            changeFromDirt(dirt);
        }

    }

    private static void changeFromDirt(Block dirtBlock) {
        TillableBlockRegistry.register(
                dirtBlock,
                HoeItem::canTillFarmland,
                context -> {
                    BlockState result = Blocks.FARMLAND.getDefaultState();
                    HoeItem.createTillAction(result).accept(context);
                }
        );
    }
}

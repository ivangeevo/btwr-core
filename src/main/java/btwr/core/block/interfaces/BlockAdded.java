package btwr.core.block.interfaces;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BlockAdded
{

    /**
     * Called when a plant hits a full growth stage, like wheat fully grown,
     * or each full block of Hemp.  Used to clear fertilizer.
     */
    default void notifyOfFullStagePlantGrowthOn(World world, BlockPos pos, Block plantBlock)
    {
    }

    /**
     * This is used by old style non-daily plant growth
     */
    default float getPlantGrowthOnMultiplier(World world, BlockPos pos, Block plantBlock)
    {
        return 0;
    }

    default boolean isBlockHydratedForPlantGrowthOn(World world, BlockPos pos)
    {
        return false;
    }

}

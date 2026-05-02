package org.btwr.core.api;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class BlightSpreadConditions {
    
    @FunctionalInterface
    public interface BlightSpreadCondition {
        boolean canSpread(World world, BlockPos pos, BlockState state, int blightLevel);
    }
    
    private static final Map<Block, BlightSpreadCondition> CONDITIONS = new HashMap<>();

    // Register a condition for a block
    public static void register(Block block, BlightSpreadCondition condition) {
        CONDITIONS.put(block, condition);
    }

    public static void registerDefaults() {
        // Grass spreads at any level
        register(Blocks.GRASS_BLOCK, (world, pos, state, blightLevel) -> true);

        // Dirt spreads at any level
        register(Blocks.DIRT, (world, pos, state, blightLevel) -> true);

        // Podzol spreads at any level
        register(Blocks.PODZOL, (world, pos, state, blightLevel) -> true);

        // Farmland only spreads at level 1+
        register(Blocks.FARMLAND, (world, pos, state, blightLevel) -> blightLevel >= 1);

        // Mycelium only spreads at level 2+
        register(Blocks.MYCELIUM, (world, pos, state, blightLevel) -> blightLevel >= 2);

        // Moss only spreads at level 2+
        register(Blocks.MOSS_BLOCK, (world, pos, state, blightLevel) -> blightLevel >= 2);
    }

    // Check if blight can spread to a block at a given level
    public static boolean canBlightSpreadTo(World world, BlockPos pos, BlockState state, int blightLevel) {
        BlightSpreadCondition condition = CONDITIONS.get(state.getBlock());
        if (condition != null) {
            return condition.canSpread(world, pos, state, blightLevel);
        }

        return false;
    }
}
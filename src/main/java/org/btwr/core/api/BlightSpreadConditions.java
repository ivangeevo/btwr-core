package org.btwr.core.api;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for controlling which blocks blight can spread to, and under what conditions.
 *
 * <p>Other mods can register their own blocks via {@link #register(Block, BlightSpreadCondition)}
 * to participate in the blight spread system.
 */
public class BlightSpreadConditions {

    /**
     * A condition that determines whether blight can spread to a given block.
     *
     * <p>Implementations receive full world context so conditions can check
     * neighbouring blocks, biome, light level, or any other world state.
     */
    @FunctionalInterface
    public interface BlightSpreadCondition {
        /**
         * Returns {@code true} if blight is allowed to spread to the block at {@code pos}.
         *
         * @param world      the world the spread is occurring in
         * @param pos        the position of the block being spread to
         * @param state      the current state of the block at {@code pos}
         * @param blightLevel the level of the adjacent blight block that is spreading (0–3)
         * @return {@code true} if blight may convert this block
         */
        boolean canSpread(World world, BlockPos pos, BlockState state, int blightLevel);
    }

    /**
     * Maps each registered block to its spread condition.
     */
    private static final Map<Block, BlightSpreadCondition> CONDITIONS = new HashMap<>();

    /**
     * Registers a {@link BlightSpreadCondition} for the given block.
     *
     * <p>If a condition is already registered for this block it will be replaced.
     * Call this during mod initialisation, before any blight spread can occur.
     *
     * @param block     the block to register a condition for
     * @param condition the condition that controls whether blight may spread to this block
     */
    public static void register(Block block, BlightSpreadCondition condition) {
        CONDITIONS.put(block, condition);
    }

    /**
     * Registers the built-in spread conditions for vanilla blocks.
     *
     * <p>Called automatically during BTWR initialisation. Vanilla behaviour:
     * <ul>
     *   <li>Grass, dirt, and podzol — spreadable at any blight level</li>
     *   <li>Farmland — requires blight level 1 or higher</li>
     *   <li>Mycelium and moss — require blight level 2 or higher</li>
     * </ul>
     */
    public static void registerDefaults() {
        register(Blocks.GRASS_BLOCK, (world, pos, state, blightLevel) -> true);
        register(Blocks.DIRT, (world, pos, state, blightLevel) -> true);
        register(Blocks.PODZOL, (world, pos, state, blightLevel) -> true);
        register(Blocks.FARMLAND, (world, pos, state, blightLevel) -> blightLevel >= 1);
        register(Blocks.MYCELIUM, (world, pos, state, blightLevel) -> blightLevel >= 2);
        register(Blocks.MOSS_BLOCK, (world, pos, state, blightLevel) -> blightLevel >= 2);
    }

    /**
     * Returns whether blight can spread to the block at the given position.
     *
     * <p>Looks up the condition registered for {@code state}'s block and delegates to it.
     * Returns {@code false} if no condition has been registered for the block.
     *
     * @param world      the world the spread is occurring in
     * @param pos        the position of the candidate block
     * @param state      the state of the candidate block
     * @param blightLevel the level of the spreading blight block (0–3)
     * @return {@code true} if blight may convert this block
     */
    public static boolean canBlightSpreadTo(World world, BlockPos pos, BlockState state, int blightLevel) {
        BlightSpreadCondition condition = CONDITIONS.get(state.getBlock());
        if (condition != null) {
            return condition.canSpread(world, pos, state, blightLevel);
        }
        return false;
    }
}
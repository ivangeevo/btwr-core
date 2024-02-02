package btwr.core.data.constants;

    /**
     * Constants of Mining Levels.
     * <p>Mining levels are used by blocks to determine the strength of the tools required to successfully harvest them.
     * <br>All tool materials have an assigned mining level. If a tool's mining level is equal to or greater than the block's,
     * the tool will apply its efficiency bonus and the block will drop its loot table.
     * <p>Blocks without mining levels, or items that aren't tools, use {@link net.fabricmc.yarn.constants.MiningLevels#HAND}.
     * @see net.minecraft.item.ToolMaterial#getMiningLevel() ToolMaterial#getMiningLevel
     */
    public final class BTWR_MiningLevels {
        /**
         * Blocks with this level do not require a tool to harvest.
         * <br>This is the default level for blocks and items.
         */
        public static final int HAND = 0;

        /**
         * Blocks with this level require a Wooden tool or better to harvest.
         * <br>In addition to Wooden Tools, Golden Tools also use this level.
         */
        public static final int WOOD = 1;

        /**
         * Blocks with this level require a Stone tool or better to harvest.
         */
        public static final int STONE = 2;

        /**
         * Blocks with this level require an Iron tool or better to harvest.
         */
        public static final int IRON = 3;

        /**
         * Blocks with this level require a Steel tool or better to harvest.
         */
        public static final int STEEL = 5;

        /**
         * Blocks with this level require a Diamond tip tool to harvest.
         */
        public static final int DIAMOND = 4;

        private BTWR_MiningLevels() {
        }
    }

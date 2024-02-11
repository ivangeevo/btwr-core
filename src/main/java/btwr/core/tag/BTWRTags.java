package btwr.core.tag;

import btwr.core.BTWRMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BTWRTags {


    public static class Blocks {

        public static final TagKey<Block> DIRECTIONAL_DROPPING_STATES = createTag("directional_dropping_states");

        public static final TagKey<Block> VANILLA_CONVERTING_BLOCKS = createTag("vanilla_converting_blocks");

        // new blocks -> will be used for 2nd strata
        public static final TagKey<Block> MANTLE_BLOCKS = createTag("mantle_blocks");

        // new blocks in place of deepslate
        public static final TagKey<Block> BLACKSTONE_BLOCKS = createTag("blackstone_blocks");

        public static final TagKey<Block> STONE_STRATA1 = createTag("stone_strata1");
        public static final TagKey<Block> STONE_STRATA2 = createTag("stone_strata2");
        public static final TagKey<Block> STONE_STRATA3 = createTag("stone_strata3");

        public static final TagKey<Block> STONE_BROKEN = createTag("stone_broken");


        public static final TagKey<Block> STRIPPED_LOG_BLOCKS = createTag("stripped_log_blocks");

        public static final TagKey<Block> MODDED_TORCHES = createTag("modded_torches");


        private static TagKey<Block> createTag (String name) {
            return TagKey.of(Registry.BLOCK_KEY, new Identifier(BTWRMod.MOD_ID, name));
        }
    }

    public static class Items {
        /** These tags don't generate using datagen.
         *  Instead, the items should be added manually and
         *  provide for other side mods.

        /** For example the MEDIUM_VALUE_FUELS Tag provides itself
         * to all other BTWR sidemods to add their items.
         * This can't be done with datagen as not all items are present in this project. **/
        // ---------- TAGS THAT DON'T GENERATE ---------- //
        public static final TagKey<Item> MEDIUM_VALUE_FUELS = createTag("medium_value_fuels");
        public static final TagKey<Item> LOW_VALUE_FUELS = createTag("low_value_fuels");




        // ---------- NORMAL TAGS ---------- //

        public static final TagKey<Item> DROP_SPREAD_ITEMS = createTag("drop_spread_items");

        public static final TagKey<Item> WOOL_ITEMS = createTag("wool_items");
        public static final TagKey<Item> WOOL_KNIT_ITEMS = createTag("wool_knit_items");


        public static final TagKey<Item> COOKED_EGGS = createTag("cooked_eggs");
        public static final TagKey<Item> FISH_FOR_FOOD = createTag("fish_for_food");

        public static final TagKey<Item> BARK_ITEMS = createTag("bark_items");

        public static final TagKey<Item> PICKAXES = createTag("pickaxes");
        public static final TagKey<Item> PRIMITIVE_CHISELS = createTag("primitive_chisels");
        public static final TagKey<Item> MODERN_CHISELS = createTag("modern_chisels");
        public static final TagKey<Item> MODERN_PICKAXES = createTag("modern_pickaxes");

        public static final TagKey<Item> PRIMITIVE_AXES = createTag("primitive_axes");

        public static final TagKey<Item> AXES_MAKE_PLANKS = createTag("axes_make_planks");

        public static final TagKey<Item> PIG_BREEDING_ITEMS = createTag("pig_breeding_items");
        public static final TagKey<Item> PIG_TEMPT_ITEMS = createTag("pig_tempt_items");







        private static TagKey<Item> createTag (String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier(BTWRMod.MOD_ID, name));
        }
    }

}

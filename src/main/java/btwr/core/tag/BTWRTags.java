package btwr.core.tag;

import btwr.core.BTWRMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BTWRTags
{

    public static class Conventional
    {
        public static class Blocks
        {
            public static final TagKey<Block> VANILLA_CONVERTING_BLOCKS = createTag("vanilla_converting_blocks");

            public static final TagKey<Block> MODDED_CONVERTING_BLOCKS = createTag("modded_converting_blocks");

            private static TagKey<Block> createTag(String name) {
                return TagKey.of(RegistryKeys.BLOCK, new Identifier("c", name));
            }
        }

        public static class Items
        {
            /**
             * Tool levels;
             *
             * 1.Primitive
             * 2.Modern
             * 3.Advanced
             *
             */

            public static final TagKey<Item> PRIMITIVE_CHISELS = createTag("primitive_chisels");
            public static final TagKey<Item> MODERN_CHISELS = createTag("modern_chisels");

            public static final TagKey<Item> PRIMITIVE_PICKAXES = createTag("primitive_pickaxes");
            public static final TagKey<Item> MODERN_PICKAXES = createTag("modern_pickaxes");
            public static final TagKey<Item> ADVANCED_PICKAXES = createTag("advanced_pickaxes");

            public static final TagKey<Item> PRIMITIVE_AXES = createTag("primitive_axes");
            public static final TagKey<Item> MODERN_AXES = createTag("modern_axes");
            public static final TagKey<Item> ADVANCED_AXES = createTag("advanced_axes");


            public static final TagKey<Item> PRIMITIVE_SHOVELS = createTag("primitive_shovels");
            public static final TagKey<Item> MODERN_SHOVELS = createTag("modern_shovels");
            public static final TagKey<Item> ADVANCED_SHOVELS = createTag("advanced_shovels");


            public static final TagKey<Item> PRIMITIVE_HOES = createTag("primitive_hoes");
            public static final TagKey<Item> MODERN_HOES = createTag("modern_hoes");
            public static final TagKey<Item> ADVANCED_HOES = createTag("advanced_hoes");

            // Item Tag for items that should do knockback if the config for knockback restriction is turned on.
            public static final TagKey<Item> DO_KNOCKBACK_ITEMS = createTag("do_knockback_items");


            private static TagKey<Item> createTag(String name) {
                return TagKey.of(RegistryKeys.ITEM, new Identifier("c", name));
            }
        }


    }

    public static class Blocks
    {


        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(BTWRMod.MOD_ID, name));
        }
    }

    public static class Items
    {

        /** These tags don't generate using datagen.
         *  Instead, the items should be added manually and
         *  provide for other side mods.

        /** For example the MEDIUM_VALUE_FUELS Tag provides itself
         * to all other BTWR sidemods to add their items.
         * This can't be done with datagen as not all items are present in this project. **/
        // ---------- TAGS THAT DON'T GENERATE ---------- //






        // ---------- NORMAL TAGS ---------- //

        public static final TagKey<Item> CLAY_ITEMS = createTag("clay_items");
        public static final TagKey<Item> DROP_SPREAD_ITEMS = createTag("drop_spread_items");

        public static final TagKey<Item> WOOL_ITEMS = createTag("wool_items");
        public static final TagKey<Item> WOOL_KNIT_ITEMS = createTag("wool_knit_items");

        public static final TagKey<Item> NORMAL_LEATHERS = createTag("normal_leathers");

        public static final TagKey<Item> SCOURED_LEATHERS = createTag("scoured_leathers");
        public static final TagKey<Item> TANNED_LEATHERS = createTag("tanned_leathers");

        public static final TagKey<Item> COOKED_EGGS = createTag("cooked_eggs");
        public static final TagKey<Item> FISH_FOR_FOOD = createTag("fish_for_food");

        public static final TagKey<Item> BARK_ITEMS = createTag("bark_items");

        public static final TagKey<Item> PICKAXES = createTag("pickaxes");
        public static final TagKey<Item> MODERN_PICKAXES = createTag("modern_pickaxes");
        public static final TagKey<Item> PRIMITIVE_AXES = createTag("primitive_axes");
        public static final TagKey<Item> AXES_MAKE_PLANKS = createTag("axes_make_planks");
        public static final TagKey<Item> PIG_BREEDING_ITEMS = createTag("pig_breeding_items");
        public static final TagKey<Item> PIG_TEMPT_ITEMS = createTag("pig_tempt_items");

        public static final TagKey<Item> STRING_TOOL_MATERIALS = createTag("string_tool_materials");
        public static final TagKey<Item> SHEARS = createTag("shears");



        private static TagKey<Item> createTag (String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(BTWRMod.MOD_ID, name));
        }
    }


}

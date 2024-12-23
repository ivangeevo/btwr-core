package btwr.core.tag;

import btwr.core.BTWRMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BTWRTags {

    public static class Blocks {


        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(BTWRMod.MOD_ID, name));
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
        public static final TagKey<Item> PIG_BREEDING_ITEMS = createTag("pig_breeding_items");
        public static final TagKey<Item> PIG_TEMPT_ITEMS = createTag("pig_tempt_items");



        private static TagKey<Item> createTag (String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(BTWRMod.MOD_ID, name));
        }
    }


}

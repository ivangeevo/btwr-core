package btwr.core.tag;

import btwr.core.BTWRMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BTWRTags {


    public static class Blocks {



        private static TagKey<Block> createTag (String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(BTWRMod.MOD_ID, name));
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
            return TagKey.of(RegistryKeys.ITEM, new Identifier(BTWRMod.MOD_ID, name));
        }
    }

    public static class Mineable
    {
        public static final TagKey<Block> CHISEL_MINEABLE = register("mineable/chisel");

        private static TagKey<Block> register(String id)
        {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(BTWRMod.MOD_ID, id));
        }

    }


}

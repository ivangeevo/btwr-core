package org.btwr.core.tag;

import net.minecraft.entity.EntityType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.btwr.shared_library.util.utils.IdUtils;

public class BTWRTags {

    public static class Blocks {
        // transparent or semi transparent blocks that can let light through
        public static final TagKey<Block> GROUND_COVERS = createTag("ground_covers");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, IdUtils.ofBTWR(name));
        }
    }

    public static class Items {
        public static final TagKey<Item> CLAY_ITEMS = createTag("clay_items");

        public static final TagKey<Item> NORMAL_LEATHERS = createTag("normal_leathers");
        public static final TagKey<Item> SCOURED_LEATHERS = createTag("scoured_leathers");
        public static final TagKey<Item> TANNED_LEATHERS = createTag("tanned_leathers");
        public static final TagKey<Item> CUT_LEATHERS = createTag("cut_leathers");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, IdUtils.ofBTWR(name));
        }
    }

    public static class EntityTypes {
        //public static final TagKey<EntityType<?>> NEUTERABLE_CREEPERS = createTag("neuterable_creepers");

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, IdUtils.ofBTWR(name));
        }
    }

}
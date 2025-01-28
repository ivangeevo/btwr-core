package btwr.core;

import btwr.core.item.BTWR_Items;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BTWRItemGroup {
    public static void registerItemGroups() {
        Registry.register(Registries.ITEM_GROUP,
                Identifier.of(BTWRMod.MOD_ID, "group_btwr"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemgroup.group_btwr"))
                        .icon(() -> new ItemStack(BTWR_Items.GROUP_BTWR))
                        .entries((displayContext, entries) ->
                        {
                            /** ITEMS **/

                            // Uncategorized
                            entries.add(BTWR_Items.DIAMOND_INGOT);
                            entries.add(BTWR_Items.DIAMOND_PLATE);
                            entries.add(BTWR_Items.BRICK_UNFIRED);
                            entries.add(BTWR_Items.HEMP_SEEDS);
                            entries.add(BTWR_Items.HEMP_LEAVES);
                            entries.add(BTWR_Items.HEMP_FIBERS);
                            entries.add(BTWR_Items.HEMP_FABRIC);
                            entries.add(BTWR_Items.LEATHER_SCOURED);
                            entries.add(BTWR_Items.LEATHER_TANNED);
                            entries.add(BTWR_Items.LEATHER_CUT);
                            entries.add(BTWR_Items.LEATHER_SCOURED_CUT);
                            entries.add(BTWR_Items.LEATHER_TANNED_CUT);

                            // Tools
                            entries.add(BTWR_Items.DIAMOND_SHEARS);
                            entries.add(BTWR_Items.CLUB_BONE);
                            entries.add(BTWR_Items.CLUB_WOOD);

                            // Armor
                            entries.add(BTWR_Items.LEATHER_TANNED_HELMET);
                            entries.add(BTWR_Items.LEATHER_TANNED_CHESTPLATE);
                            entries.add(BTWR_Items.LEATHER_TANNED_LEGGINGS);
                            entries.add(BTWR_Items.LEATHER_TANNED_BOOTS);

                            // Food
                            entries.add(BTWR_Items.SANDWICH);
                            entries.add(BTWR_Items.HAM_AND_EGGS);
                            entries.add(BTWR_Items.CHOWDER);
                            entries.add(BTWR_Items.EGG_SCRAMBLED_RAW);
                            entries.add(BTWR_Items.EGG_SCRAMBLED_COOKED);
                            entries.add(BTWR_Items.MUSHROOM_OMELETTE_RAW);
                            entries.add(BTWR_Items.MUSHROOM_OMELETTE_COOKED);
                            entries.add(BTWR_Items.STEAK_AND_POTATOES);
                            entries.add(BTWR_Items.RAW_KEBAB);
                            entries.add(BTWR_Items.COOKED_KEBAB);
                            entries.add(BTWR_Items.STEAK_DINNER);
                            entries.add(BTWR_Items.PORK_DINNER);
                            entries.add(BTWR_Items.WOLF_DINNER);
                            entries.add(BTWR_Items.CHICKEN_SOUP);
                            entries.add(BTWR_Items.HEARTY_STEW);
                            entries.add(BTWR_Items.BEAST_LIVER_RAW);
                            entries.add(BTWR_Items.BEAST_LIVER_COOKED);
                            entries.add(BTWR_Items.CREEPER_OYSTERS);

                        }).build());
    }
}

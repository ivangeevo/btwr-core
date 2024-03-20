package btwr.core;

import btwr.core.item.BTWR_Items;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BTWRItemGroup {

    public static final ItemGroup GROUP_BTWR = Registry.register(Registries.ITEM_GROUP,
            new Identifier(BTWRMod.MOD_ID, "group_btwr"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.group_btwr"))
                    .icon(() -> new ItemStack(BTWR_Items.GROUP_BTWR)).entries((displayContext, entries) -> {
                        entries.add(BTWR_Items.CREEPER_OYSTERS);
                        entries.add(BTWR_Items.DIAMOND_INGOT);
                        entries.add(BTWR_Items.STONE_BRICK);

                        // Tools
                        entries.add(BTWR_Items.DIAMOND_SHEARS);
                        entries.add(BTWR_Items.CLUB_BONE);
                        entries.add(BTWR_Items.CLUB_WOOD);

                    }).build());

    public static void registerItemGroups() {
        /**
        // Example of adding to existing Item Group
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(BTWR_Items.CREEPER_OYSTERS);
            entries.add(BTWR_Items.CLUB_BONE);
            entries.add(BTWR_Items.CLUB_WOOD);
            entries.add(BTWR_Items.DIAMOND_INGOT);
            entries.add(BTWR_Items.DIAMOND_SHEARS);


        });
         **/
    }
}

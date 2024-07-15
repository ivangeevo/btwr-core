package btwr.core;

import btwr.core.block.BTWR_Blocks;
import btwr.core.item.BTWR_Items;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BTWRItemGroup
{

    public static final ItemGroup GROUP_BTWR = Registry.register(Registries.ITEM_GROUP,
            new Identifier(BTWRMod.MOD_ID, "group_btwr"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.group_btwr"))
                    .icon(() -> new ItemStack(BTWR_Items.GROUP_BTWR))
                    .entries((displayContext, entries) ->
                    {
                        /** ITEMS **/

                        // Uncategorized
                        entries.add(BTWR_Items.CREEPER_OYSTERS);
                        entries.add(BTWR_Items.DIAMOND_INGOT);
                        entries.add(BTWR_Items.DIAMOND_PLATE);
                        entries.add(BTWR_Items.STONE_BRICK);
                        entries.add(BTWR_Items.BRICK_UNFIRED);
                        entries.add(BTWR_Items.HEMP_SEEDS);
                        entries.add(BTWR_Items.HEMP_LEAVES);
                        entries.add(BTWR_Items.HEMP_FIBERS);
                        entries.add(BTWR_Items.HEMP_FABRIC);
                        entries.add(BTWR_Items.ROPE);
                        entries.add(BTWR_Items.LEATHER_SCOURED);
                        entries.add(BTWR_Items.LEATHER_TANNED);
                        entries.add(BTWR_Items.LEATHER_CUT);
                        entries.add(BTWR_Items.LEATHER_SCOURED_CUT);
                        entries.add(BTWR_Items.LEATHER_TANNED_CUT);
                        //entries.add(BTWR_Items.NETHERRACK_GROUND);
                        //entries.add(BTWR_Items.DUST_HELLFIRE);
                        //entries.add(BTWR_Items.COAL_NETHER);
                        entries.add(BTWR_Items.STRAP);
                        entries.add(BTWR_Items.BELT);
                        entries.add(BTWR_Items.GEAR);
                        entries.add(BTWR_Items.FILAMENT);


                        // Tools
                        entries.add(BTWR_Items.DIAMOND_SHEARS);
                        entries.add(BTWR_Items.CLUB_BONE);
                        entries.add(BTWR_Items.CLUB_WOOD);

                        // Armor
                        entries.add(BTWR_Items.LEATHER_TANNED_HELMET);
                        entries.add(BTWR_Items.LEATHER_TANNED_CHESTPLATE);
                        entries.add(BTWR_Items.LEATHER_TANNED_LEGGINGS);
                        entries.add(BTWR_Items.LEATHER_TANNED_BOOTS);

                        entries.add(BTWR_Items.SANDWICH);



                        // TO BE ADDED (maybe)
                        //entries.add(BTWR_Items.EGG_RAW);
                        //entries.add(BTWR_Items.EGG_FRIED);
                        //entries.add(BTWR_Items.EGG_POACHED);
                        //entries.add(BTWR_Items.EGG_SCRAMBLED_RAW);
                        //entries.add(BTWR_Items.EGG_SCRAMBLED_COOKED);

                        //entries.add(BTWR_Blocks.COMPANIONCUBE);


                        /** BLOCKS **/

                        // excluded because of some stack size error
                        //entries.add(BTWR_Blocks.LIGHTBLOCK);
                        //entries.add(BTWR_Blocks.ROPE_COIL);

                    }).build());

    public static void registerItemGroups() {
        /**
        // Example of adding to existing Item Group
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries ->
         {

            entries.add(BTWR_Items.CREEPER_OYSTERS);

        });
         **/
    }
}

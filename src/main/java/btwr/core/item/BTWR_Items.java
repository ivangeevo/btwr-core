package btwr.core.item;

import btwr.core.BTWRMod;
import btwr.core.item.items.ClubItem;
import btwr.core.material.BTWR_ToolMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


// This class registers all BTWR items.
public class BTWR_Items {



    // The GROUP_BTWR is first, as it acts as an Item that is called in the BTWRItemGroup class.
    public static final Item GROUP_BTWR = registerItem( "group_btwr", new Item(new FabricItemSettings()));

    // List of Items
    public static final Item CREEPER_OYSTERS = registerItem("creeper_oysters", new Item(new FabricItemSettings().maxCount(16)));
    public static final Item DIAMOND_INGOT = registerItem( "diamond_ingot", new Item (new FabricItemSettings()));



    // --------- //

    // ---------- Tool Items ---------- //

    public static final Item CLUB_WOOD = registerItem("club_wood",
            new ClubItem(ToolMaterials.WOOD,1,-1.1f, new FabricItemSettings()));

    public static final Item CLUB_BONE = registerItem("club_bone",
            new ClubItem(BTWR_ToolMaterials.BONE, 2,-1.3f, new FabricItemSettings()));

    public static final Item DIAMOND_SHEARS = registerItem( "diamond_shears", new ShearsItem (new FabricItemSettings().maxDamage(500)));



    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(CREEPER_OYSTERS);
        entries.add(DIAMOND_INGOT);

    }


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(BTWRMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        BTWRMod.LOGGER.info("Registering Mod Items for " + BTWRMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(BTWR_Items::addItemsToIngredientItemGroup);
    }



    // ---------------------- //
}

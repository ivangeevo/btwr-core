package btwr.core.item;

import btwr.core.BTWRMod;
import btwr.core.block.BTWR_Blocks;
import btwr.core.item.items.ClubItem;
import btwr.core.material.BTWR_ToolMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
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
    public static final Item STONE_BRICK = registerItem( "stone_brick", new Item (new FabricItemSettings()));
    public static final Item BRICK_UNFIRED = registerItem( "brick_unfired", new Item (new FabricItemSettings()));

    public static final Item HEMP_SEEDS = registerItem( "hemp_seeds",
            new AliasedBlockItem(BTWR_Blocks.CROP_HEMP, new FabricItemSettings()));

    public static final Item HEMP_LEAVES = registerItem( "hemp_leaves", new Item(new FabricItemSettings()));
    public static final Item HEMP_FIBERS = registerItem( "hemp_fibers", new Item(new FabricItemSettings()));
    public static final Item HEMP_FABRIC = registerItem( "hemp_fabric", new Item(new FabricItemSettings()));

    public static final Item ROPE = registerItem( "rope", new Item(new FabricItemSettings()));

    public static final Item LEATHER_CUT = registerItem( "leather_cut", new Item (new FabricItemSettings()));
    public static final Item LEATHER_SCOURED = registerItem( "leather_scoured", new Item (new FabricItemSettings()));
    public static final Item LEATHER_SCOURED_CUT = registerItem( "leather_scoured_cut", new Item (new FabricItemSettings()));
    public static final Item LEATHER_TANNED = registerItem( "leather_tanned", new Item (new FabricItemSettings()));
    public static final Item LEATHER_TANNED_CUT = registerItem( "leather_tanned_cut", new Item (new FabricItemSettings()));

    public static final Item NETHERRACK_GROUND = registerItem( "netherrack_ground", new Item (new FabricItemSettings()));
    public static final Item DUST_HELLFIRE = registerItem( "dust_hellfire", new Item (new FabricItemSettings()));
    public static final Item COAL_NETHER = registerItem( "coal_nether", new Item (new FabricItemSettings()));

    public static final Item STRAP = registerItem( "strap", new Item (new FabricItemSettings()));
    public static final Item BELT = registerItem( "belt", new Item (new FabricItemSettings()));
    public static final Item GEAR = registerItem( "gear", new Item (new FabricItemSettings()));

    // --------- //

    // ---------- Tool Items ---------- //

    public static final Item CLUB_WOOD = registerItem("club_wood",
            new ClubItem(ToolMaterials.WOOD,1,-1.1f, new FabricItemSettings()));

    public static final Item CLUB_BONE = registerItem("club_bone",
            new ClubItem(BTWR_ToolMaterials.BONE, 2,-1.3f, new FabricItemSettings()));

    public static final Item DIAMOND_SHEARS = registerItem( "diamond_shears",
            new ShearsItem (new FabricItemSettings().maxDamage(500)));




    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries)
    {

        entries.add(CREEPER_OYSTERS);

    }

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, new Identifier(BTWRMod.MOD_ID, name), item);
    }

    public static void registerModItems()
    {
        BTWRMod.LOGGER.info("Registering Mod Items for " + BTWRMod.MOD_ID);
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(BTWR_Items::addItemsToIngredientItemGroup);
    }



    // ---------------------- //
}

package btwr.core.item;

import btwr.core.BTWRMod;
import btwr.core.block.BTWR_Blocks;
import btwr.core.item.items.ClubItem;
import btwr.core.material.BTWR_ToolMaterials;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


// This class registers all BTWR items.
public class BTWR_Items {



    // The GROUP_BTWR is first, as it acts as an Item that is called in the BTWRItemGroup class.
    public static final Item GROUP_BTWR = registerItem( "group_btwr", new Item(new Item.Settings()));

    public static final Item CREEPER_OYSTERS = registerItem("creeper_oysters", new Item(new Item.Settings().maxCount(16)));
    public static final Item DIAMOND_INGOT = registerItem( "diamond_ingot", new Item (new Item.Settings()));

    public static final Item DIAMOND_PLATE = registerItem("diamond_plate", new Item(new Item.Settings()));

    public static final Item STONE_BRICK = registerItem( "stone_brick", new Item (new Item.Settings()));

    public static final Item HEMP_SEEDS = registerItem( "hemp_seeds",
            new AliasedBlockItem(BTWR_Blocks.CROP_HEMP, new Item.Settings()));
    public static final Item HEMP_LEAVES = registerItem( "hemp_leaves", new Item(new Item.Settings()));
    public static final Item HEMP_FIBERS = registerItem( "hemp_fibers", new Item(new Item.Settings()));
    public static final Item HEMP_FABRIC = registerItem( "hemp_fabric", new Item(new Item.Settings()));
    public static final Item ROPE = registerItem( "rope", new Item(new Item.Settings()));

    public static final Item LEATHER_CUT = registerItem( "leather_cut", new Item (new Item.Settings()));
    public static final Item LEATHER_SCOURED = registerItem( "leather_scoured", new Item (new Item.Settings()));
    public static final Item LEATHER_SCOURED_CUT = registerItem( "leather_scoured_cut", new Item (new Item.Settings()));
    public static final Item LEATHER_TANNED = registerItem( "leather_tanned", new Item (new Item.Settings()));
    public static final Item LEATHER_TANNED_CUT = registerItem( "leather_tanned_cut", new Item (new Item.Settings()));

    public static final Item STRAP = registerItem( "strap", new Item (new Item.Settings()));
    public static final Item BELT = registerItem( "belt", new Item (new Item.Settings()));
    public static final Item GEAR = registerItem( "gear", new Item (new Item.Settings()));
    public static final Item FILAMENT = registerItem( "filament", new Item (new Item.Settings()));



    public static final Item BRICK_UNFIRED = registerItem( "brick_unfired", new AliasedBlockItem(BTWR_Blocks.BRICK_UNFIRED ,new Item.Settings()));

    // --------- //

    // ---------- Tool Items ---------- //

    public static final Item CLUB_WOOD = registerItem("club_wood",
            new ClubItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(ClubItem.createAttributeModifiers(ToolMaterials.WOOD,1, -1.1f))));

    public static final Item CLUB_BONE = registerItem("club_bone",
            new ClubItem(BTWR_ToolMaterials.BONE, new Item.Settings().attributeModifiers(ClubItem.createAttributeModifiers(BTWR_ToolMaterials.BONE,2, -1.3f))));

    public static final Item DIAMOND_SHEARS = registerItem( "diamond_shears",
            new ShearsItem (new Item.Settings().maxDamage(500)));
    
    // Armor

    public static final Item LEATHER_TANNED_HELMET = registerItem("leather_tanned_helmet", new ArmorItem(BTWRArmorMaterials.LEATHER_TANNED,  ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(7))));
    public static final Item LEATHER_TANNED_CHESTPLATE = registerItem("leather_tanned_chestplate", new ArmorItem(BTWRArmorMaterials.LEATHER_TANNED, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(7))));
    public static final Item LEATHER_TANNED_LEGGINGS = registerItem("leather_tanned_leggings", new ArmorItem(BTWRArmorMaterials.LEATHER_TANNED, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(7))));
    public static final Item LEATHER_TANNED_BOOTS = registerItem("leather_tanned_boots", new ArmorItem(BTWRArmorMaterials.LEATHER_TANNED, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(7))));



    // Food items

    public static final Item SANDWICH = registerItem( "sandwich", new Item (new Item.Settings().food(ModFoodComponents.SANDWICH)));


    //public static final Item EGG_RAW = registerItem("egg_raw", new Item( new Item.Settings().food(ModFoodComponents.EGG_RAW)));
    //public static final Item EGG_SCRAMBLED_RAW = registerItem("egg_scrambled_raw", new Item( new Item.Settings().food(ModFoodComponents.EGG_SCRAMBLED_RAW)));
    //public static final Item EGG_FRIED = registerItem("egg_fried", new Item( new Item.Settings().food(ModFoodComponents.EGG_COOKED)));
    //public static final Item EGG_POACHED = registerItem("egg_poached", new Item( new Item.Settings().food(ModFoodComponents.EGG_COOKED)));
    //public static final Item EGG_SCRAMBLED_COOKED = registerItem("egg_scrambled_cooked", new Item( new Item.Settings().food(ModFoodComponents.EGG_SCRAMBLED_COOKED)));




    // TO BE ADDED :


    //public static final Item NETHERRACK_GROUND = registerItem( "netherrack_ground", new Item (new Item.Settings()));
    //public static final Item DUST_HELLFIRE = registerItem( "dust_hellfire", new Item (new Item.Settings()));
    //public static final Item COAL_NETHER = registerItem( "coal_nether", new Item (new Item.Settings()));





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

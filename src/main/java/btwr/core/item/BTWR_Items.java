package btwr.core.item;

import btwr.core.BTWRMod;
import btwr.core.block.BTWR_Blocks;
import btwr.core.block.blocks.HempCropBlock;
import btwr.core.block.blocks.LightBlock;
import btwr.core.item.items.ClubItem;
import btwr.core.material.BTWR_ToolMaterials;
import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;


// This class registers all BTWR items.
public class BTWR_Items {



    // The GROUP_BTWR is first, as it acts as an Item that is called in the BTWRItemGroup class.
    public static final Item GROUP_BTWR = registerItem( "group_btwr", new Item(new Item.Settings()));

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
            new ShearsItem (new Item.Settings().maxDamage(500).component(DataComponentTypes.TOOL, ShearsItem.createToolComponent())));
    
    // Armor

    public static final Item LEATHER_TANNED_HELMET = registerItem("leather_tanned_helmet", new ArmorItem(BTWRArmorMaterials.LEATHER_TANNED,  ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(7))));
    public static final Item LEATHER_TANNED_CHESTPLATE = registerItem("leather_tanned_chestplate", new ArmorItem(BTWRArmorMaterials.LEATHER_TANNED, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(7))));
    public static final Item LEATHER_TANNED_LEGGINGS = registerItem("leather_tanned_leggings", new ArmorItem(BTWRArmorMaterials.LEATHER_TANNED, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(7))));
    public static final Item LEATHER_TANNED_BOOTS = registerItem("leather_tanned_boots", new ArmorItem(BTWRArmorMaterials.LEATHER_TANNED, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(7))));



    // Food items

    // Raw
    public static final Item EGG_SCRAMBLED_RAW = registerItem("egg_scrambled_raw", new Item( new Item.Settings().food(ModFoodComponents.EGGS_SCRAMBLED_RAW).maxCount(16)));
    public static final Item MUSHROOM_OMELETTE_RAW = registerItem("mushroom_omelette_raw", new Item( new Item.Settings().food(ModFoodComponents.MUSHROOM_OMELETTE_RAW).maxCount(16)));

    // Cooked
    public static final Item EGG_SCRAMBLED_COOKED = registerItem("egg_scrambled_cooked", new Item( new Item.Settings().food(ModFoodComponents.EGG_SCRAMBLED_COOKED).maxCount(16)));
    public static final Item MUSHROOM_OMELETTE_COOKED = registerItem("mushroom_omelette_cooked", new Item( new Item.Settings().food(ModFoodComponents.MUSHROOM_OMELETTE_COOKED).maxCount(16)));
    public static final Item BOILED_POTATO = registerItem( "boiled_potato", new Item (new Item.Settings().food(ModFoodComponents.BOILED_POTATO).maxCount(16)));
    public static final Item COOKED_CARROT = registerItem( "cooked_carrot", new Item (new Item.Settings().food(ModFoodComponents.COOKED_CARROT).maxCount(16)));

    // Special food items
    public static final Item SANDWICH = registerItem( "sandwich", new Item (new Item.Settings().food(ModFoodComponents.SANDWICH).maxCount(16)));
    public static final Item HAM_AND_EGGS = registerItem( "ham_and_eggs", new Item (new Item.Settings().food(ModFoodComponents.HAM_AND_EGGS).maxCount(16)));
    public static final Item CHOWDER = registerItem( "chowder", new Item (new Item.Settings().food(ModFoodComponents.CHOWDER).maxCount(16)));
    public static final Item STEAK_AND_POTATOES = registerItem( "steak_and_potatoes", new Item (new Item.Settings().food(ModFoodComponents.STEAK_AND_POTATOES).maxCount(16)));
    public static final Item RAW_KEBAB = registerItem( "raw_kebab", new Item (new Item.Settings().food(ModFoodComponents.KEBAB_RAW).maxCount(16)));
    public static final Item COOKED_KEBAB = registerItem( "cooked_kebab", new Item (new Item.Settings().food(ModFoodComponents.KEBAB_COOKED).maxCount(16)));
    public static final Item STEAK_DINNER = registerItem( "steak_dinner", new Item (new Item.Settings().food(ModFoodComponents.STEAK_DINNER).maxCount(16)));
    public static final Item PORK_DINNER = registerItem( "pork_dinner", new Item (new Item.Settings().food(ModFoodComponents.PORK_DINNER).maxCount(16)));
    public static final Item WOLF_DINNER = registerItem( "wolf_dinner", new Item (new Item.Settings().food(ModFoodComponents.WOLF_DINNER).maxCount(16)));

    public static final Item CHICKEN_SOUP = registerItem( "chicken_soup", new Item (new Item.Settings().food(ModFoodComponents.CHICKEN_SOUP).maxCount(16)));
    public static final Item HEARTY_STEW = registerItem( "hearty_stew", new Item (new Item.Settings().food(ModFoodComponents.HEARTY_STEW).maxCount(16)));

    // Unique food items
    public static final Item BEAST_LIVER_RAW = registerItem( "beast_liver_raw", new Item (new Item.Settings().food(ModFoodComponents.BEAST_LIVER_RAW).maxCount(16)));
    public static final Item BEAST_LIVER_COOKED = registerItem( "beast_liver_cooked", new Item (new Item.Settings().food(ModFoodComponents.BEAST_LIVER_COOKED).maxCount(16)));
    public static final Item CREEPER_OYSTERS = registerItem("creeper_oysters", new Item(new Item.Settings().food(ModFoodComponents.CREEPER_OYSTERS).maxCount(16)));


    // Blocks to Items experiment
    public static final Item LIGHTBLOCK = register(BTWR_Blocks.LIGHTBLOCK);
    public static final Item ROPE_COIL = register(BTWR_Blocks.ROPE_COIL);



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
        return Registry.register(Registries.ITEM, Identifier.of(BTWRMod.MOD_ID, name), item);
    }

    public static void registerModItems()
    {
        BTWRMod.LOGGER.info("Registering Mod Items for " + BTWRMod.MOD_ID);
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(BTWR_Items::addItemsToIngredientItemGroup);
    }

    public static Item register(Block block) {
        return register(new BlockItem(block, new Item.Settings()));
    }

    public static Item register(BlockItem item) {
        return register(item.getBlock(), item);
    }

    public static Item register(Block block, Item item) {
        return register(Registries.BLOCK.getId(block), item);
    }

    public static Item register(Identifier id, Item item) {
        return register(RegistryKey.of(Registries.ITEM.getKey(), id), item);
    }

    public static Item register(RegistryKey<Item> key, Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, key, item);
    }



    // ---------------------- //
}

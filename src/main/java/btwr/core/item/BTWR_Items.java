package btwr.core.item;

import btwr.core.BTWRMod;
import btwr.core.item.items.ClubItem;
import btwr.core.material.BTWR_ToolMaterials;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


// This class registers all BTWR items.
public class BTWR_Items {


    // Registered the ItemGroup here as a static class. Let's hope it doesn't crash and burn lol
    public static class ItemGroups {
        public static final ItemGroup GROUP_BTWR = FabricItemGroupBuilder.build(new Identifier(BTWRMod.MOD_ID, "group_btwr"), () -> new ItemStack(BTWR_Items.GROUP_BTWR));

    }

    // The GROUP_BTWR is first, as it acts as an Item that is called in the BTWRItemGroup class.
    public static final Item GROUP_BTWR = registerItem( "group_btwr", new Item(new FabricItemSettings()));

    // List of Items

    public static final Item CREEPER_OYSTERS = registerItem("creeper_oysters", new Item(new FabricItemSettings().maxCount(16).group(ItemGroups.GROUP_BTWR)));

    public static final Item DIAMOND_INGOT = registerItem( "diamond_ingot", new Item (new FabricItemSettings().group(ItemGroups.GROUP_BTWR)));


    // --------- //

    // ---------- Tool Items ---------- //

    public static final Item CLUB_WOOD = registerItem("club_wood",
            new ClubItem(ToolMaterials.WOOD,1,-1.1f, new FabricItemSettings().group(ItemGroups.GROUP_BTWR)));

    public static final Item CLUB_BONE = registerItem("club_bone",
            new ClubItem(BTWR_ToolMaterials.BONE, 1,-1.3f, new FabricItemSettings().group(ItemGroups.GROUP_BTWR)));







    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(BTWRMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        BTWRMod.LOGGER.info("Registering Mod Items for " + BTWRMod.MOD_ID);

    }



    // ---------------------- //
}

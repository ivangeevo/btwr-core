package btwr.core.registry;

import btwr.core.item.BTWR_Items;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class ModFuelItems
{

    public static void register()
    {
        FuelRegistry.INSTANCE.add(BTWR_Items.CLUB_WOOD, 100);
        FuelRegistry.INSTANCE.add(BTWR_Items.HEMP_FIBERS, 25);
        FuelRegistry.INSTANCE.add(BTWR_Items.HEMP_LEAVES, 100);
        FuelRegistry.INSTANCE.add(BTWR_Items.HEMP_FABRIC, 225);
        FuelRegistry.INSTANCE.add(BTWR_Items.HEMP_SEEDS, 15);

    }
}

package btwr.core;

import btwr.core.block.BTWR_Blocks;
import btwr.core.item.BTWR_Items;
import net.fabricmc.api.ModInitializer;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTWRMod implements ModInitializer {

    public static final String MOD_ID = "btwr";

    public static final String MOD_VERSION = "0.22";
    public static final Logger LOGGER = LoggerFactory.getLogger("btwr");

    @Override
    public void onInitialize() {
        BTWRItemGroup.registerItemGroups();

        BTWR_Blocks.registerModBlocks();
        BTWR_Items.registerModItems();



        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.SMOOTH_STONE)
                .lightWithItem(Items.BUCKET)
                .destDimID(new Identifier(BTWRMod.MOD_ID, "thehemmet"))
                .tintColor(0xc76efa).flatPortal()
                .registerPortal();

    }
}

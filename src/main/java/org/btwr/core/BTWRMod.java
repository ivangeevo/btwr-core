package org.btwr.core;

import org.btwr.core.api.BlightSpreadConditions;
import org.btwr.core.block.BTWR_Blocks;
import org.btwr.core.config.BTWRModConfig;
import org.btwr.core.difficulty.BTWRDifficulties;
import org.btwr.core.event.BTWR_Events;
import org.btwr.core.item.BTWR_Items;
import net.fabricmc.api.ModInitializer;
import org.btwr.core.networking.BTWR_Networking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTWRMod implements ModInitializer {

    public static final String MOD_ID = "btwr";
    public static final String MOD_NAME = "BTWR: Core";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing BTWR: Core.");
        BTWRDifficulties.register();
        BTWRModConfig.register();

        BTWR_Blocks.register();
        BTWR_Items.register();
        BTWRItemGroup.register();
        BTWR_Events.register();
        BTWR_Networking.register();

        // Register default conditions for blight being able to spread
        BlightSpreadConditions.registerDefaults();


        // Not useful unless global mob cap is increased
        //SurfaceReinforcement.init();
    }

}
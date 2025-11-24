package org.btwr.core;

import org.btwr.core.block.BTWR_Blocks;
import org.btwr.core.config.BTWRModConfig;
import org.btwr.core.data.BTWRDataAttachments;
import org.btwr.core.event.BTWREntityEvents;
import org.btwr.core.item.BTWR_Items;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTWRMod implements ModInitializer {
    public static final String MOD_ID = "btwr";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing BTWR: Core.");

        BTWRModConfig.register();
        BTWRItemGroup.register();
        BTWR_Blocks.register();
        BTWR_Items.register();
        BTWRDataAttachments.register();
        BTWREntityEvents.register();
    }
}
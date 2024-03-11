package btwr.core.client;

import btwr.core.BTWRMod;
import btwr.core.config.BTWRSettingsGUI;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;

public class BTWRModClient implements ClientModInitializer {

    private static BTWRModClient instance;
    public BTWRSettingsGUI settingsGUI;
    public static BTWRModClient getInstance() { return instance; }

    public static final Logger LOGGER = BTWRMod.LOGGER;
    @Override
    public void onInitializeClient() {
        instance = this;
        settingsGUI = new BTWRSettingsGUI();

    }


}

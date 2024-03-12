package btwr.core.config;

import btwr.core.client.BTWRModClient;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class BTWRModMenu implements ModMenuApi
{

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return BTWRSettingsGUI::createConfigScreen;
    }

}
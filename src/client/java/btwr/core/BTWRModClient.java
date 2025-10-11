package btwr.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.slf4j.Logger;

import java.util.List;

public class BTWRModClient implements ClientModInitializer {

    public static final Logger LOGGER = BTWRMod.LOGGER;
    @Override
    public void onInitializeClient() {
        //this.hideArbitraryFAPIMods();
    }

    // This removes all arbitrary mods that are loaded by FAPI by default from showing up in the loaded mods text,
    // so it can display a more accurate number of the actual mods loaded
    private void hideArbitraryFAPIMods() {
        List<ModContainer> filteredMods = FabricLoader.getInstance().getAllMods().stream()
                .filter(mod -> !mod.getMetadata().getId().startsWith("fabric"))
                .toList();

        System.out.println("Filtered Mods Count: " + filteredMods.size());
        filteredMods.forEach(mod -> System.out.println("Loaded Mod: " + mod.getMetadata().getName()));

    }


}

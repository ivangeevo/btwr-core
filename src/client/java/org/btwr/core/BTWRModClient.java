package org.btwr.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.List;

public class BTWRModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //this.hideArbitraryFabricAPIMods();
    }

    // This removes all arbitrary mods that are loaded by FAPI by default from showing up in the loaded mods text,
    // so it can display a more accurate number of the actual mods loaded
    private void hideArbitraryFabricAPIMods() {
        List<ModContainer> filteredMods = FabricLoader.getInstance().getAllMods().stream()
                .filter(mod -> !mod.getMetadata().getId().startsWith("fabric"))
                .toList();

        System.out.println("Filtered Mods Count: " + filteredMods.size());
        filteredMods.forEach(mod -> System.out.println("Loaded Mod: " + mod.getMetadata().getName()));
    }
}
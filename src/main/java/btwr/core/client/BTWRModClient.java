package btwr.core.client;

import btwr.core.BTWRMod;
import btwr.core.block.BTWR_Blocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.render.RenderLayer;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class BTWRModClient implements ClientModInitializer {

    public static final Logger LOGGER = BTWRMod.LOGGER;
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BTWR_Blocks.CROP_HEMP, RenderLayer.getCutout());

        //this.initializeHiddenModsDisplayed();

    }

    private void initializeHiddenModsDisplayed() {
        List<ModContainer> filteredMods = FabricLoader.getInstance().getAllMods().stream()
                .filter(mod -> !mod.getMetadata().getId().startsWith("fabric"))
                .toList();

        System.out.println("Filtered Mods Count: " + filteredMods.size());
        filteredMods.forEach(mod -> System.out.println("Loaded Mod: " + mod.getMetadata().getName()));

    }


}

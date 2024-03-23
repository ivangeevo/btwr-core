package btwr.core.client;

import btwr.core.BTWRMod;
import btwr.core.block.BTWR_Blocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import org.slf4j.Logger;

public class BTWRModClient implements ClientModInitializer
{

    public static final Logger LOGGER = BTWRMod.LOGGER;
    @Override
    public void onInitializeClient()
    {
        BlockRenderLayerMap.INSTANCE.putBlock(BTWR_Blocks.LIGHTBLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BTWR_Blocks.CROP_HEMP, RenderLayer.getCutout());

    }


}

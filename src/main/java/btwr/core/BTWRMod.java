package btwr.core;

import btwr.core.block.BTWR_Blocks;
import btwr.core.item.BTWR_Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTWRMod implements ModInitializer
{
    public static final String MOD_ID = "btwr";
    public static final Logger LOGGER = LoggerFactory.getLogger("btwr");


    @Override
    public void onInitialize()
    {
        LOGGER.info("Initializing BTWR-Core.");

        BTWRItemGroup.registerItemGroups();
        BTWR_Blocks.registerModBlocks();
        BTWR_Items.registerModItems();


    }

}

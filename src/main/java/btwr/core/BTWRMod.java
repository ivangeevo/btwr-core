package btwr.core;

import btwr.core.block.BTWR_Blocks;
import btwr.core.item.BTWR_Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTWRMod implements ModInitializer {

    public static final String MOD_ID = "btwr";

    public static final String MOD_VERSION = "0.22";
    public static final Logger LOGGER = LoggerFactory.getLogger("btwr");

    @Override
    public void onInitialize() {
        BTWR_Blocks.registerModBlocks();

        BTWR_Items.registerModItems();

    }
}

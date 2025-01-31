package btwr.core.entity;

import btwr.core.BTWRMod;
import btwr.core.block.BTWR_Blocks;
import btwr.core.block.entity.UnfiredBrickBE;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BTWR_EntityTypes
{

    public static class Blocks
    {
        public static BlockEntityType<UnfiredBrickBE> BRICK_UNFIRED;

        public static void registerBlockEntities()
        {
            BRICK_UNFIRED = Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(BTWRMod.MOD_ID, "brick_unfired"),
                    BlockEntityType.Builder.create(UnfiredBrickBE::new, BTWR_Blocks.BRICK_UNFIRED).build()
            );
        }


    }
}

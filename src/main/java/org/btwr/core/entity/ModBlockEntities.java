package org.btwr.core.entity;

import com.bwt.utils.Id;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.btwr.core.BTWRMod;
import org.btwr.core.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<CementBlockEntity> CEMENT_BLOCK_ENTITY;

    public static void initialize() {
        CEMENT_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(BTWRMod.MOD_ID, "cement_block_entity"),
                BlockEntityType.Builder.create(
                        CementBlockEntity::new,
                        ModBlocks.CEMENT
                ).build()
        );
    }
}

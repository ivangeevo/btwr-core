package org.btwr.core.data;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import org.btwr.core.BTWRMod;
import org.btwr.core.data.attached.ItemBuoyancyData;

public class ModDataAttachments {
    public static final AttachmentType<ItemBuoyancyData> ITEM_BUOYANCY = AttachmentRegistry.create(
            Identifier.of(BTWRMod.MOD_ID, "item_buoyancy"),
            builder -> builder
                    .initializer(ItemBuoyancyData::forDefault)
                    .persistent(ItemBuoyancyData.CODEC)
                    .syncWith(ItemBuoyancyData.PACKET_CODEC, AttachmentSyncPredicate.all())
    );

    public static void initialize() {
        BTWRMod.LOGGER.info("Registering mod data attachments for {}", BTWRMod.MOD_NAME);
    }

}
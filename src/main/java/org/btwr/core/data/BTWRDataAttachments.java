package org.btwr.core.data;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.btwr.core.BTWRMod;
import org.btwr.shared_library.api.data.EntityAttachmentBase;
import org.btwr.shared_library.api.event.BTWREvents;
import org.btwr.shared_library.util.utils.IdUtils;

public class BTWRDataAttachments {
    public static final AttachmentType<CreeperData> CREEPER_DATA = AttachmentRegistry.create(
            IdUtils.ofBTWR("creeper_data"),
            builder -> builder
                    .initializer(() -> new CreeperData(false, false))
                    .persistent(CreeperData.CODEC)
                    .syncWith(CreeperData.PACKET_CODEC, AttachmentSyncPredicate.all())
    );

    public static void register() {
        BTWRMod.LOGGER.info("Registering {} attachments", BTWRMod.MOD_ID);
        // Technically this method can stay empty, but some developers like to notify
        // the console, that certain parts of the mod have been successfully initialized

        BTWREvents.LIVING_TICK.add(living -> {
            tickAndSync(CREEPER_DATA, living);
        });
    }

    private static <T extends Entity, A extends EntityAttachmentBase<T>> void tickAndSync(AttachmentType<A> type, LivingEntity entity) {
        A attachment = entity.getAttachedOrCreate(type);
        attachment.tick((T) entity);
        if (attachment.isDirty()) {
            entity.setAttached(type, attachment);
        }
    }

}
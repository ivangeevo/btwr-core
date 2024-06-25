package btwr.core.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface ItemUsageFinishCallback {
    Event<ItemUsageFinishCallback> EVENT = EventFactory.createArrayBacked(ItemUsageFinishCallback.class,
            (listeners) -> (player, world, hand) -> {
                for (ItemUsageFinishCallback listener : listeners) {
                    listener.onItemUsageFinish(player, world, hand);
                }
            });

    void onItemUsageFinish(PlayerEntity player, World world, Hand hand);
}

package org.btwr.core.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.btwr.api.api.difficulty.DifficultyRegistry;
import org.btwr.api.api.difficulty.impl.BTWRDifficulty;
import org.btwr.core.networking.packet.SyncBTWRDifficultyS2CPacket;

public final class BTWR_ClientNetworking {
    public static void register() {
        registerDifficultyNetworking();
    }

    private static void registerDifficultyNetworking() {
        ClientPlayNetworking.registerGlobalReceiver(
                SyncBTWRDifficultyS2CPacket.ID,
                (packet, context) -> context.client().execute(() -> {
                    BTWRDifficulty difficulty = DifficultyRegistry.get(packet.info().id());
                    if (difficulty != null) {
                        ClientBTWRDifficultyCache.set(difficulty);
                        ClientBTWRDifficultyCache.setLocked(packet.info().locked());
                    }
                })
        );
    }
}
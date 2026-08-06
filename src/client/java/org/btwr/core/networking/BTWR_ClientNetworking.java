package org.btwr.core.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.btwr.core.api.DifficultyRegistry;
import org.btwr.core.difficulty.impl.BTWRDifficulty;

public final class BTWR_ClientNetworking {
    public static void register() {
        registerDifficultyNetworking();
    }

    private static void registerDifficultyNetworking() {
        ClientPlayNetworking.registerGlobalReceiver(
                SyncBTWRDifficultyS2CPacket.ID,
                (packet, context) -> context.client().execute(() -> {
                    BTWRDifficulty difficulty = DifficultyRegistry.get(packet.difficultyId());
                    if (difficulty != null) {
                        ClientBTWRDifficultyCache.set(difficulty);
                    }
                })
        );
    }
}
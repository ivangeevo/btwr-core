package org.btwr.core.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class BTWR_Events {
    public static void register() {
        registerNetworkingEvents();
    }

    private static void registerNetworkingEvents() {
        // Initialize the difficulty data if it's null
        ServerLifecycleEvents.SERVER_STARTED.register(BTWRDifficultyHelper::onServerStartedEvent);

        // Sync the difficulty data from server to client
        ServerPlayConnectionEvents.JOIN.register(BTWRDifficultyHelper::onPlayerJoinEvent);
    }
}
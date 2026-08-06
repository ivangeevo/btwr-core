package org.btwr.core.event;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.btwr.core.data.saved.BTWRDifficultyData;
import org.btwr.core.difficulty.impl.BTWRDifficulty;
import org.btwr.core.networking.SyncBTWRDifficultyS2CPacket;

public class BTWRDifficultyHelper {

    private BTWRDifficultyHelper() {}

    public static void onServerStartedEvent(MinecraftServer server) {
        BTWRDifficultyData data = BTWRDifficultyData.get(server);
        if (!data.hasDifficulty()) {
            data.setDifficulty(BTWRWorldCreationData.getSelected());
            BTWRWorldCreationData.reset();
        }
    }

    public static void onPlayerJoinEvent(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        BTWRDifficulty difficulty = BTWRDifficultyData.get(server).getDifficulty();
        ServerPlayNetworking.send(handler.player, new SyncBTWRDifficultyS2CPacket(difficulty.id()));
    }

}
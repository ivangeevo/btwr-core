package org.btwr.core.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.btwr.core.api.DifficultyRegistry;
import org.btwr.core.data.saved.BTWRDifficultyData;
import org.btwr.core.difficulty.impl.BTWRDifficulty;

public class BTWR_Networking {
    public static void register() {
        registerDifficultyNetworking();
    }

    private static void registerDifficultyNetworking() {
        PayloadTypeRegistry.playC2S().register(
                UpdateBTWRDifficultyC2SPacket.ID,
                UpdateBTWRDifficultyC2SPacket.PACKET_CODEC
        );

        PayloadTypeRegistry.playS2C().register(
                SyncBTWRDifficultyS2CPacket.ID,
                SyncBTWRDifficultyS2CPacket.PACKET_CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
                UpdateBTWRDifficultyC2SPacket.ID, BTWR_Networking::difficultyPayloadHandler
        );
    }

    private static void difficultyPayloadHandler(UpdateBTWRDifficultyC2SPacket packet, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {

            if (!context.player().hasPermissionLevel(2))
                return;

            BTWRDifficulty difficulty = DifficultyRegistry.get(packet.difficultyId());

            if (difficulty == null)
                return;

            MinecraftServer server = context.server();

            BTWRDifficultyData data = BTWRDifficultyData.get(server);

            data.setDifficulty(difficulty);

            SyncBTWRDifficultyS2CPacket sync = new SyncBTWRDifficultyS2CPacket(difficulty.id());

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ServerPlayNetworking.send(player, sync);
            }
        });
    }
}
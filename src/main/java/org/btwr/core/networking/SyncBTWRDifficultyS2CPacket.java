package org.btwr.core.networking;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.btwr.core.BTWRMod;

public record SyncBTWRDifficultyS2CPacket(Identifier difficultyId) implements CustomPayload {
    public static final Id<SyncBTWRDifficultyS2CPacket> ID = new Id<>(
            Identifier.of(BTWRMod.MOD_ID, "sync_difficulty")
    );

    public static final PacketCodec<PacketByteBuf, SyncBTWRDifficultyS2CPacket> PACKET_CODEC =
            PacketCodec.tuple(
                    Identifier.PACKET_CODEC,
                    SyncBTWRDifficultyS2CPacket::difficultyId,
                    SyncBTWRDifficultyS2CPacket::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
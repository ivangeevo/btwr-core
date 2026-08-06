package org.btwr.core.networking;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.btwr.core.BTWRMod;

public record UpdateBTWRDifficultyC2SPacket(Identifier difficultyId) implements CustomPayload {
    public static final CustomPayload.Id<UpdateBTWRDifficultyC2SPacket> ID = new CustomPayload.Id<>(
            Identifier.of(BTWRMod.MOD_ID, "update_difficulty")
    );

    public static final PacketCodec<PacketByteBuf, UpdateBTWRDifficultyC2SPacket> PACKET_CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC, UpdateBTWRDifficultyC2SPacket::difficultyId, UpdateBTWRDifficultyC2SPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
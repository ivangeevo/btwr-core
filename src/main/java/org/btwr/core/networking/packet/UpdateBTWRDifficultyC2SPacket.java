package org.btwr.core.networking.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.btwr.api.api.difficulty.impl.DifficultyInfo;
import org.btwr.core.BTWRMod;

public record UpdateBTWRDifficultyC2SPacket(DifficultyInfo info) implements CustomPayload {
    public static final CustomPayload.Id<UpdateBTWRDifficultyC2SPacket> ID = new CustomPayload.Id<>(
            Identifier.of(BTWRMod.MOD_ID, "update_difficulty")
    );

    public static final PacketCodec<RegistryByteBuf, UpdateBTWRDifficultyC2SPacket> PACKET_CODEC = PacketCodec.tuple(
            DifficultyInfo.PACKET_CODEC, UpdateBTWRDifficultyC2SPacket::info, UpdateBTWRDifficultyC2SPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
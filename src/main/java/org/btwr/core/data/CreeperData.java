package org.btwr.core.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import org.btwr.shared_library.data.UpdateRequiringData;

public class CreeperData extends UpdateRequiringData<CreeperEntity> {
    public static Codec<CreeperData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("is_neutered").forGetter(CreeperData::isNeutered),
                    Codec.BOOL.fieldOf("is_determined_to_explode").forGetter(CreeperData::getIsDeterminedToExplode)
            ).apply(instance, CreeperData::new)
    );

    public static PacketCodec<ByteBuf, CreeperData> PACKET_CODEC = PacketCodecs.codec(CODEC);

    private boolean isNeutered;
    private boolean isDeterminedToExplode;

    public CreeperData(boolean isNeutered, boolean isDeterminedToExplode) {
        this.isNeutered = isNeutered;
        this.isDeterminedToExplode = isDeterminedToExplode;
    }

    public boolean isNeutered() {
        return isNeutered;
    }

    public boolean canNeuter() {
        return !isNeutered;
    }

    public void setNeutered(boolean value) {
        isNeutered = value;
    }

    public boolean getIsDeterminedToExplode() {
        return isDeterminedToExplode;
    }

    public void setDeterminedToExplode(boolean value) {
        isDeterminedToExplode = value;
    }
}

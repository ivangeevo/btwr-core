package org.btwr.core.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import org.btwr.core.block.blocks.CementBlock;
import org.jetbrains.annotations.Nullable;

public class CementBlockEntity extends BlockEntity {
    private int dryTime = 0;
    private int spreadDist = 0;

    public CementBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CEMENT_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt("dryTime", dryTime);
        nbt.putInt("spreadDist", spreadDist);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        if (nbt.contains("dryTime")) {
            dryTime = nbt.getInt("dryTime");
        } else {
            // No dry time saved -> dry immediately
            dryTime = CementBlock.CEMENT_TICKS_TO_DRY;
        }

        if (nbt.contains("spreadDist")) {
            spreadDist = nbt.getInt("spreadDist");
        } else {
            // No spread dist saved -> don't spread further
            spreadDist = CementBlock.MAX_CEMENT_SPREAD_DIST;
        }

        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 0);
        }
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound nbt = new NbtCompound();
        nbt.putShort("d", (short) dryTime);
        nbt.putShort("s", (short) spreadDist);
        return nbt;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public int getDryTime() {
        return dryTime;
    }

    public int getSpreadDist() {
        return spreadDist;
    }

    public void setDryTime(int value) {
        dryTime = value;
        markDirty();
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }

    public void setSpreadDist(int value) {
        spreadDist = value;
        markDirty();
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }
}

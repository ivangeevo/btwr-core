package org.btwr.core.data.saved;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import org.btwr.core.difficulty.BTWRDifficulties;
import org.btwr.core.api.DifficultyRegistry;
import org.btwr.core.difficulty.impl.BTWRDifficulty;

public class BTWRDifficultyData extends PersistentState {
    private Identifier difficultyId;
    private boolean locked;

    public BTWRDifficulty getDifficulty() {
        return DifficultyRegistry.find(difficultyId).orElse(BTWRDifficulties.STANDARD);
    }

    public boolean hasDifficulty() {
        return difficultyId != null;
    }

    public void setDifficulty(BTWRDifficulty difficulty) {
        this.difficultyId = difficulty.id();
        markDirty();
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
        markDirty();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putString("Difficulty", difficultyId.toString());
        nbt.putBoolean("Locked", locked);
        return nbt;
    }

    public static BTWRDifficultyData fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        BTWRDifficultyData state = new BTWRDifficultyData();
        Identifier difficultyId = Identifier.tryParse(nbt.getString("Difficulty"));

        state.difficultyId = difficultyId != null ? difficultyId : BTWRDifficulties.STANDARD.id();
        state.locked = nbt.getBoolean("Locked");

        return state;
    }

    public static final PersistentState.Type<BTWRDifficultyData> TYPE = new PersistentState.Type<>(
            BTWRDifficultyData::new,
            BTWRDifficultyData::fromNbt,
            null
    );

    public static BTWRDifficultyData get(MinecraftServer server) {
        // This could be either the overworld or another dimension.
        ServerWorld world = server.getWorld(ServerWorld.OVERWORLD);

        if (world == null) return new BTWRDifficultyData(); // Return a new instance if the world is null.

        // The first time the following 'getOrCreate' function is called, it creates a new 'Data'
        // instance and stores it inside the 'PersistentStateManager'.
        // Subsequent calls to 'getOrCreate' returns the saved 'Data' NBT on disk to the Codec in our type,
        // using the Codec to decode the NBT into our saved data.
        return world.getPersistentStateManager().getOrCreate(TYPE, "btwr_difficulty");
    }
}
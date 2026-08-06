package org.btwr.core.data.saved;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import org.btwr.core.difficulty.BTWRDifficulties;
import org.btwr.core.difficulty.DifficultyRegistry;
import org.btwr.core.difficulty.impl.BTWRDifficulty;

public class BTWRDifficultyState extends PersistentState {

    private Identifier difficultyId = BTWRDifficulties.STANDARD.id();
    private boolean locked;

    public BTWRDifficulty getDifficulty() {
        return DifficultyRegistry.find(difficultyId)
                .orElse(BTWRDifficulties.STANDARD);
    }

    public boolean isLocked() {
        return locked;
    }

    public void setDifficulty(BTWRDifficulty difficulty) {
        this.difficultyId = difficulty.id();
        markDirty();
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

    public static BTWRDifficultyState fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        BTWRDifficultyState state = new BTWRDifficultyState();

        state.difficultyId = Identifier.tryParse("Difficulty", BTWRDifficulties.STANDARD.id().toString());
        state.locked = nbt.getBoolean("Locked");

        return state;
    }

}
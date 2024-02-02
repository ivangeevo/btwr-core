package btwr.core.entity.interfaces;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;

public interface CreeperEntityAdded {
    default boolean determinedToExplode() {
        return false;
    }

    TrackedData<Boolean> NEUTERED = DataTracker.registerData(CreeperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    default boolean isNeutered() {
        return false;
    }

    default boolean getIsDeterminedToExplode() {
        return false;
    }

    void neuter();

}

package org.btwr.core.world;

import org.btwr.api.api.difficulty.impl.BTWRDifficulty;
import org.btwr.core.data.saved.BTWRDifficultySaveData;

public interface WorldAdded {
    default BTWRDifficulty btwr$difficulty() {
        throw new UnsupportedOperationException();
    }
    default BTWRDifficultySaveData btwr$difficultyData() {
        throw new UnsupportedOperationException();
    }
}

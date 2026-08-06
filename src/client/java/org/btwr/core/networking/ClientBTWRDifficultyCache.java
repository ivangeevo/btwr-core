package org.btwr.core.networking;

import org.btwr.core.difficulty.BTWRDifficulties;
import org.btwr.core.difficulty.impl.BTWRDifficulty;

public final class ClientBTWRDifficultyCache {
    private static BTWRDifficulty difficulty = BTWRDifficulties.STANDARD;

    private ClientBTWRDifficultyCache() {}

    public static BTWRDifficulty get() {
        return difficulty;
    }

    public static void set(BTWRDifficulty value) {
        difficulty = value;
    }
}
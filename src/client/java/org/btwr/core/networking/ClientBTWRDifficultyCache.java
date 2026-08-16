package org.btwr.core.networking;

import org.btwr.api.api.difficulty.impl.BTWRDifficulty;
import org.btwr.core.difficulty.ModDifficulties;

public final class ClientBTWRDifficultyCache {
    private static BTWRDifficulty difficulty = ModDifficulties.STANDARD;
    private static boolean locked = false;

    private ClientBTWRDifficultyCache() {}

    public static BTWRDifficulty get() {
        return difficulty;
    }

    public static void set(BTWRDifficulty value) {
        difficulty = value;
    }

    public static boolean isLocked() {
        return locked;
    }

    public static void setLocked(boolean value) {
        locked = value;
    }
}
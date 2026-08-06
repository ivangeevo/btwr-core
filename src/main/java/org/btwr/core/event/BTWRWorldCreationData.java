package org.btwr.core.event;

import org.btwr.core.difficulty.BTWRDifficulties;
import org.btwr.core.difficulty.impl.BTWRDifficulty;

public class BTWRWorldCreationData {
    private static BTWRDifficulty selected = BTWRDifficulties.STANDARD;

    public static void setSelected(BTWRDifficulty difficulty) {
        selected = difficulty;
    }

    public static BTWRDifficulty getSelected() {
        return selected;
    }

    public static void reset() {
        selected = BTWRDifficulties.STANDARD;
    }
}
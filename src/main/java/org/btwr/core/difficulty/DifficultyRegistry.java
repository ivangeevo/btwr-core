package org.btwr.core.difficulty;

import net.minecraft.util.Identifier;
import org.btwr.core.difficulty.impl.BTWRDifficulty;
import org.btwr.core.difficulty.impl.DifficultyBuilder;

import java.util.*;

public class DifficultyRegistry {

    public static final Map<Identifier, BTWRDifficulty> DIFFICULTIES = new LinkedHashMap<>();

    public static BTWRDifficulty get(Identifier id) {
        return DIFFICULTIES.get(id);
    }

    public static Optional<BTWRDifficulty> find(Identifier id) {
        return Optional.ofNullable(DIFFICULTIES.get(id));
    }

    public static DifficultyBuilder builder(Identifier id) {
        return new DifficultyBuilder(id);
    }

    private static void register(BTWRDifficulty difficulty) {
        if (DIFFICULTIES.putIfAbsent(difficulty.id(), difficulty) != null) {
            throw new IllegalStateException("Duplicate difficulty: " + difficulty.id());
        }
    }

}

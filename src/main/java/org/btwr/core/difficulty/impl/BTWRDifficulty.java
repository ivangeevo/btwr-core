package org.btwr.core.difficulty.impl;

import net.minecraft.util.Identifier;

import java.util.Map;

public final class BTWRDifficulty {
    private final Identifier id;
    private final Map<DifficultyValue<?>, Object> values;

    BTWRDifficulty(Identifier id, Map<DifficultyValue<?>, Object> values) {
        this.id = id;
        this.values = values;
    }

    public Identifier id() {
        return id;
    }
    public Map<DifficultyValue<?>, Object>  values() {
        return values;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(DifficultyValue<T> parameter) {
        return (T) values.get(parameter);
    }
}
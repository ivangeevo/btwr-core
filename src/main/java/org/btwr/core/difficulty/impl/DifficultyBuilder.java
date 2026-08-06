package org.btwr.core.difficulty.impl;

import net.minecraft.util.Identifier;
import org.btwr.core.difficulty.DifficultyRegistry;

import java.util.LinkedHashMap;
import java.util.Map;

public class DifficultyBuilder {
    private final Identifier id;
    private final Map<DifficultyValue<?>, Object> values = new LinkedHashMap<>();

    public DifficultyBuilder(Identifier id) {
        this.id = id;
        DifficultyRegistry.difficulties().forEach(p -> values.put(p, p.values()));
    }

    public DifficultyBuilder inherit(BTWRDifficulty difficulty) {
        values.clear();
        values.putAll(difficulty.values());
        return this;
    }

    public <T> DifficultyBuilder setParam(DifficultyValue<T> parameter, T value) {
        values.put(parameter, value);
        return this;
    }

    public BTWRDifficulty build() {
        return new BTWRDifficulty(id, values);
    }

}

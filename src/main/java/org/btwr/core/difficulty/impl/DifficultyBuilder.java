package org.btwr.core.difficulty.impl;

import net.minecraft.util.Identifier;
import org.btwr.core.difficulty.BTWRDifficulties;

import java.util.LinkedHashMap;
import java.util.Map;

public class DifficultyBuilder {
    private final Identifier id;
    private final Map<DifficultyValue<?>, Object> values = new LinkedHashMap<>();

    public DifficultyBuilder(Identifier id) {
        this.id = id;
        BTWRDifficulties.parametersList().forEach(
                (id1,parameter) -> values.put(parameter, parameter.defaultValue())
        );
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
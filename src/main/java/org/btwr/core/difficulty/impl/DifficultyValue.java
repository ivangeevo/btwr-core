package org.btwr.core.difficulty.impl;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;

 /** A simple utility record that holds all data of a difficulty parameter **/
public record DifficultyValue<T>(Identifier id, Codec<T> codec, T defaultValue) {}
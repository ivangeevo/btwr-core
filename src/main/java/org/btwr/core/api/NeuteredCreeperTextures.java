package org.btwr.core.api;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility registry that allows external mods to provide custom textures
 * for neutered creeper-like entities.
 *
 * <p><strong>Usage:</strong></p>
 *
 * <pre>{@code
 * NeuteredCreeperTextures.register(
 *         MyEntities.MY_CUSTOM_CREEPER,
 *         Identifier.of("mymod", "textures/entity/my_neutered_creeper.png")
 * );
 * }</pre>
 */
public final class NeuteredCreeperTextures {

    /** Internal storage mapping entity types to their neutered texture identifiers. */
    private static final Map<EntityType<?>, Identifier> OVERRIDES = new HashMap<>();

    /**
     * Registers a custom neutered texture for the given entity type.
     * The provided texture will override the default neutered creeper texture
     * whenever a matching entity is rendered.
     *
     * @param type     the creeper-like entity type
     * @param texture  the texture identifier to use when neutered
     */
    public static void register(EntityType<?> type, Identifier texture) {
        OVERRIDES.put(type, texture);
    }

    /**
     * Retrieves the registered neutered texture for the given entity type.
     *
     * @param type  the entity type being rendered
     * @return the registered texture identifier, or {@code null} if none exists
     */
    public static Identifier get(EntityType<?> type) {
        return OVERRIDES.get(type);
    }

}

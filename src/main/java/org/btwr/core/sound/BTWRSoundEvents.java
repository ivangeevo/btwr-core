package org.btwr.core.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.btwr.shared_library.util.utils.IdUtils;

public class BTWRSoundEvents {
    public static final SoundEvent CREEPER_SHORN = register("creeper_shorn");
    public static final SoundEvent CREEPER_SHORN_LAYER = register("creeper_shorn_layer");

    private static SoundEvent register(String name) {
        Identifier id = IdUtils.ofBTWR(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {}
}

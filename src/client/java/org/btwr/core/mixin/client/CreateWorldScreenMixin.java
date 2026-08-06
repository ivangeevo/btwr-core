package org.btwr.core.mixin.client;

import com.mojang.serialization.Lifecycle;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.registry.CombinedDynamicRegistries;
import net.minecraft.registry.ServerDynamicRegistryType;
import net.minecraft.world.level.LevelProperties;
import org.btwr.core.BTWRMod;
import org.btwr.core.difficulty.impl.BTWRDifficulty;
import org.btwr.core.event.BTWRWorldCreationData;
import org.btwr.core.networking.ClientBTWRDifficultyCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {

    @Inject(method = "startServer", at = @At("TAIL"))
    private void btwr$setInitialDifficulty(
            LevelProperties.SpecialProperty specialProperty,
            CombinedDynamicRegistries<ServerDynamicRegistryType> combinedDynamicRegistries,
            Lifecycle lifecycle,
            CallbackInfo ci
    ) {
        BTWRMod.LOGGER.info(
                "CREATION SELECTED: {}",
                BTWRWorldCreationData.getSelected().id()
        );

        BTWRDifficulty difficulty = BTWRWorldCreationData.getSelected();

        if (difficulty != null) {
            ClientBTWRDifficultyCache.set(difficulty);
        }
    }

}
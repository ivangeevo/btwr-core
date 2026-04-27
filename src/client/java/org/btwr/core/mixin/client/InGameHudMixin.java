package org.btwr.core.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.btwr.core.util.EnderSpectaclesHelper;
import org.btwr.shared_library.util.utils.IdUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Unique
    private static final Identifier SPECTACLES_BLUR_TEXTURE = IdUtils.ofBTWR("textures/gui/spectacles_blur.png");

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderMiscOverlays", at = @At("TAIL"))
    private void renderEnderSpectaclesOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (client.player == null) return;
        if (client.options.getPerspective().isFirstPerson() && EnderSpectaclesHelper.isWearingEnderSpectacles(client.player)) {
            renderSpectaclesBlur(context);
        }
    }

    @Unique
    private void renderSpectaclesBlur(DrawContext context) {
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        context.drawTexture(SPECTACLES_BLUR_TEXTURE, 0, 0, 0, 0 , screenWidth, screenHeight, screenWidth, screenHeight);

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}

package org.btwr.core.mixin.client;

import com.terraformersmc.modmenu.config.ModMenuConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/** WARNING: Causes problems when launching the game with only BTWR: SL, even when nothing is added. **/
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {

    // testing with custom text count
    //@Inject(method = "render", at = @At("TAIL"))
    private void modifyModCountRendering(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // Calculate the filtered mod count
        int modCount = (int) FabricLoader.getInstance().getAllMods().stream()
                .filter(mod -> !mod.getMetadata().getId().startsWith("fabric")) // Exclude Fabric API
                .count();

        // Replace the default "mods loaded" text
        String customModText = modCount + " Mods Loaded";
        context.drawText(
                MinecraftClient.getInstance().textRenderer,
                customModText,
                10, // Adjust X position as needed
                10, // Adjust Y position as needed
                0xFFFFFF, // White color
                false
        );
    }

    /** Filters the default fabric mods(packages) from displaying in the mod menu count text at the left bottom corner **/
    //@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)I", ordinal = 0))
    private String onRender(String string) {
        if (ModMenuConfig.MODIFY_TITLE_SCREEN.getValue() && ModMenuConfig.MOD_COUNT_LOCATION.getValue()
                .isOnTitleScreen()) {

            // Get the list of mods excluding the default Fabric mods
            List<String> filteredMods = FabricLoader.getInstance().getAllMods().stream()
                    .filter(mod -> !mod.getMetadata().getId().startsWith("fabric") && !mod.getMetadata().getId().equals("fabricloader"))
                    .map(mod -> mod.getMetadata().getId())
                    .toList();

            // Get the filtered mod count
            String count = String.valueOf(filteredMods.size());

            String specificKey = "modmenu.mods." + count;
            String replacementKey = I18n.hasTranslation(specificKey) ? specificKey : "modmenu.mods.n";
            if (ModMenuConfig.EASTER_EGGS.getValue() && I18n.hasTranslation(specificKey + ".secret")) {
                replacementKey = specificKey + ".secret";
            }

            return string.replace(I18n.translate(I18n.translate("menu.modded")), I18n.translate(replacementKey, count));
        }
        return string;
    }

}
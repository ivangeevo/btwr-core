package org.btwr.core.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import org.btwr.core.api.DifficultyRegistry;
import org.btwr.core.difficulty.impl.BTWRDifficulty;
import org.btwr.core.event.BTWRWorldCreationData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.screen.world.CreateWorldScreen$GameTab")
public abstract class GameTabMixin extends GridScreenTab {

    protected GameTabMixin(Text title) {
        super(title);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/GridWidget$Adder;add(Lnet/minecraft/client/gui/widget/Widget;Lnet/minecraft/client/gui/widget/Positioner;)Lnet/minecraft/client/gui/widget/Widget;", ordinal = 2))
    private <T extends Widget> T hideVanillaDifficultyButton(GridWidget.Adder instance, T widget, Positioner positioner) {
        return widget;
    }

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/widget/CyclingButtonWidget$Builder;build(IIIILnet/minecraft/text/Text;Lnet/minecraft/client/gui/widget/CyclingButtonWidget$UpdateCallback;)Lnet/minecraft/client/gui/widget/CyclingButtonWidget;",
                    ordinal = 1,      // 0 = game mode's build(), 1 = difficulty's build()
                    shift = At.Shift.AFTER
            )
    )
    private void addDifficultyOption(CreateWorldScreen screen, CallbackInfo ci, @Local GridWidget.Adder adder, @Local Positioner positioner) {
        WorldCreator worldCreator = screen.getWorldCreator();

        CyclingButtonWidget<BTWRDifficulty> button = adder.add(
                CyclingButtonWidget.builder(BTWRDifficulty::getDisplayName)
                        .values(DifficultyRegistry.getAll().toArray(new BTWRDifficulty[0]))
                        .tooltip(value -> Tooltip.of(value.getTooltip()))
                        .build(
                                0, 0, 210, 20,
                                Text.translatable("difficulty.btwr"),
                                (btn, value) -> BTWRWorldCreationData.setSelected(value)
                        ),
                positioner
        );

        worldCreator.addListener(creator -> {
            button.setValue(BTWRWorldCreationData.getSelected());
        });
    }

}
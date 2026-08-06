package org.btwr.core.mixin.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OnlineOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.GameOptions;
import net.minecraft.network.packet.c2s.play.UpdateDifficultyLockC2SPacket;
import net.minecraft.text.Text;
import org.btwr.core.BTWRMod;
import org.btwr.core.api.DifficultyRegistry;
import org.btwr.core.difficulty.impl.BTWRDifficulty;
import org.btwr.core.networking.ClientBTWRDifficultyCache;
import org.btwr.core.networking.UpdateBTWRDifficultyC2SPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {

    @Shadow @Final private GameOptions settings;

    @Unique
    private @Nullable CyclingButtonWidget<BTWRDifficulty> btwrDifficultyButton;

    @Unique
    private @Nullable LockButtonWidget btwrLockDifficultyButton;

    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "createTopRightButton", at = @At("HEAD"), cancellable = true)
    private void replaceDifficultyWidget(CallbackInfoReturnable<Widget> cir) {
        cir.setReturnValue(createTopRightButton());
    }

    @Unique
    private Widget createTopRightButton() {
        MinecraftClient client = this.client;

        if (client == null) {
            BTWRMod.LOGGER.error("[{}] Can't create difficulty widget: Client is null", BTWRMod.MOD_NAME);
            return null;
        }

        if (client.world != null && client.isIntegratedServerRunning()) {
            this.btwrDifficultyButton = createDifficultyButtonWidget(0, 0, "options.difficulty", this.client);
            if (!client.world.getLevelProperties().isHardcore()) {
                this.btwrLockDifficultyButton = new LockButtonWidget(
                        0, 0,
                        button -> {
                            assert this.client.world != null;
                            client
                                    .setScreen(
                                            new ConfirmScreen(
                                                    this::lockDifficulty,
                                                    Text.translatable("difficulty.lock.title"),
                                                    Text.translatable("difficulty.lock.question", ClientBTWRDifficultyCache.get().getTooltip())
                                            )
                                    );
                        });
                this.btwrDifficultyButton.setWidth(this.btwrDifficultyButton.getWidth() - this.btwrLockDifficultyButton.getWidth());
                this.btwrLockDifficultyButton.setLocked(client.world.getLevelProperties().isDifficultyLocked());
                this.btwrLockDifficultyButton.active = !this.btwrLockDifficultyButton.isLocked();
                this.btwrDifficultyButton.active = !this.btwrLockDifficultyButton.isLocked();
                AxisGridWidget axisGridWidget = new AxisGridWidget(150, 0, AxisGridWidget.DisplayAxis.HORIZONTAL);
                axisGridWidget.add(this.btwrDifficultyButton);
                axisGridWidget.add(this.btwrLockDifficultyButton);
                return axisGridWidget;
            } else {
                assert this.btwrDifficultyButton != null;
                this.btwrDifficultyButton.active = false;
                return this.btwrDifficultyButton;
            }
        } else {
            return ButtonWidget.builder(Text.translatable("options.online"), button -> client.setScreen(new OnlineOptionsScreen(this, this.settings)))
                    .dimensions(this.width / 2 + 5, this.height / 6 - 12 + 24, 150, 20)
                    .build();
        }
    }

    @Unique
    private static CyclingButtonWidget<BTWRDifficulty> createDifficultyButtonWidget(int x, int y, String translationKey, MinecraftClient client) {
        return CyclingButtonWidget.builder(BTWRDifficulty::getDisplayName)
                .values(DifficultyRegistry.getAll())
                .initially(ClientBTWRDifficultyCache.get())
                .build(
                        x, y, 150, 20,
                        Text.translatable(translationKey),
                        (button, difficulty) -> ClientPlayNetworking.send(
                                new UpdateBTWRDifficultyC2SPacket(difficulty.id())
                        )
                );
    }

    @Unique
    private void lockDifficulty(boolean difficultyLocked) {
        assert this.client != null;
        assert this.client.getNetworkHandler() != null;

        this.client.setScreen(this);
        if (difficultyLocked && this.client.world != null && this.btwrLockDifficultyButton != null && this.btwrDifficultyButton != null) {
            this.client.getNetworkHandler().sendPacket(new UpdateDifficultyLockC2SPacket(true));
            this.btwrLockDifficultyButton.setLocked(true);
            this.btwrLockDifficultyButton.active = false;
            this.btwrDifficultyButton.active = false;
        }
    }

}
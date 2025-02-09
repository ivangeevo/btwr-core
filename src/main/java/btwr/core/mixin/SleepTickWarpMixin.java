package btwr.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.ServerTickManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.SleepManager;
import net.minecraft.text.Text;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.GameRules;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.tick.TickManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class SleepTickWarpMixin extends World {
    @Final
    @Shadow
    private SleepManager sleepManager;

    @Final
    @Shadow
    List<ServerPlayerEntity> players;

    @Invoker("wakeSleepingPlayers")
    abstract void wakeSleepingPlayers();

    @Invoker("resetWeather")
    abstract void resetWeather();

    protected SleepTickWarpMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess, maxChainedNeighborUpdates);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/SleepManager;canSkipNight(I)Z"))
    public boolean canSkipNightRedirect(SleepManager instance, int percentage) {
        // Turn off normal sleeping
        return false;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getInt(Lnet/minecraft/world/GameRules$Key;)I"))
    public void skipNightTickWarp(BooleanSupplier shouldKeepTicking, CallbackInfo ci, @Local TickManager tickManager) {
        int i = this.getGameRules().getInt(GameRules.PLAYERS_SLEEPING_PERCENTAGE);

        if (!(tickManager instanceof ServerTickManager serverTickManager)) {
            return;
        }

        if (this.sleepManager.canSkipNight(i) && this.sleepManager.canResetTime(i, this.players)) {
            long time = this.properties.getTimeOfDay();
            long sprintTargetTicks = 24000L - ((time + 24000L) % 24000L);
            if (!this.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
                return;
            }

            if (time % 24000L == 0) {
                serverTickManager.stopSprinting();
                this.wakeSleepingPlayers();
                if (this.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE) && this.isRaining()) {
                    this.resetWeather();
                }
                this.players.forEach(player -> player.sendMessage(Text.of("Good morning!")));
                return;
            }

            if (!serverTickManager.isSprinting()) {
                serverTickManager.startSprint((int) sprintTargetTicks);
                this.players.forEach(player -> player.sendMessage(Text.of("Skipping the night by sprinting. Wheeeeeee!")));
            }
        }
    }

}

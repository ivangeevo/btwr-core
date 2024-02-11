package btwr.core.mixin.entity;

import btwr.core.entity.interfaces.PlayerEntityAdded;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements PlayerEntityAdded {


    private int timesCraftedThisTick = 0;

    @Override
    public int timesCraftedThisTick() {
        return timesCraftedThisTick;
    }

    @Override
    public void setTimesCraftedThisTick(int value) {
        timesCraftedThisTick = value;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void injectedTick(CallbackInfo ci) {
        setTimesCraftedThisTick(0);
    }
}

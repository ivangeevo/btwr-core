package org.btwr.core.mixin.entity;

import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.item.Item;
import org.btwr.core.item.BTWR_Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin {
    @ModifyArg(method = "isPlayerStaring", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private Item requireEnderSpecs(Item item) {
        return BTWR_Items.ENDER_SPECTACLES;
    }
}
package btwr.core.mixin.item;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ToolMaterials.class)
public abstract class ToolMaterialsMixin {
    @Mutable @Shadow @Final private int miningLevel;


    // Modify the durability values for all tool materials.
    @Inject(method = "getDurability", at = @At("HEAD"), cancellable = true)
    private void customMaterialDurability(CallbackInfoReturnable<Integer> cir) {

        if (this.miningLevel == MiningLevels.WOOD) {
            cir.setReturnValue(10);
        } else if (this.miningLevel == MiningLevels.STONE) {
            cir.setReturnValue(50);
        } else if (this.miningLevel == MiningLevels.IRON) {
            cir.setReturnValue(500);
        } else if (this.miningLevel == MiningLevels.DIAMOND) {
            cir.setReturnValue(1800);
        } else if (this.miningLevel == MiningLevels.NETHERITE) {
            cir.setReturnValue(1800);
        }
    }

    @Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
    private void customMaterialSpeed(CallbackInfoReturnable<Float> cir) {

        if (this.miningLevel == MiningLevels.WOOD) {
            cir.setReturnValue(1.0f);
        } else if (this.miningLevel == MiningLevels.STONE) {
            cir.setReturnValue(1.1f);
        } else if (this.miningLevel == MiningLevels.IRON) {
            cir.setReturnValue(6f);
        } else if (this.miningLevel == MiningLevels.DIAMOND) {
            cir.setReturnValue(8f);
        } else if (this.miningLevel == MiningLevels.NETHERITE) {
            cir.setReturnValue(9f);
        }
    }

}

package btwr.core.mixin.item;

import btwr.core.BTWRMod;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = ToolMaterials.class)
public abstract class ToolMaterialsMixin {
    @Shadow @Final private int miningLevel;


    // Modify the durability values for all tool materials.
    @Inject(method = "getDurability", at = @At("HEAD"), cancellable = true)
    private void customMaterialDurability(CallbackInfoReturnable<Integer> cir) {

        if (!BTWRMod.getInstance().settings.isHCMaterialDurabilityEnabled()) {
            return;
        }

        switch (this.miningLevel) {
            case MiningLevels.WOOD:
                cir.setReturnValue(10);
                break;
            case MiningLevels.STONE:
                cir.setReturnValue(50);
                break;
            case MiningLevels.IRON:
                cir.setReturnValue(500);
                break;
            case MiningLevels.DIAMOND:
            case MiningLevels.NETHERITE:
                cir.setReturnValue(1800);
                break;
        }

    }

    //@Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
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

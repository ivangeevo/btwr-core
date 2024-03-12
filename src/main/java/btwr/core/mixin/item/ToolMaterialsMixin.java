package btwr.core.mixin.item;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ToolMaterials.class)
public abstract class ToolMaterialsMixin
{
    @Shadow @Final private int miningLevel;

    // Modify the durability values for all tool materials.
    @Inject(method = "getDurability", at = @At("HEAD"), cancellable = true)
    private void customMaterialDurability(CallbackInfoReturnable<Integer> cir)
    {
        //if (BTWRSettingsGUI.getConfigValue(BTWRSettings.HARDCORE_MATERIAL_DURABILITY_KEY)) {
            applyCustomDurability(cir);
        //}
    }

    @Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
    private void customMaterialSpeed(CallbackInfoReturnable<Float> cir)
    {
        //if (BTWRSettingsGUI.getConfigValue(BTWRSettings.HARDCORE_MATERIAL_SPEED_KEY)) {
            applyCustomSpeed(cir);
        //}
    }

    @Unique
    private void applyCustomDurability(CallbackInfoReturnable<Integer> cir) {
        switch (this.miningLevel)
        {
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

    @Unique
    private void applyCustomSpeed(CallbackInfoReturnable<Float> cir)
    {
        switch (this.miningLevel)
        {
            case MiningLevels.WOOD:
                cir.setReturnValue(1.0f);
                break;
            case MiningLevels.STONE:
                cir.setReturnValue(1.1f);
                break;
            case MiningLevels.IRON:
                cir.setReturnValue(6f);
                break;
            case MiningLevels.DIAMOND:
                cir.setReturnValue(8f);
                break;
            case MiningLevels.NETHERITE:
                cir.setReturnValue(9f);
                break;
        }
    }
}

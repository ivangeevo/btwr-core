package btwr.core.mixin.item;

import btwr.core.item.ItemAndToolMixinManager;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ShearsItem.class)
public abstract class ShearsItemMixin extends Item
{
    public ShearsItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "createToolComponent", at = @At("RETURN"), cancellable = true)
    private static void onCreateToolComponent(CallbackInfoReturnable<ToolComponent> cir)
    {
        List<ToolComponent.Rule> newList = ItemAndToolMixinManager.MODIFIED_SHEARS_COMPONENT_LIST;
        cir.setReturnValue(new ToolComponent(newList, 1.0f, 1));
    }

}

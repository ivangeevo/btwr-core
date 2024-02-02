package btwr.core.mixin.item;

import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShearsItem.class)
public abstract class ShearsItemMixin extends Item {


    public ShearsItemMixin(Settings settings) {
        super(settings);
    }
}

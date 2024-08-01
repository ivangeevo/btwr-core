package btwr.core.mixin.item;

import btwr.core.block.BTWR_Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.registry.tag.BlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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

    // Adds remainder logic so the item doesn't get consumed on crafting.
    @Override
    public ItemStack getRecipeRemainder(ItemStack stack)
    {
        if (stack.getDamage() < stack.getMaxDamage() - 1)
        {
            ItemStack moreDamaged = stack.copy();
            moreDamaged.setDamage(stack.getDamage() + 1);
            return moreDamaged;
        }

        return ItemStack.EMPTY;
    }

    @Inject(method = "createToolComponent", at = @At("RETURN"), cancellable = true)
    private static void onCreateToolComponent(CallbackInfoReturnable<ToolComponent> cir)
    {
        cir.setReturnValue(newComponent());
    }

    @Unique
    private static ToolComponent newComponent()
    {
        return new ToolComponent(
                List.of(
                ToolComponent.Rule.ofAlwaysDropping(List.of(Blocks.COBWEB), 15.0f),
                ToolComponent.Rule.of(BlockTags.LEAVES, 15.0f),
                ToolComponent.Rule.of(BlockTags.WOOL, 5.0f),
                ToolComponent.Rule.of(List.of(Blocks.VINE, Blocks.GLOW_LICHEN), 2.0f),
                ToolComponent.Rule.of(List.of(BTWR_Blocks.CROP_HEMP),2.0f)
                ),
                1.0f, 1);
    }

}

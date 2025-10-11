package btwr.core.item.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;

public class StackableStewItem extends Item {

    public StackableStewItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player && !world.isClient) {
            ItemStack bowl = new ItemStack(Items.BOWL);

            // Try to add the bowl to inventory first
            boolean added = player.getInventory().insertStack(bowl);

            // If inventory is full, drop the bowl
            if (!added) {
                ItemScatterer.spawn(world, player.getX(), player.getY() + 1, player.getZ(), bowl);
            }
        }

        return super.finishUsing(stack, world, user);
    }




}

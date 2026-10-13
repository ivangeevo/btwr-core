package org.btwr.core.item.items;

import com.bwt.blocks.BwtBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.btwr.core.block.ModBlocks;
import org.btwr.core.entity.CementBlockEntity;

public class CementBucketItem extends Item {
    public CementBucketItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos().offset(context.getSide());
        BlockState existing = world.getBlockState(pos);

        if (existing.isAir() || !existing.isSolidBlock(world, pos)) {
            if (!world.isClient) {
                world.playSound(null,
                        pos,
                        SoundEvents.ENTITY_GHAST_WARN,
                        SoundCategory.BLOCKS,
                        0.5f,
                        2.6f + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.8f
                );

                world.setBlockState(pos, ModBlocks.CEMENT.getDefaultState());
                // Source block: spreadDist = 0
                setCementSource(world, pos);

                PlayerEntity player = context.getPlayer();

                // Give back empty bucket
                if (player != null && !player.isCreative())  {
                    player.setStackInHand(context.getHand(), new ItemStack(Items.BUCKET));
                }
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    private void setCementSource(World world, BlockPos pos) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof CementBlockEntity cbe) {
            cbe.setSpreadDist(0); // 0 = source
            cbe.setDryTime(0);
        }
    }
}

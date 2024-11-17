// FCMOD

package btwr.core.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static net.minecraft.block.Block.dropStack;

public class ItemUtils
{

    static public void ejectStackWithRandomOffset(World world, BlockPos pos, ItemStack stack) {
        float xOffset = world.getRandom().nextFloat() * 0.7F + 0.15F;
        float yOffset = world.getRandom().nextFloat() * 0.2F + 0.1F;
        float zOffset = world.getRandom().nextFloat() * 0.7F + 0.15F;

        ejectStackWithRandomVelocity(world, (float) pos.getX() + xOffset, (float) pos.getY() + yOffset, (float) pos.getZ() + zOffset, stack);
    }

    static public void ejectSingleItemWithRandomOffset(World world, BlockPos pos, int iShiftedItemIndex) {
        Item item = Registries.ITEM.get(iShiftedItemIndex);
        ItemConvertible itemConvertible = item.asItem();

        ItemStack itemStack = new ItemStack(itemConvertible, 1);

        ejectStackWithRandomOffset(world, pos, itemStack);
    }


    public static void ejectStackWithRandomVelocity(World world, double x, double y, double z, ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(world, x, y, z, stack);

        float velocityFactor = 0.05F;

        itemEntity.setVelocity(
                world.random.nextGaussian() * velocityFactor,
                world.random.nextGaussian() * velocityFactor + 0.2F,
                world.random.nextGaussian() * velocityFactor
        );

        itemEntity.setPickupDelay(10);

        world.spawnEntity(itemEntity);
    }

    static public void ejectSingleItemWithRandomVelocity(World world, float xPos, float yPos, float zPos, int iShiftedItemIndex, int iDamage) {
        Item item = Registries.ITEM.get(iShiftedItemIndex);

        ItemConvertible itemConvertible = item.asItem();

        ItemStack itemStack = new ItemStack(itemConvertible, 1);

        ItemUtils.ejectStackWithRandomVelocity(world, xPos, yPos, zPos, itemStack);
    }

    static public void dropStackAsIfBlockHarvested(World world, BlockPos pos, ItemStack stack) {
        if (!world.isClient && !stack.isEmpty() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            double d = 0.5D;
            double d1 = world.random.nextFloat() * 0.8F + 0.1F;
            double d2 = world.random.nextFloat() * 0.8F + 0.1F;
            double d3 = world.random.nextFloat() * 0.8F + 0.1F;

            ItemEntity entityitem = new ItemEntity(world, pos.getX() + d, pos.getY() + d1, pos.getZ() + d2, stack);
            entityitem.setPos(
                    world.random.nextGaussian() * 0.05D,
                    world.random.nextGaussian() * 0.05D + 0.2D,
                    world.random.nextGaussian() * 0.05D
            );

            world.spawnEntity(entityitem);
        }
    }

    static public void dropSingleItemAsIfBlockHarvested(World world, BlockPos pos, int iShiftedItemIndex, int iDamage) {
        Item item = Registries.ITEM.get(iShiftedItemIndex);

        ItemConvertible itemConvertible = item.asItem();

        ItemStack itemStack = new ItemStack(itemConvertible, 1);

        ItemUtils.dropStackAsIfBlockHarvested(world, pos, itemStack);
    }

    // TODO: Fix stacks dropping in random places sometimes when broken.
    static public void ejectStackFromBlockTowardsFacing(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack, Direction direction) {


        for (ItemStack droppedItems : Block.getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, player, stack))
        {
            dropInDirection(world, pos, direction, droppedItems);
        }

        state.onStacksDropped((ServerWorld) world, pos, stack, true);
    }

    public static void dropInDirection(World world, BlockPos pos, Direction direction, ItemStack stack) {
        int i = direction.getOffsetX(); // X offset based on direction
        int j = direction.getOffsetY(); // Y offset based on direction
        int k = direction.getOffsetZ(); // Z offset based on direction

        double d = (double) EntityType.ITEM.getWidth() / 2.0;
        double e = (double)EntityType.ITEM.getHeight() / 2.0;

        // Calculate exact drop position based on the direction without randomness
        double f = (double)pos.getX() + 0.5 + i * (0.5 + d); // X position
        double g = (double)pos.getY() + 0.5 + j * (0.5 + e) - e; // Y position, adjust for height
        double h = (double)pos.getZ() + 0.5 + k * (0.5 + d); // Z position

        // Remove the random motion and set velocity to zero
        double l = i * 0.1; // X velocity
        // TODO: Currently testing for dropping them without the upward motion to see if this
        //  fixes the weird behaviour for certain drops in direction.
        double m = j * 0.1 /** + 0.1 **/; // Y velocity (a slight upward motion to prevent items from falling instantly)
        double n = k * 0.1; // Z velocity

        // Spawn the item with the fixed position and no randomness
        dropStack(world, () -> new ItemEntity(world, f, g, h, stack, l, m, n), stack);
    }

    private static void dropStack(World world, Supplier<ItemEntity> itemEntitySupplier, ItemStack stack) {
        if (!world.isClient && !stack.isEmpty() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            ItemEntity itemEntity = itemEntitySupplier.get();
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
    }

}

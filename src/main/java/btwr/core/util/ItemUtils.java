// FCMOD

package btwr.core.util;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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

        Direction miningDirection = VectorUtils.getMiningDirection(player, world, pos);

        for (ItemStack droppedItems : Block.getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, player, stack))
        {
            dropStack(world, pos, direction, droppedItems);
        }

        state.onStacksDropped((ServerWorld) world, pos, stack, true);
    }


    private static void spawnItemEntity(World world, Supplier<ItemEntity> itemEntitySupplier, Direction direction) {
        ItemEntity entity = itemEntitySupplier.get();
        Vec3d ejectVel;

        if (direction.getAxis().isVertical()) {
            ejectVel = new Vec3d(
                    world.getRandom().nextDouble() * 0.1D - 0.05D,
                    0.2D,
                    world.getRandom().nextDouble() * 0.1D - 0.05D
            );
        } else {
            ejectVel = new Vec3d(
                    world.getRandom().nextDouble() * 0.1D - 0.05D,
                    world.getRandom().nextDouble() * 0.1D - 0.05D,
                    world.getRandom().nextDouble() * 0.1D - 0.05D
            );
        }

        ejectVel = ejectVel.normalize().multiply(0.2D);

        entity.setVelocity(ejectVel.x, ejectVel.y, ejectVel.z);
        entity.setToDefaultPickupDelay();
        world.spawnEntity(entity);
    }


    /**
     * Yaws the vector around the origin of the J axis. Assumes that the initial facing is along the negative K axis (facing 2).
     */
    public static void rotateAsVectorAroundJToFacing(Vec3d vector, int iFacing) {
        if (iFacing > 2) {
            if (iFacing == 5) // i + 1
            {
                double tempZ = vector.x;

                vector = new Vec3d(-vector.z, vector.y, tempZ);
            } else if (iFacing == 4) // i - 1
            {
                double tempZ = -vector.x;

                vector = new Vec3d(vector.z, vector.y, tempZ);
            } else // if ( iFacing == 3 ) // k + 1
            {
                vector = new Vec3d(-vector.x, -vector.y, -vector.z);
            }
        }
    }



}

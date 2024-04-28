/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package btwr.core.block.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public abstract class ModCropBlock
        extends CropBlock
        implements Fertilizable {


    public ModCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), 0));
    }


    protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        boolean bl2;
        float f = 1.0f;
        BlockPos blockPos = pos.down();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float g = 0.0f;
                BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
                if (blockState.isOf(Blocks.FARMLAND)) {
                    g = 1.0f;
                    if (blockState.get(FarmlandBlock.MOISTURE) > 0) {
                        g = 3.0f;
                    }
                }
                if (i != 0 || j != 0) {
                    g /= 4.0f;
                }
                f += g;
            }
        }
        BlockPos blockPos2 = pos.north();
        BlockPos blockPos3 = pos.south();
        BlockPos blockPos4 = pos.west();
        BlockPos blockPos5 = pos.east();
        boolean bl = world.getBlockState(blockPos4).isOf(block) || world.getBlockState(blockPos5).isOf(block);
        boolean bl3 = bl2 = world.getBlockState(blockPos2).isOf(block) || world.getBlockState(blockPos3).isOf(block);
        if (bl && bl2) {
            f /= 2.0f;
        } else {
            boolean bl32;
            boolean bl4 = bl32 = world.getBlockState(blockPos4.north()).isOf(block) || world.getBlockState(blockPos5.north()).isOf(block) || world.getBlockState(blockPos5.south()).isOf(block) || world.getBlockState(blockPos4.south()).isOf(block);
            if (bl32) {
                f /= 2.0f;
            }
        }
        return f;
    }

    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return  floor.isOf(Blocks.FARMLAND);
    }


    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        boolean isFarmland = world.getBlockState(pos.down()).isOf(Blocks.FARMLAND);
        boolean isLitEnough = (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos));

        return isLitEnough && isFarmland;
    }


    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            world.breakBlock(pos, true, entity);
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    protected ItemConvertible getSeedsItem() {
        return null;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.getSeedsItem());
    }


    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return !isMature(state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.applyGrowth(world, pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

}


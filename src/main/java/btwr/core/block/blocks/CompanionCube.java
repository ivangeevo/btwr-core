/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package btwr.core.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CompanionCube extends FacingBlock
{
    public CompanionCube(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return null;
    }

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;


    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        super.onBreak(world, pos, state, player);
        world.playSound(player, pos, SoundEvents.ENTITY_WOLF_DEATH, SoundCategory.BLOCKS, 1.0F, 1.0F);

        return state;
    }


    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack)
    {
        if (player.getWorld().isClient)
        {
            world.addParticle(ParticleTypes.HEART,pos.getX(), pos.getY() + 0.6f, pos.getZ(),0f,0f,0f);
        }
        super.afterBreak(world, player, pos, state, blockEntity, stack);
    }


}


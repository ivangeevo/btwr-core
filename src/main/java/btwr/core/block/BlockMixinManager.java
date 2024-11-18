package btwr.core.block;

import btwr.core.tag.BTWRConventionalTags;
import btwr.core.util.ItemUtils;
import btwr.core.util.MiscUtils;
import btwr.core.util.VectorUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.state.property.Properties.WATERLOGGED;

public class BlockMixinManager
{
    private static final BlockMixinManager instance = new BlockMixinManager();

    // Private constructor to prevent instantiation
    private BlockMixinManager() {}

    public static BlockMixinManager getInstance()
    {
        return instance;
    }

    public void dropStacksInDirectionOrElse(BlockState state, World world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool)
    {


        if (world instanceof ServerWorld)
        {
            // the opposite direction
            Direction lookDirection = VectorUtils.getMiningDirection((PlayerEntity)entity, world, pos);

            if ((state.isIn(BTWRConventionalTags.Blocks.VANILLA_CONVERTING_BLOCKS) || state.isIn(BTWRConventionalTags.Blocks.MODDED_CONVERTING_BLOCKS))
                    && !BlockMixinManager.getInstance().isFullyBreakingTool(tool))
            {
                ItemUtils.ejectStackFromBlockTowardsFacing(world, (PlayerEntity) entity, pos, state, blockEntity, tool, lookDirection.getOpposite());
            }
            else
            {
                Block.getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, entity, tool).forEach(stack -> Block.dropStack(world, pos, stack));
                state.onStacksDropped((ServerWorld)world, pos, tool, true);
            }



        }
    }

    public boolean isFullyBreakingTool(ItemStack tool)
    {
        return tool.isOf(Items.STONE_AXE)
                || tool.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES)
                || tool.isIn(BTWRConventionalTags.Items.MODERN_AXES)

                || tool.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                || tool.isIn(BTWRConventionalTags.Items.ADVANCED_AXES);

    }

    @NotNull
    public Direction getBlockHitSide()
    {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        ClientPlayerEntity player = minecraftClient.player;

        // Ensure the player and world are not null
        if (player != null && minecraftClient.world != null)
        {
            // Get the player's crosshair position
            HitResult hitResult = minecraftClient.crosshairTarget;

            // Check if the crosshair is pointing at a block
            if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK)
            {
                // Get the side from the block state
                return ((BlockHitResult) hitResult).getSide();
            }
        }

        // Return a default direction if the conditions are not met
        return Direction.NORTH;
    }

    public void handleCustomWaterlogging(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        if ( state.contains(WATERLOGGED) && state.get(WATERLOGGED) && !world.isClient )
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            if (player.getAbilities().creativeMode)
            {
                MiscUtils.placeNonPersistentWater(world, pos);
            }
            else
            {
                MiscUtils.placeNonPersistentWater(world, pos);
            }
        }
    }


}

package org.btwr.core.mixin.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.btwr.core.config.BTWRModSettings;
import org.btwr.shared_library.tag.BTWRConventionalTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// TODO: Rework this modification. It can probably be done better
@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlock$AbstractBlockStateMixin {
    // Predicate that checks if mobs can spawn on this block (excluding wooden blocks)
    @Unique
    private static final AbstractBlock.TypedContextPredicate<EntityType<?>> newSpawningPredicate = (state, world, pos, type) ->
            !isWoodenBlock(state) && isSolidOnTop(state, world, pos) && state.getLuminance() < 14;
    @Unique
    private static final AbstractBlock.TypedContextPredicate<EntityType<?>> OGallowsSpawningPredicate = (state, world, pos, type) ->
            state.isSideSolidFullSquare(world, pos, Direction.UP) && state.getLuminance() < 14;

    @Inject(method = "allowsSpawning", at = @At("HEAD"), cancellable = true)
    private void modifyAllowsSpawning(BlockView world, BlockPos pos, EntityType<?> type, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = (BlockState) (Object) this;

        // Check the setting to determine which predicate to use
        boolean useCustomLogic = BTWRModSettings.spawnMobsOnWood.get();

        if (useCustomLogic) {
            // Use the original spawning predicate
            cir.setReturnValue(OGallowsSpawningPredicate.test(state, world, pos, type));
        }
        else {
            // Use the custom spawning predicate
            cir.setReturnValue(newSpawningPredicate.test(state, world, pos, type));
        }
    }

    // Check if the block is solid on the top side, including bottom slabs
    @Unique
    private static boolean isSolidOnTop(BlockState state, BlockView world, BlockPos pos) {
        // Special case for bottom slabs
        if (state.getBlock() instanceof SlabBlock) {
            SlabType slabType = state.get(SlabBlock.TYPE);
            if (slabType == SlabType.BOTTOM) {
                return true; // Allow spawning on bottom slabs
            }
        }
        // Regular solid block check
        return state.isSideSolidFullSquare(world, pos, Direction.UP);
    }

    //TODO: This could probably use a better check than this
    @Unique
    private static boolean isWoodenBlock(BlockState state) {
        return state.isIn(BlockTags.LOGS)
                || state.isIn(BlockTags.OVERWORLD_NATURAL_LOGS)
                || state.isIn(BlockTags.PLANKS)
                || state.isIn(BlockTags.WOODEN_SLABS)
                || state.isIn(BTWRConventionalTags.Blocks.WOODEN_MISC_BLOCKS);
    }
}
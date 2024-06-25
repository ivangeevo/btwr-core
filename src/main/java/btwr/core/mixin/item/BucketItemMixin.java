package btwr.core.mixin.item;

import btwr.core.util.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidFillable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends Item implements FluidModificationItem {
    @Shadow
    @Final
    private Fluid fluid;

    @Shadow protected abstract void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos);

    public BucketItemMixin(Settings settings) {
        super(settings);
    }


    @Inject(method = "placeFluid", at = @At("HEAD"), cancellable = true)
    private void modifyPlaceFluid(PlayerEntity player, World world, BlockPos pos, BlockHitResult hitResult, CallbackInfoReturnable<Boolean> cir) {

        boolean bl2;
        if (!(this.fluid instanceof FlowableFluid))
        {
            cir.setReturnValue(false);
        }
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        boolean bl = blockState.canBucketPlace(this.fluid);
        boolean bl3 = bl2 = blockState.isAir() || bl || block instanceof FluidFillable && ((FluidFillable) block).canFillWithFluid(world, pos, blockState, this.fluid);
        if (!bl2)
        {
            cir.setReturnValue(hitResult != null && this.placeFluid(player, world, hitResult.getBlockPos().offset(hitResult.getSide()), null));
        }
        if (world.getDimension().ultrawarm() && this.fluid.isIn(FluidTags.WATER))
        {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f);
            for (int l = 0; l < 8; ++l) {
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0, 0.0, 0.0);
            }
            cir.setReturnValue(true);
        }

        if (!world.isClient && bl && !blockState.isLiquid())
        {
            world.breakBlock(pos, true);
        }


        if (this.fluid == Fluids.WATER) {
            MiscUtils.placeNonPersistentWater(world, pos);
            this.playEmptyingSound(player, world, pos);
            cir.setReturnValue(true);
        }
        else
        {
            // Rest of your conditions for other fluids
            if (world.setBlockState(pos, this.fluid.getDefaultState().getBlockState(),
                    Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD) || blockState.getFluidState().isStill()) {
                this.playEmptyingSound(player, world, pos);
                cir.setReturnValue(true);
            }
        }


        cir.setReturnValue(cir.getReturnValue());
    }



}

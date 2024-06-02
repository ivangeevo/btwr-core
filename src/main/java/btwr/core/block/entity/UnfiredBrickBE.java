package btwr.core.block.entity;

import btwr.core.block.BTWR_Blocks;
import btwr.core.block.blocks.UnfiredBrickBlock;
import btwr.core.entity.BTWR_EntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class UnfiredBrickBE extends BlockEntity
{
   protected int dryingLevel = 0;

    private static final int TIME_TO_COOK = (10 * 60 * 20);
    private static final int RAIN_COOK_DECAY = 10;

    private boolean isDrying = false;


    public UnfiredBrickBE(BlockPos pos, BlockState state)
    {
        super(BTWR_EntityTypes.Blocks.BRICK_UNFIRED, pos, state);
    }

    public int getDryingLevel()
    {
        return dryingLevel;
    }

    @Override
    public void readNbt(NbtCompound nbt)
    {
        super.readNbt(nbt);
        this.dryingLevel = nbt.getShort("DryingTime");
    }

    @Override
    protected void writeNbt(NbtCompound nbt)
    {
        super.writeNbt(nbt);
        nbt.putShort("DryingTime", (short) this.dryingLevel);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, UnfiredBrickBE be)
    {
        if ( !world.isClient )
        {
            be.updateDrying();
        }
        else
        {
            if (be.isDrying)
            {
                if ( world.random.nextInt( 20 ) == 0 )
                {
                    double xPos = pos.getX() + 0.25F + world.random.nextFloat() * 0.5F;
                    double yPos = pos.getY() + 0.5F + world.random.nextFloat() * 0.25F;
                    double zPos = pos.getZ() + 0.25F + world.random.nextFloat() * 0.5F;

                    world.addParticle(ParticleTypes.WHITE_ASH, xPos, yPos, zPos, 0.0D, 0.0D, 0.0D );
                }
            }
        }
    }

    public void updateDrying()
    {
        boolean bNewDrying;
        assert world != null;
        //int iBlockMaxNaturalLight = world.getMaxLightLevel();
        //int iBlockCurrentNaturalLight = iBlockMaxNaturalLight - world.getBaseLightLevel(pos,0);

        //bNewDrying = iBlockCurrentNaturalLight >= 15;
        bNewDrying = world.getLightLevel(pos) >= 15;

        BlockState stateAbove = world.getBlockState( pos.up() );
        Block blockAbove = stateAbove.getBlock();

        /**
        if ( blockAbove != null && stateAbove.isTransparent(world, pos) )
        {
            bNewDrying = false;
        }
         **/

        if (bNewDrying != isDrying)
        {
            isDrying = bNewDrying;

            world.markDirty( pos );
        }

        UnfiredBrickBlock brickBlock = (UnfiredBrickBlock) BTWR_Blocks.BRICK_UNFIRED;

        if (isDrying)
        {
            dryingLevel++;

            if (dryingLevel >= TIME_TO_COOK)
            {
                brickBlock.onFinishedCooking(world, pos, world.getBlockState(pos));

                return;
            }
        }
        else
        {
            if ( isRainingOnBrick(world, pos) )
            {
                dryingLevel -= RAIN_COOK_DECAY;

                if (dryingLevel < 0 )
                {
                    dryingLevel = 0;
                }
            }
        }

        int iDisplayedDryLevel = brickBlock.getDryLevel(world, pos);
        int iCurrentDryLevel = computeDryLevel();;

        if ( iDisplayedDryLevel != iCurrentDryLevel )
        {
            UnfiredBrickBlock.setDryingLevel(world, pos, iCurrentDryLevel);
        }
    }

    public boolean isRainingOnBrick(World world, BlockPos pos)
    {
        return world.isRaining() && world.hasRain(pos);
    }

    private int computeDryLevel()
    {
        if (dryingLevel > 0 )
        {
            int iCookLevel = (int)(((float) dryingLevel / (float) TIME_TO_COOK) * 7F ) + 1;

            return MathHelper.clamp( iCookLevel, 0, 7 );
        }

        return 0;
    }


}

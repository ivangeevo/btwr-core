package btwr.core.block.blocks;

import btwr.core.block.BTWR_Blocks;
import btwr.core.block.interfaces.BlockAdded;
import btwr.core.item.BTWR_Items;
import btwr.core.tag.BTWRConventionalTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.state.property.Properties.LIT;


public class HempCropBlock extends CropBlock
{
    public static final BooleanProperty TOP = BooleanProperty.of("top");

    public HempCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), 0).with(TOP, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return BTWR_Items.HEMP_SEEDS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        super.appendProperties(builder);
        builder.add(TOP);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (!state.get(TOP) &&
        /** getWeedsGrowthLevel(world, pos) && **/ isValidLightSourceAbove(world, pos))
        {
            Block blockBelow = world.getBlockState(pos.down()).getBlock();

            if (blockBelow != null && ((BlockAdded) blockBelow).isBlockHydratedForPlantGrowthOn(world, pos.down()))
            {
                // only the base of the plants grows, and only does if its on hydrated soil

                if (state.get(AGE) < 7)
                {

                    float growthChance = getBaseGrowthChance() *
                            ((BlockAdded) blockBelow).getPlantGrowthOnMultiplier(world, pos.down(), this);

                    if (random.nextFloat() <= growthChance)
                    {
                        incrementGrowthLevel(world, pos, state);
                    }

                }
                else if (world.isAir(pos.up()))
                {

                    float topGrowthChance = (getBaseGrowthChance() / 4F) *
                            ((BlockAdded) blockBelow).getPlantGrowthOnMultiplier(world, pos.down(), this);

                    if (random.nextFloat() <= topGrowthChance)
                    {
                        world.setBlockState(pos.up(), state.with(TOP, true).with(AGE, 7),3);
                        ((BlockAdded) blockBelow).notifyOfFullStagePlantGrowthOn(world, pos.down(), this);
                    }

                }
            }
        }
    }



    @Override
    public boolean hasRandomTicks(BlockState state)
    {
        return !state.get(TOP);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
    {
        return super.canPlaceAt(state, world, pos) || !world.getBlockState(pos.down()).get(TOP) ;
    }

    protected void incrementGrowthLevel(World world, BlockPos pos, BlockState state)
    {
        int iGrowthLevel = this.getAge(state) + 1;

        world.setBlockState(pos, state.with(AGE, iGrowthLevel),3);

        if ( iGrowthLevel == 7 )
        {
            Block blockBelow = world.getBlockState(pos.down()).getBlock();

            if ( blockBelow != null )
            {
                ((BlockAdded)blockBelow).notifyOfFullStagePlantGrowthOn(world, pos.down(), this);
            }
        }
    }


    private boolean isValidLightSourceAbove(World world, BlockPos pos)
    {
        Block lightBlockLit = BTWR_Blocks.LIGHTBLOCK.getDefaultState().with(LIT, true).getBlock();
        boolean isAbove = world.getBlockState(pos.up(1)).isOf(lightBlockLit);
        boolean isTwoAbove = world.getBlockState(pos.up(2)).isOf(lightBlockLit);

        return (isAbove || isTwoAbove) || world.getLightLevel(pos) >= 15 ;
    }

    public float getBaseGrowthChance()
    {
        return 0.1F;
    }

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]
            {
                    // Define VoxelShapes for each stage from 0 to 7
                    Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 2.0, 11.0),
                    Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 4.0, 11.0),
                    Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
                    Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 8.0, 11.0),
                    Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 10.0, 11.0),
                    Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 12.0, 11.0),
                    Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 14.0, 11.0),
                    Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 16.0, 11.0)
            };
}

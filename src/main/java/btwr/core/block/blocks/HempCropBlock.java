package btwr.core.block.blocks;

import btwr.core.item.BTWR_Items;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

//TODO: Maybe it's better to have the hemp crop work similarly to the BWT(better with time) one where the boolean
//  property is "is_connected" and checks when the block above is of this, instead of "if its a top block" which is a
//  (presumably) wrong way of looking at it.
public class HempCropBlock extends CropBlock {
    public static final BooleanProperty IS_TOP = BooleanProperty.of("is_top");
    private static final float BASE_GROWTH_CHANCE = 0.1F;

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[] {
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 2.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 4.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 8.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 10.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 12.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 14.0, 11.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 16.0, 11.0)
    };

    public HempCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), 0).with(IS_TOP, false));
    }


    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(IS_TOP)) return;

        int lightLevel = world.getLightLevel(pos);
        if (lightLevel < 15 && !isValidAlternateLightSourceAbove(world, pos)) return;

        Block blockBelow = world.getBlockState(pos.down()).getBlock();
        if (blockBelow == null || !blockBelow.isBlockHydratedForPlantGrowthOn(world, pos.down())) return;

        if (state.get(AGE) < 7) {
            attemptGrowth(world, pos, state, random, blockBelow, BASE_GROWTH_CHANCE);
        } else if (world.isAir(pos.up())) {
            attemptTopGrowth(world, pos, state, random, blockBelow);
        }
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(IS_TOP);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState belowState = world.getBlockState(pos.down());
        return super.canPlaceAt(state, world, pos) || belowState.isOf(this) && !belowState.get(IS_TOP);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !state.get(IS_TOP);
    }

    private void attemptGrowth(World world, BlockPos pos, BlockState state, Random random, Block blockBelow, float growthChance) {
        float chance = growthChance * blockBelow.getPlantGrowthOnMultiplier(world, pos.down(), this);
        if (random.nextFloat() <= chance) {
            incrementGrowthLevel(world, pos, state);
        }
    }

    private void attemptTopGrowth(World world, BlockPos pos, BlockState state, Random random, Block blockBelow) {
        float topGrowthChance = (BASE_GROWTH_CHANCE / 4F) * blockBelow.getPlantGrowthOnMultiplier(world, pos.down(), this);
        if (random.nextFloat() <= topGrowthChance) {
            world.setBlockState(pos.up(), state.with(IS_TOP, true).with(AGE, 7), Block.NOTIFY_LISTENERS);
            blockBelow.notifyOfFullStagePlantGrowthOn(world, pos.down(), this);
        }
    }

    private void incrementGrowthLevel(World world, BlockPos pos, BlockState state) {
        int newAge = state.get(AGE) + 1;
        world.setBlockState(pos, state.with(AGE, newAge), Block.NOTIFY_LISTENERS);
        if (newAge == 7) {
            Block blockBelow = world.getBlockState(pos.down()).getBlock();
            if (blockBelow != null) {
                blockBelow.notifyOfFullStagePlantGrowthOn(world, pos.down(), this);
            }
        }
    }

    private boolean isValidAlternateLightSourceAbove(World world, BlockPos pos) {
        Block bwtLightBlock = Registries.BLOCK.get(Identifier.of("bwt", "light_block"));
        BlockState lightBlockState = FabricLoader.getInstance().isModLoaded("bwt")
                ? bwtLightBlock.getDefaultState()
                : Blocks.REDSTONE_LAMP.getDefaultState();

        return isLitLightBlock(world, pos.up(), lightBlockState) || isLitLightBlock(world, pos.up(2), lightBlockState);
    }

    private boolean isLitLightBlock(World world, BlockPos pos, BlockState lightBlockState) {
        return world.getBlockState(pos).equals(lightBlockState.with(Properties.LIT, true));
    }
}

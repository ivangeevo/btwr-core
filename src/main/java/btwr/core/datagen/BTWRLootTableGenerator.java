package btwr.core.datagen;

import btwr.core.block.BTWR_Blocks;
import btwr.core.block.blocks.HempCropBlock;
import btwr.core.item.BTWR_Items;
import btwr.core.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.predicate.item.ItemSubPredicateTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BTWRLootTableGenerator extends FabricBlockLootTableProvider
{
    private static final float[] LEAVES_STICK_DROP_CHANCE = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};
    public static final LootCondition.Builder WITH_AXE = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.AXES));
    public static final LootCondition.Builder WITH_AXE_HARVEST_FULL_BLOCK = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.AXES_HARVEST_FULL_BLOCK));
    public static final LootCondition.Builder CONVENTIONAL_WITH_SHEARS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ConventionalItemTags.SHEAR_TOOLS));
    private static final float[] JUNGLE_SAPLING_DROP_CHANCE = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

    public BTWRLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public final LootCondition.Builder createWithShearsOrSilkTouchConditionModded() {
        return CONVENTIONAL_WITH_SHEARS.or(this.createSilkTouchCondition());
    }

    public LootTable.Builder dropsWithSilkTouchOrShearsModded(Block block, LootPoolEntry.Builder<?> loot) {
        return drops(block, this.createWithShearsOrSilkTouchConditionModded(), loot);
    }


    @Override
    public void generate()
    {
        this.generateVanillaTables();
        this.generateModdedTables();

    }

    private void generateVanillaTables()
    {
        this.initLeavesDrops();
    }

    private void generateModdedTables()
    {
        this.initMiscDrops();
    }

    private void initLeavesDrops()
    {
        this.addDrop(Blocks.OAK_LEAVES, block -> this.oakLeavesDropsModded(block, Blocks.OAK_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.SPRUCE_LEAVES, block -> this.leavesDropsModded(block, Blocks.SPRUCE_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.BIRCH_LEAVES, block -> this.leavesDropsModded(block, Blocks.BIRCH_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.JUNGLE_LEAVES, block -> this.leavesDropsModded(block, Blocks.JUNGLE_SAPLING, JUNGLE_SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.ACACIA_LEAVES, block -> this.leavesDropsModded(block, Blocks.ACACIA_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.DARK_OAK_LEAVES, block -> this.oakLeavesDropsModded(block, Blocks.DARK_OAK_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.CHERRY_LEAVES, block -> this.leavesDropsModded(block, Blocks.CHERRY_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.AZALEA_LEAVES, block -> this.leavesDropsModded(block, Blocks.AZALEA, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.MANGROVE_LEAVES, this::mangroveLeavesDropsModded);
        this.addDrop(Blocks.FLOWERING_AZALEA_LEAVES, block -> this.leavesDropsModded(block, Blocks.FLOWERING_AZALEA, SAPLING_DROP_CHANCE));
    }

    private void initMiscDrops()
    {
        this.addDrop(BTWR_Blocks.BRICK, drops(Items.BRICK));
        this.addDrop(BTWR_Blocks.LIGHTBLOCK);

        this.addDrop(BTWR_Blocks.BRICK_UNFIRED, drops(Items.CLAY_BALL));
        this.addDrop(BTWR_Blocks.CROP_HEMP, block -> this.hempCropDrops(block, BTWR_Items.HEMP_LEAVES, BTWR_Items.HEMP_SEEDS));
        this.addDrop(BTWR_Blocks.ROPE_COIL, block -> this.ropeCoilDrops());
    }

    public LootTable.Builder hempCropDrops(Block block, Item hempLeaves, Item hempSeeds) {
        // Create a loot pool for shears condition with hemp leaves drop
        LootPool.Builder leavesPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(hempLeaves))
                .conditionally(CONVENTIONAL_WITH_SHEARS);

        // Create a loot pool for shears condition with hemp seeds drop (50% chance)
        LootPool.Builder seedsPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(hempSeeds)
                        .conditionally(RandomChanceLootCondition.builder(0.5f))
                        .conditionally(BlockStatePropertyLootCondition.builder(block)
                                .properties(StatePredicate.Builder.create().exactMatch(HempCropBlock.TOP, true))))
                .conditionally(CONVENTIONAL_WITH_SHEARS);

        // Combine both pools into the loot table
        return LootTable.builder()
                .pool(leavesPool)
                .pool(seedsPool);
    }

    private LootTable.Builder ropeCoilDrops()
    {
       return LootTable.builder()
                .pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(
                                        AlternativeEntry.builder(
                                                ItemEntry.builder(BTWR_Items.ROPE_COIL)
                                                        .conditionally(
                                                                MatchToolLootCondition.builder(
                                                                        ItemPredicate.Builder.create().tag(ItemTags.AXES)
                                                                )
                                                        ),
                                                ItemEntry.builder(BTWR_Items.ROPE)
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(9)))
                                        )
                                )
                );
    }

    public LootTable.Builder leavesDropsModded(Block leaves, Block drop, float... saplingChance) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouchOrShearsModded(
                leaves, this.addSurvivesExplosionCondition(leaves, ItemEntry.builder(drop))
                                .conditionally(TableBonusLootCondition.builder(impl.getOrThrow(Enchantments.FORTUNE), saplingChance))
        )
                .pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .conditionally(this.createWithShearsOrSilkTouchConditionModded().invert())
                                .with(
                                        this.applyExplosionDecay(leaves, ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F))))
                                                .conditionally(TableBonusLootCondition.builder(impl.getOrThrow(Enchantments.FORTUNE), LEAVES_STICK_DROP_CHANCE)))
                                );
    }

    public LootTable.Builder oakLeavesDropsModded(Block leaves, Block drop, float... chance) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.leavesDropsModded(leaves, drop, chance)
                .pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .conditionally(this.createWithShearsOrSilkTouchConditionModded().invert())
                                .with(
                                        this.addSurvivesExplosionCondition(leaves, ItemEntry.builder(Items.APPLE))
                                                .conditionally(TableBonusLootCondition.builder(impl.getOrThrow(Enchantments.FORTUNE), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))
                                )
                );
    }

    public LootTable.Builder mangroveLeavesDropsModded(Block leaves) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return dropsWithSilkTouch(
                leaves,
                this.applyExplosionDecay(
                                Blocks.MANGROVE_LEAVES, ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
                        )
                        .conditionally(TableBonusLootCondition.builder(impl.getOrThrow(Enchantments.FORTUNE), LEAVES_STICK_DROP_CHANCE))
        );
    }


    @Override
    public String getName() {
        return "BTWR Block Loot Tables";
    }
}

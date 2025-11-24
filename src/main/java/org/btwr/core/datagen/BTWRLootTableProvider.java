package org.btwr.core.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BTWRLootTableProvider extends FabricBlockLootTableProvider {
    private static final float[] LEAVES_STICK_DROP_CHANCE = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};

    private static final float[] JUNGLE_SAPLING_DROP_CHANCE = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

    public static final LootCondition.Builder WITH_CONVENTIONAL_SHEARS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ConventionalItemTags.SHEAR_TOOLS));

    public final LootCondition.Builder WITH_SILK_TOUCH_OR_SHEARS = WITH_CONVENTIONAL_SHEARS.or(this.createSilkTouchCondition());

    public BTWRLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.generateVanillaTables();
    }

    private void generateVanillaTables() {
        this.initLeavesDrops();
        addDrop(Blocks.VINE, BTWRLootTableProvider::dropsWithShears);
    }

    private void initLeavesDrops() {
        this.addDrop(Blocks.OAK_LEAVES, block -> this.oakLeavesDrops(block, Blocks.OAK_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.SPRUCE_LEAVES, block -> this.leavesDrops(block, Blocks.SPRUCE_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.BIRCH_LEAVES, block -> this.leavesDrops(block, Blocks.BIRCH_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.JUNGLE_LEAVES, block -> this.leavesDrops(block, Blocks.JUNGLE_SAPLING, JUNGLE_SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.ACACIA_LEAVES, block -> this.leavesDrops(block, Blocks.ACACIA_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.DARK_OAK_LEAVES, block -> this.oakLeavesDrops(block, Blocks.DARK_OAK_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.CHERRY_LEAVES, block -> this.leavesDrops(block, Blocks.CHERRY_SAPLING, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.AZALEA_LEAVES, block -> this.leavesDrops(block, Blocks.AZALEA, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.FLOWERING_AZALEA_LEAVES, block -> this.leavesDrops(block, Blocks.FLOWERING_AZALEA, SAPLING_DROP_CHANCE));
        this.addDrop(Blocks.MANGROVE_LEAVES, this::mangroveLeavesDrops);
    }

    public LootTable.Builder leavesDrops(Block leaves, Block drop, float... chance) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return dropsWithSilkTouchOrShears(
                leaves, this.addSurvivesExplosionCondition(leaves, ItemEntry.builder(drop)).conditionally(TableBonusLootCondition.builder(impl.getOrThrow(Enchantments.FORTUNE), chance)
        ))
                .pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .conditionally(WITH_SILK_TOUCH_OR_SHEARS.invert())
                                .with(
                                        this.applyExplosionDecay(leaves, ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F))))
                                                .conditionally(TableBonusLootCondition.builder(impl.getOrThrow(Enchantments.FORTUNE), LEAVES_STICK_DROP_CHANCE)
                                )));
    }

    public LootTable.Builder oakLeavesDrops(Block leaves, Block drop, float... chance) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.leavesDrops(leaves, drop, chance)
                .pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .conditionally(WITH_SILK_TOUCH_OR_SHEARS.invert())
                                .with(
                                        this.addSurvivesExplosionCondition(leaves, ItemEntry.builder(Items.APPLE))
                                                .conditionally(TableBonusLootCondition.builder(impl.getOrThrow(Enchantments.FORTUNE), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))
                                )
                );
    }

    public LootTable.Builder mangroveLeavesDrops(Block leaves) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return dropsWithSilkTouchOrShears(
                leaves,
                this.applyExplosionDecay(
                                Blocks.MANGROVE_LEAVES, ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
                        )
                        .conditionally(TableBonusLootCondition.builder(impl.getOrThrow(Enchantments.FORTUNE), LEAVES_STICK_DROP_CHANCE))
        );
    }

    public LootTable.Builder dropsWithSilkTouchOrShears(Block drop, LootPoolEntry.Builder<?> child) {
        return drops(drop, WITH_SILK_TOUCH_OR_SHEARS, child);
    }

    public static LootTable.Builder dropsWithShears(ItemConvertible drop) {
        return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f)).conditionally(WITH_CONVENTIONAL_SHEARS).with(ItemEntry.builder(drop)));
    }

    @Override
    public String getName() {
        return "BTWR Block Loot Tables";
    }
}
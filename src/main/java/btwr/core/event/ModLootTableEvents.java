package btwr.core.event;

import btwr.core.BTWRMod;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.predicate.item.ItemSubPredicateTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModLootTableEvents {

    private static final RegistryKey<LootTable> DIRT_LOOT_TABLE_ID = RegistryKey.of(
            RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("blocks/dirt"));

    private static final RegistryKey<LootTable> GRASS_BLOCK_LOOT_TABLE_ID = RegistryKey.of(
            RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("blocks/grass_block"));

    private static final LootCondition.Builder WITHOUT_HOE = MatchToolLootCondition.builder(
            ItemPredicate.Builder.create().tag(ItemTags.HOES)).invert();

    public static void initialize() {
        // set new loot tables for when breaking dirt and grass blocks
        if (BTWRMod.getInstance().settings.shouldChangeHoesBTWStyle()) {
            replaceGrassAndDirtLootTables();
        }
    }

    private static void replaceGrassAndDirtLootTables() {
        LootTableEvents.REPLACE.register((key, original, source, registries) -> {
            if (key.equals(DIRT_LOOT_TABLE_ID)) {
                return createDirtLootTable();
            }

            if (key.equals(GRASS_BLOCK_LOOT_TABLE_ID)) {
                return createGrassBlockLootTable(registries);
            }

            return null; // don't replace other tables
        });
    }

    private static LootTable createDirtLootTable() {
        LootPool.Builder pool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .conditionally(SurvivesExplosionLootCondition.builder())
                // Added a condition "drop dirt when not a hoe". This makes it so it does not drop when
                // using hoes with left click break to make farmland.
                .with(ItemEntry.builder(Items.DIRT).conditionally(WITHOUT_HOE));

        return LootTable.builder().pool(pool).build();
    }

    private static LootTable createGrassBlockLootTable(RegistryWrapper.WrapperLookup registryLookup) {
        LeafEntry.Builder<?> grassBlockItem = ItemEntry.builder(Items.GRASS_BLOCK);
        LeafEntry.Builder<?> dirtItem = ItemEntry.builder(Items.DIRT);

        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(dirtItem.conditionally(WITHOUT_HOE))
                .alternatively(grassBlockItem.conditionally(createSilkTouchCondition(registryLookup)));

        return LootTable.builder().pool(LootPool.builder().with(alternativeEntry)).build();
    }

    public static LootCondition.Builder createSilkTouchCondition(RegistryWrapper.WrapperLookup registryLookup) {
        RegistryWrapper.Impl<Enchantment> impl = registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return MatchToolLootCondition.builder(
                ItemPredicate.Builder.create()
                        .subPredicate(
                                ItemSubPredicateTypes.ENCHANTMENTS,
                                EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(impl.getOrThrow(Enchantments.SILK_TOUCH), NumberRange.IntRange.atLeast(1))))
                        )
        );
    }
}

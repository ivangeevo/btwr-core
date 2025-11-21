package org.btwr.core.datagen;

import org.btwr.core.BTWRMod;
import org.btwr.core.item.BTWR_Items;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BTWRLangProvider extends FabricLanguageProvider {

    public BTWRLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder tb) {
        addItemGroup("group_btwr", "Better Than Wolves: Remastered!", tb);
        this.addBlockTranslations(tb);
        this.addItemTranslations(tb);
        this.addConfigTranslations(tb);
    }

    private void addBlockTranslations(TranslationBuilder tb) {
        //tb.add();
    }

    private void addItemTranslations(TranslationBuilder tb) {
        tb.add(BTWR_Items.COOKED_KEBAB, "Cooked Kebab");
        tb.add(BTWR_Items.EGG_SCRAMBLED_COOKED, "Scrambled Eggs");
        tb.add(BTWR_Items.EGG_SCRAMBLED_RAW, "Raw Scrambled Eggs");
        tb.add(BTWR_Items.HAM_AND_EGGS, "Ham and Eggs");
        tb.add(BTWR_Items.HEARTY_STEW, "Hearty Stew");
        tb.add(BTWR_Items.MUSHROOM_OMELETTE_COOKED, "Mushroom Omelette");
        tb.add(BTWR_Items.MUSHROOM_OMELETTE_RAW, "Raw Mushroom Omelette");
        tb.add(BTWR_Items.PORK_DINNER, "Pork Dinner");
        tb.add(BTWR_Items.RAW_KEBAB, "Raw Kebab");
        tb.add(BTWR_Items.SANDWICH, "Tasty Sandwich");
        tb.add(BTWR_Items.STEAK_AND_POTATOES, "Steak and Potatoes");
        tb.add(BTWR_Items.STEAK_DINNER, "Steak Dinner");
        tb.add(BTWR_Items.WOLF_DINNER, "Wolf Dinner");
        tb.add(BTWR_Items.CHICKEN_SOUP, "Chicken Soup");
        tb.add(BTWR_Items.CHOWDER, "Chowder");
        tb.add(BTWR_Items.BEAST_LIVER_COOKED, "Cooked Liver");
        tb.add(BTWR_Items.BEAST_LIVER_RAW, "Liver Of The Beast");
        tb.add(BTWR_Items.CREEPER_OYSTERS, "Creeper Oysters");

        tb.add(BTWR_Items.DIAMOND_SHEARS, "Diamond Shears");
        tb.add(BTWR_Items.CLUB_WOOD, "Wooden Club");
        tb.add(BTWR_Items.CLUB_BONE, "Bone Club");

        tb.add(BTWR_Items.LEATHER_TANNED_BOOTS, "Tanned Leather Boots");
        tb.add(BTWR_Items.LEATHER_TANNED_CHESTPLATE, "Tanned Leather Chestplate");
        tb.add(BTWR_Items.LEATHER_TANNED_HELMET, "Tanned Leather Helmet");
        tb.add(BTWR_Items.LEATHER_TANNED_LEGGINGS, "Tanned Leather Leggings");

        tb.add(BTWR_Items.DIAMOND_INGOT, "Diamond Ingot");
        tb.add(BTWR_Items.DIAMOND_PLATE,"Diamond Armor Plate");
        tb.add(BTWR_Items.LEATHER_CUT, "Cut Leather");
        tb.add(BTWR_Items.LEATHER_SCOURED, "Scoured Leather");
        tb.add(BTWR_Items.LEATHER_SCOURED_CUT, "Cut Scoured Leather");
        tb.add(BTWR_Items.LEATHER_TANNED, "Tanned Leather");
        tb.add(BTWR_Items.LEATHER_TANNED_CUT, "Cut Tanned Leather");

        //tb.add(BTWR_Items.ANCIENT_PROPHECHY, "");
    }

    private void addConfigTranslations(TranslationBuilder tb) {
        addConfigMenuTitle("BTWR: Core Configuration Menu", tb);
        addConfigCategory("general", "General Options", tb);
        addConfigCategory("entity", "Entity Options", tb);
        addConfig("knockbackRestriction", "Knockback Restriction", tb);
        addConfig("spawnBabyZombies", "Spawn Baby Zombies", tb);
        addConfig("spawnMobsOnWood", "Mobs Spawn On Wood", tb);
        addConfig("increasedMonsterSpawnsPerChunk", "Increased Mobs Per Chunk", tb);
        addConfig("btwHoeFunctionality", "BTW Styled Hoes Functionality", tb);
        addConfig("changedCreeperExplosionPos", "Changed Creeper Explosion Origin", tb);
        addConfigTooltip("knockbackRestriction", "Disables knockback if not using a suitable weapon", tb);
        addConfigTooltip("spawnBabyZombies", "Can baby zombies spawn naturally?", tb);
        addConfigTooltip("spawnMobsOnWood", "Can mobs spawn on wooden blocks?", tb);
        addConfigTooltip("increasedMonsterSpawnsPerChunk", "Slightly increase the amount of mobs that can spawn per chunk", tb);
        addConfigTooltip("btwHoeFunctionality", "Hoes now function only with left-click breaking grass/tillable blocks to create farmland (like in BTW)", tb);
        addConfigTooltip("changedCreeperExplosionPos", "Changes the location of creeper's explosion origin to be calculated from their eyes instead of their feet", tb);
    }

    private void addItemGroup(String entryPath, String translation, TranslationBuilder tb) {
        tb.add("itemgroup." + entryPath, translation);
    }

    private void addConfigMenuTitle(String translation, TranslationBuilder tb) {
        tb.add("title." + BTWRMod.MOD_ID + ".config", translation);
    }

    private void addConfigCategory(String categoryPath, String translation, TranslationBuilder tb) {
        tb.add("config." + BTWRMod.MOD_ID + ".category." + categoryPath, translation);
    }

    private void addConfig(String configPath, String translation, TranslationBuilder tb) {
        tb.add("config." + BTWRMod.MOD_ID + "." + configPath, translation);
    }

    private void addConfigTooltip(String configPath, String translation, TranslationBuilder tb) {
        tb.add("config." + BTWRMod.MOD_ID + ".tooltip." + configPath, translation);
    }
}
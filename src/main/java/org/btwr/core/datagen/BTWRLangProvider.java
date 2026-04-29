package org.btwr.core.datagen;

import org.btwr.core.BTWRMod;
import org.btwr.core.block.BTWR_Blocks;
import org.btwr.core.item.BTWR_Items;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BTWRLangProvider extends FabricLanguageProvider {

    public BTWRLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup)
    {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder tb) {
        this.addItemGroup("group_btwr", "Better Than Wolves: Remastered!", tb);
        this.generateBlockTranslations(tb);
        this.generateItemTranslations(tb);
        this.generateConfigTranslations(tb);

        tb.add("subtitles.btwr.creeper_sheared", "Creeper sheared");
    }

    private void generateBlockTranslations(TranslationBuilder tb) {
        tb.add(BTWR_Blocks.BLIGHT, "Blight");
    }

    private void generateItemTranslations(TranslationBuilder tb) {
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

        tb.add(BTWR_Items.OCULAR_OF_ENDER, "Ocular of Ender");
        tb.add(BTWR_Items.ENDER_SPECTACLES, "Ender Spectacles");
    }

    private void generateConfigTranslations(TranslationBuilder tb) {
        this.addConfigMenuDefaults(tb);
        this.addConfigMenuTitle("BTWR: Core Client Configuration Menu", tb);
        this.addConfigCategory("general", "General", tb);
        //this.addConfig("knockbackRestrictions", "Knockback Restrictions", tb);
        //this.addConfig("spawnBabyZombies", "Spawn Baby Zombies", tb);
        //this.addConfig("spawnMobsOnWood", "Spawn Mobs on Wood", tb);
        //this.addConfig("increasedMonsterSpawnsPerChunk", "Increase Mobs Spawns per Chunk", tb);
        //this.addConfig("changedCreeperExplosionPos", "Changed Creeper Explosion", tb);
        //this.addConfigTooltip("knockbackRestrictions", "Disables knockback if not using a suitable weapon", tb);
        //this.addConfigTooltip("spawnBabyZombies", "Can baby zombies spawn naturally?", tb);
        //this.addConfigTooltip("spawnMobsOnWood", "Can mobs spawn on wooden blocks?", tb);
        //this.addConfigTooltip("increasedMonsterSpawnsPerChunk", "Slightly increase the amount of mobs that can spawn per chunk", tb);
        //this.addConfigTooltip("changedCreeperExplosionPos", "Changes the location of creeper's explosion origin to\n be calculated from their eyes instead of their feet", tb);
    }

    private void addConfigMenuDefaults(TranslationBuilder tb) {
        this.addSimpleText("clientSettingsText", "Client Settings:", tb);
        this.addSimpleText("emptyClientConfigText", "§eNote:§r There are currently no client config settings.", tb);
        this.addSimpleText("serverSettingsText", "Server Settings:", tb);
        this.addSimpleText("serverSettingsNoAccessText", "§eNote:§r Server settings are not accessible in menus." +
                "\nThey can only be changed by editing the config file manually and require a world reload to take effect.", tb
        );
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

    private void addSimpleText(String configPath, String translation, TranslationBuilder tb) {
        tb.add("config." + BTWRMod.MOD_ID + ".text." + configPath, translation);
    }

    private void addConfig(String configPath, String translation, TranslationBuilder tb) {
        tb.add("config." + BTWRMod.MOD_ID + "." + configPath, translation);
    }

    private void addConfigTooltip(String configPath, String translation, TranslationBuilder tb) {
        tb.add("config." + BTWRMod.MOD_ID + ".tooltip." + configPath, translation);
    }

}
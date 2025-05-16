package btwr.core.config;

public class BTWRSettings {

    public boolean knockbackRestrictions = true;
    public boolean spawnBabyZombies = false;
    public boolean spawnMobsOnWood = false;

    public boolean increasedMonsterSpawnsPerChunk = true;

    public boolean shouldDoKnockbackRestrictions() {
        return knockbackRestrictions;
    }
    public boolean shouldSpawnBabyZombies() {
        return spawnBabyZombies;
    }
    public boolean shouldMobsSpawnOnWood() {
        return spawnMobsOnWood;
    }
    public boolean shouldIncreaseMaxMobCapacity() { return increasedMonsterSpawnsPerChunk; }

}

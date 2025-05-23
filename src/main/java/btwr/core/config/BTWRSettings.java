package btwr.core.config;

public class BTWRSettings {

    // general
    public boolean knockbackRestrictions = true;
    public boolean btwHoeFunctionality = true;

    // entity
    public boolean spawnBabyZombies = false;
    public boolean spawnMobsOnWood = false;
    public boolean increasedMonsterSpawnsPerChunk = true;
    public boolean changedCreeperExplosionPos = true;


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
    public boolean shouldChangeCreeperExplosionPos() { return changedCreeperExplosionPos; }
    public boolean shouldChangeHoesBTWStyle() { return btwHoeFunctionality; }

}

package btwr.core.config;

public class BTWRSettings
{

    public boolean knockbackRestrictions = true;
    public boolean babyZombiesSpawn = false;
    public boolean mobsSpawnOnWood = false;


    public boolean shouldDoKnockbackRestrictions() {
        return knockbackRestrictions;
    }
    public boolean shouldSpawnBabyZombies() {
        return babyZombiesSpawn;
    }
    public boolean shouldMobsSpawnOnWood() {
        return mobsSpawnOnWood;
    }


}

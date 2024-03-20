package btwr.core.config;

public class BTWRSettings
{

    public boolean knockbackRestriction = true;
    public boolean dontSpawnBabyZombies = true;

    public boolean isDontSpawnBabyZombiesEnabled() {
        return dontSpawnBabyZombies;
    }

    public boolean isKnockbackRestrictionEnabled() {
        return knockbackRestriction;
    }


}

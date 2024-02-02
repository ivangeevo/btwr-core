// FCMOD

package btwr.core.entity.ai.goal;


import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.mob.CreeperEntity;

public class CreeperSwellBehavior extends CreeperIgniteGoal {
    private CreeperEntity myCreeper;

    public CreeperSwellBehavior(CreeperEntity creeper )
    {
        super( creeper );

        myCreeper = creeper;
    }

    @Override
    public boolean canStart()
    {
        if (!myCreeper.isIgnited() && myCreeper.isNeutered())
        {
            return false;
        }
        else if ( myCreeper.getIsDeterminedToExplode() )
        {
            return true;
        }

        return super.canStart();
    }

    @Override
    public void tick()
    {
        if (myCreeper.isNeutered() )
        {
            myCreeper.setFuseSpeed(-1);
        }
        else if (!myCreeper.getIsDeterminedToExplode() &&
                ( myCreeper.getTarget() == null || myCreeper.getAttackDistanceScalingFactor(this.myCreeper.getTarget()) > 36D ||
                        !myCreeper.canSee(myCreeper.getTarget()) ) )
        {
            myCreeper.setFuseSpeed(-1);
        }
        else
        {
            myCreeper.setFuseSpeed(1);
        }
    }
}

package btwr.core.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.mob.CreeperEntity;
import org.jetbrains.annotations.Nullable;

public class CreeperSwellBehavior extends CreeperIgniteGoal {
    private CreeperEntity myCreeper;

    @Nullable
    private LivingEntity target;


    public CreeperSwellBehavior(CreeperEntity creeper) {
        super(creeper);
        myCreeper = creeper;
    }

    @Override
    public boolean canStart() {
        if (!myCreeper.isIgnited() && myCreeper.isNeutered()) {
            return false;
        } else if (myCreeper.getIsDeterminedToExplode()) {
            return true;
        }
        return super.canStart();
    }

    @Override
    public void start() {
        this.myCreeper.getNavigation().stop();
        this.target = this.myCreeper.getTarget();
    }

    @Override
    public void stop() {
        this.target = null;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.target == null || this.myCreeper.isNeutered()) {
            this.myCreeper.setFuseSpeed(-1);
            return;
        }
        if (this.myCreeper.squaredDistanceTo(this.target) > 36.0) {
            this.myCreeper.setFuseSpeed(-1);
            return;
        }
        if (!this.myCreeper.getVisibilityCache().canSee(this.target)) {
            this.myCreeper.setFuseSpeed(-1);
            return;
        }
        this.myCreeper.setFuseSpeed(1);

    }
}

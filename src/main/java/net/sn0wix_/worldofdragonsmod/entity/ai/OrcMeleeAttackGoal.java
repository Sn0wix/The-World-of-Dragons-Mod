package net.sn0wix_.worldofdragonsmod.entity.ai;/*package net.sn0wix_.worldofdragonsmod.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.util.math.BlockPos;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMod;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.ModOrcEntity;

import java.util.EnumSet;

public class OrcMeleeAttackGoal extends Goal {

    protected final ModOrcEntity mob;
    private final double speed;
    private Path path;
    private final int ticksToAttack;
    private int timer = 0;

    public OrcMeleeAttackGoal(ModOrcEntity mob, double speed, int ticksToAttack) {
        this.mob = mob;
        this.speed = speed;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        this.ticksToAttack = ticksToAttack;
    }

    @Override
    public void tick() {
        if (canStop()){
            path = null;
            timer = 0;
        }

        if (mob != null && mob.getTarget() != null) {
            LivingEntity target = mob.getTarget();
            this.mob.getLookControl().lookAt(target, 30.0F, 30.0F);

            if (timer <= mob.attackAnimationLength) {
                timer++;
                WorldOfDragonsMod.LOGGER.info("timer++");
            }

            if (mob.attackAnimationLength == timer) {
                mob.isAttacking = false;
                timer = 0;
                WorldOfDragonsMod.LOGGER.info("is not attacking or timer is larger than the animation");
            }

            if (path == null && !mob.isAttacking) {
                path = mob.getNavigation().findPathTo(target.getBlockPos(), 0);
                mob.getNavigation().startMovingAlong(path, speed);
                WorldOfDragonsMod.LOGGER.info("setted navigation and started moving");
            }

            if (path != null) {
                BlockPos mobPos = mob.getBlockPos();
                BlockPos targetPos = target.getBlockPos();

                if (isNear(mobPos, targetPos)) {
                    WorldOfDragonsMod.LOGGER.info("mob is near target");
                    mob.isAttacking = true;
                    mob.getNavigation().stop();

                    if (ticksToAttack == timer) {
                        mob.tryAttack(target);
                        WorldOfDragonsMod.LOGGER.info("cooldown is bigger or equal to timer");
                    }
                }
            }

            if (mob.getNavigation().getCurrentPath() != null){
                mob.getNavigation().recalculatePath();
            }
        }
    }

    @Override
    public boolean canStart() {
        return mob != null && mob.isAlive() && mob.getTarget() != null;
    }

    @Override
    public boolean canStop() {
        return mob == null || mob.isDead() || mob.getTarget() == null;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    private boolean isNear(BlockPos mobPos, BlockPos targetPos) {
        if (mobPos.equals(targetPos)) {
            return true;
        }

        int mobPosX = mobPos.getX();
        int mobPosY = mobPos.getY();
        int mobPosZ = mobPos.getZ();

        int targetPosX = targetPos.getX();
        int targetPosY = targetPos.getY();
        int targetPosZ = targetPos.getZ();

        boolean x = false;
        boolean y = false;
        boolean z = false;

        if (mobPosX - targetPosX == 1 || mobPosX - targetPosX == -1 || mobPosX - targetPosX == 0) {
            x = true;
        }

        if (mobPosY - targetPosY == -1 || mobPosY - targetPosY == 0) {
            y = true;
        }

        if (mobPosZ - targetPosZ == 1 || mobPosZ - targetPosZ == -1 || mobPosZ - targetPosZ == 0) {
            z = true;
        }

        return x && y && z;
    }
}
*/
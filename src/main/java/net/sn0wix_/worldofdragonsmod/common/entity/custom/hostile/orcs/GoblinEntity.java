package net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.orcs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import net.sn0wix_.worldofdragonsmod.common.entity.ai.MMEntityMoveHelper;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class GoblinEntity extends ModOrcEntity {
    public static final RawAnimation WALK = RawAnimation.begin().then("move.walk", Animation.LoopType.LOOP);
    public static final RawAnimation IDLE = RawAnimation.begin().then("move.idle", Animation.LoopType.LOOP);

    public static final RawAnimation ATTACK = RawAnimation.begin().then("attack.melee", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation DEATH = RawAnimation.begin().then("move.death", Animation.LoopType.HOLD_ON_LAST_FRAME);

    private int attackTicksLeft = 0;

    public GoblinEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1f, false));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 2, state -> walkIdlePredicate(state, WALK, IDLE))
                .triggerableAnim("attack", ATTACK).triggerableAnim("death", DEATH));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            if (attackTicksLeft > 0) {
                this.getNavigation().stop();

                /*if (this.getTarget() != null) {
                    this.getLookControl().lookAt(this.getTarget());
                }*/

                attackTicksLeft--;
                if (attackTicksLeft <= 0 && this.getTarget() != null) {
                    tryDelayedAttack(this.getTarget());
                }
            }
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (attackTicksLeft <= 0 && !this.getWorld().isClient) {
            this.triggerAnim("controller", "attack");
            this.attackTicksLeft = 6;
        }
        return true;
    }

    public void tryDelayedAttack(Entity target) {
        if (attackTicksLeft == 0 && target instanceof LivingEntity) {
            double squaredDistance = this.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
            double d = this.getSquaredMaxAttackDistance((LivingEntity) target);

            if (squaredDistance <= d) {
                this.getNavigation().stop();
                super.tryAttack(target);
            }
        }
    }
}

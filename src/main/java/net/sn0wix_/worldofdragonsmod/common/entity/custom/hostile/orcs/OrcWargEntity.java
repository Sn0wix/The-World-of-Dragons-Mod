package net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.orcs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.entity.ai.MMEntityMoveHelper;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class OrcWargEntity extends ModOrcEntity {
    public static final RawAnimation IDLE = RawAnimation.begin().then("warg.animation.idle", Animation.LoopType.LOOP);
    public static final RawAnimation WALK_BODY = RawAnimation.begin().then("warg.animation.walk", Animation.LoopType.LOOP);
    public static final RawAnimation RUN_BODY = RawAnimation.begin().then("animation.warg.run", Animation.LoopType.LOOP);

    public static final RawAnimation BITE = RawAnimation.begin().then("animation.warg.attack_head", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation WALK_HEAD = RawAnimation.begin().then("warg.animation.walk_head", Animation.LoopType.LOOP);
    public static final RawAnimation RUN_HEAD = RawAnimation.begin().then("warg.animation.run_head", Animation.LoopType.LOOP);

    private int attackTicksLeft = 0;

    public OrcWargEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        moveControl = new MMEntityMoveHelper(this, 90);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "body", 2, this::bodyPredicate));
        controllerRegistrar.add(new AnimationController<>(this, "head", 2, this::headPredicate).triggerableAnim("bite", BITE));
    }

    private PlayState headPredicate(AnimationState<OrcWargEntity> state) {
        if (state.isMoving()) {
            return this.isAttacking() ? state.setAndContinue(RUN_HEAD) : state.setAndContinue(WALK_HEAD);
        }

        return PlayState.STOP;
    }

    private <T extends GeoAnimatable> PlayState bodyPredicate(AnimationState<T> state) {
        if (state.isMoving()) {
            return this.isAttacking() ? state.setAndContinue(RUN_BODY) : state.setAndContinue(WALK_BODY);
        }

        return state.setAndContinue(IDLE);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(2, new MeleeAttackGoal(this, 2.2f, false));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            if (attackTicksLeft > 0) {

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
            this.triggerAnim("head", "bite");
            this.attackTicksLeft = 6;
        }
        return true;
    }

    public void tryDelayedAttack(Entity target) {
        if (attackTicksLeft == 0 && target instanceof LivingEntity) {
            double squaredDistance = this.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
            double d = this.getSquaredMaxAttackDistance((LivingEntity) target);

            if (squaredDistance <= d) {
                super.tryAttack(target);
            }
        }
    }
}

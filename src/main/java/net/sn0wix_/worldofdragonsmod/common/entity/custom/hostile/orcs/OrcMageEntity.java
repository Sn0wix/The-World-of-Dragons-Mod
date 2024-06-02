package net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.orcs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animation.*;

public class OrcMageEntity extends ModOrcEntity {
    public static final RawAnimation WALK = RawAnimation.begin().then("animation.orc_mage.walk", Animation.LoopType.LOOP);
    public static final RawAnimation IDLE = RawAnimation.begin().then("animation.orc_mage.idle", Animation.LoopType.LOOP);

    public static final RawAnimation ATTACK_MELEE = RawAnimation.begin().then("animation.orc_mage.attack", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation ATTACK_SPRAY = RawAnimation.begin().then("animation.orc_mage.attack.spray", Animation.LoopType.PLAY_ONCE);

    public static final RawAnimation ORB = RawAnimation.begin().then("animation.orc_mage.orb.slow", Animation.LoopType.LOOP);
    public static final RawAnimation ORB_ATTACK = RawAnimation.begin().then("animation.orc_mage.orb.attack", Animation.LoopType.PLAY_ONCE);

    private int attackTicksLeft = 0;
    private int attackAnimTicksLeft = 0;

    public OrcMageEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1f, false));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 8.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 2, state -> walkIdlePredicate(state, WALK, IDLE))
                .triggerableAnim("attack", ATTACK_MELEE).triggerableAnim("spray", ATTACK_SPRAY));
        controllerRegistrar.add(new AnimationController<>(this, "orb_controller", 1,
                state -> state.setAndContinue(ORB)).triggerableAnim("attack", ORB_ATTACK));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            if (attackAnimTicksLeft > 0) {
                this.getNavigation().stop();
                attackAnimTicksLeft--;
            }

            if (attackTicksLeft > 0) {
                this.getNavigation().stop();

                attackTicksLeft--;
                if (attackTicksLeft <= 0 && this.getTarget() != null) {
                    tryDelayedAttack(this.getTarget());
                }
            }
        }
    }

    public void triggerAttackAnims() {
        this.triggerAnim("controller", "attack");
        this.triggerAnim("orb_controller", "attack");
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (attackTicksLeft <= 0 && !this.getWorld().isClient && attackAnimTicksLeft <= 0) {
            triggerAttackAnims();
            this.attackTicksLeft = 10;
            this.attackAnimTicksLeft = 30;
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

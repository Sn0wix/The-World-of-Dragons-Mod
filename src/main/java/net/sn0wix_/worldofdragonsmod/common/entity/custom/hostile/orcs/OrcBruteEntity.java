package net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.orcs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class OrcBruteEntity extends ModOrcEntity {
    public static final RawAnimation WALK = RawAnimation.begin().then("move.walk", Animation.LoopType.LOOP);
    public static final RawAnimation IDLE = RawAnimation.begin().then("move.idle", Animation.LoopType.LOOP);
    public static final RawAnimation ATTACK_MELEE = RawAnimation.begin().then("attack.melee", Animation.LoopType.PLAY_ONCE);

    public static final RawAnimation ATTACK_SMASH = RawAnimation.begin().then("attack.smash", Animation.LoopType.PLAY_ONCE);

    private int attackTicksLeft = 0;
    private int attackAnimTicksLeft = 0;
    private ATTACK_TYPE lastAttackedType = ATTACK_TYPE.NONE;

    public OrcBruteEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1f, false));
        //this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));

        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, SnowGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PigEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, WanderingTraderEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 4.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 2, this::predicate)
                .triggerableAnim("attack", ATTACK_MELEE).triggerableAnim("smash", ATTACK_SMASH));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {
        if (state.isMoving()) {
            return state.setAndContinue(WALK);
        }

        return state.setAndContinue(IDLE);
    }

    @Override
    public void tick() {
        super.tick();
        if (attackAnimTicksLeft > 0) {
            this.getNavigation().stop();
            attackAnimTicksLeft--;
        }

        if (attackTicksLeft > 0) {
            attackTicksLeft--;
            if (attackTicksLeft <= 0 && this.getTarget() != null) {
                tryDelayedAttack(this.getTarget());
            }
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (random.nextInt(4) == 0 && attackAnimTicksLeft <= 0 && !this.getWorld().isClient && attackTicksLeft <= 0) {
            this.triggerAnim("controller", "smash");
            this.attackTicksLeft = 8;
            this.attackAnimTicksLeft = 20;
            this.lastAttackedType = ATTACK_TYPE.SMASH;
        } else if (attackAnimTicksLeft <= 0 && !this.getWorld().isClient && attackTicksLeft <= 0) {
            this.triggerAnim("controller", "attack");
            this.attackTicksLeft = 8;
            this.attackAnimTicksLeft = 13;
            this.lastAttackedType = ATTACK_TYPE.GENERIC;
        }

        return true;
    }

    public void tryDelayedAttack(Entity target) {
        if (attackTicksLeft == 0 && target instanceof LivingEntity) {
            if (lastAttackedType == ATTACK_TYPE.GENERIC) {
                double squaredDistance = this.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
                double d = this.getSquaredMaxAttackDistance((LivingEntity) target);

                if (squaredDistance <= d) {
                    this.getNavigation().stop();
                    super.tryAttack(target);
                }
            } else if (lastAttackedType == ATTACK_TYPE.SMASH) {
                WorldOfDragons.LOGGER.info("SMASH");
            }
        }
    }


    /*if (!this.getWorld().isClient) {
            Box box = this.getBoundingBox().expand(2);
            this.getWorld().getOtherEntities(this, box).forEach(entity ->
                    entity.damage(this.getDamageSources().generic(), 5f)
            );
        }*/

    public enum ATTACK_TYPE {
        NONE,
        GENERIC,
        SMASH
    }
}

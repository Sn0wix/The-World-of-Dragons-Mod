package net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class LavaElementalEntity extends HostileEntity implements GeoEntity, RangedAttackMob {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public static final RawAnimation WALK = RawAnimation.begin().then("move.walk", Animation.LoopType.LOOP);
    public static final RawAnimation IDLE = RawAnimation.begin().then("move.idle", Animation.LoopType.LOOP);
    public static final RawAnimation SLEEP = RawAnimation.begin().then("move.sleep", Animation.LoopType.LOOP);

    public static final RawAnimation WAKE_UP = RawAnimation.begin().then("move.wake_up", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation UPPERCUT = RawAnimation.begin().then("attack.uppercut", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation SLAM = RawAnimation.begin().then("attack.slam", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation SHOOT = RawAnimation.begin().then("attack.shoot", Animation.LoopType.PLAY_ONCE);


    public static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(LavaElementalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public LavaElementalEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 4, this::predicate));
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1f, false));
        //this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new WanderAroundGoal(this, 1.0));


        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, SnowGolemEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PigEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, WanderingTraderEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 80)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 4.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 2f);
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    private PlayState predicate(AnimationState<LavaElementalEntity> state) {
        if (dataTracker.get(SLEEPING)) {
            return state.setAndContinue(SLEEP);
        } else if (state.isMoving()) {
            return state.setAndContinue(WALK);
        }

        return state.setAndContinue(IDLE);

    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            if (dataTracker.get(SLEEPING) && goalSelector.getGoals().isEmpty()) {
                goalSelector.clear(goal -> true);
            } else if (!dataTracker.get(SLEEPING) && !goalSelector.getGoals().isEmpty()) {
                initGoals();
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.getWorld().isClient && dataTracker.get(SLEEPING)) {
            dataTracker.set(SLEEPING, false);
        }

        return super.damage(source, amount);
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SNOW_GOLEM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRON_GOLEM_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_STEP, 0.15f, 1.0f);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        getDataTracker().startTracking(SLEEPING, true);
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {

    }
}

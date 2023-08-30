package net.sn0wix_.worldofdragonsmod.entity.custom.orcs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMod;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class ArmoredOrcEntity extends ModOrcEntity implements GeoEntity {
    private final AnimatableInstanceCache cache =  new SingletonAnimatableInstanceCache(this);

    public static final RawAnimation WALK = RawAnimation.begin().then("animation.armored_orc.walk", Animation.LoopType.LOOP);
    public static final RawAnimation IDLE = RawAnimation.begin().then("animation.armored_orc.idle", Animation.LoopType.LOOP);
    public static final RawAnimation DEATH = RawAnimation.begin().then("animation.armored_orc.death", Animation.LoopType.HOLD_ON_LAST_FRAME);

    public static final RawAnimation ATTACK = RawAnimation.begin().then("animation.armored_orc.attack", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation STAB = RawAnimation.begin().then("animation.armored_orc.stab", Animation.LoopType.PLAY_ONCE);
    //1/6
    public static final RawAnimation KNOCKBACK = RawAnimation.begin().then("animation.armored_orc.knockback", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation CHARGE = RawAnimation.begin().then("animation.armored_orc.charge", Animation.LoopType.LOOP);

    public ArmoredOrcEntity(EntityType<? extends HostileEntity> entityType, World world) {
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
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 4.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 2, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {
        if (state.isMoving()) {
            WorldOfDragonsMod.LOGGER.info("walk");
            return state.setAndContinue(WALK);
        }
        WorldOfDragonsMod.LOGGER.info("idle");
        return state.setAndContinue(IDLE);
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
//charge - attack ze zadu
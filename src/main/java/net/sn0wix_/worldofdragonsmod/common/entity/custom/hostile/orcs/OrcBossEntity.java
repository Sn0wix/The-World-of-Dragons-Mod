package net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.orcs;

import net.minecraft.entity.EntityType;
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
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class OrcBossEntity extends ModOrcEntity {
    public static final RawAnimation WALK = RawAnimation.begin().then("move.walk", Animation.LoopType.LOOP);
    public static final RawAnimation IDLE = RawAnimation.begin().then("move.idle", Animation.LoopType.LOOP);

    public static final RawAnimation ATTACK_STOMP = RawAnimation.begin().then("attack.stomp", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation ATTACK_AXE = RawAnimation.begin().then("attack.axe", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation ATTACK_SUPER = RawAnimation.begin().then("attack.super", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation ATTACK_SUMMON = RawAnimation.begin().then("attack.summon", Animation.LoopType.PLAY_ONCE);

    public static final RawAnimation DEATH = RawAnimation.begin().then("move.death", Animation.LoopType.HOLD_ON_LAST_FRAME);

    public OrcBossEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1f, false));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 14.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 3.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 2, this::predicate)
                .triggerableAnim("stomp", ATTACK_STOMP).triggerableAnim("axe", ATTACK_AXE).triggerableAnim("super", ATTACK_SUPER)
                .triggerableAnim("summon", ATTACK_SUMMON));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {
        if (state.isMoving()) {
            return state.setAndContinue(WALK);
        }

        return state.setAndContinue(IDLE);
    }
}

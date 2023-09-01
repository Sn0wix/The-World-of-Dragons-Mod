package net.sn0wix_.worldofdragonsmod.entity.custom.orcs;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class ArmoredOrcEntity extends ModOrcEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public static final RawAnimation WALK = RawAnimation.begin().then("animation.armored_orc.walk", Animation.LoopType.LOOP);
    public static final RawAnimation IDLE = RawAnimation.begin().then("animation.armored_orc.idle", Animation.LoopType.LOOP);
    public static final RawAnimation DEATH = RawAnimation.begin().then("animation.armored_orc.death", Animation.LoopType.HOLD_ON_LAST_FRAME);

    public static final RawAnimation ATTACK = RawAnimation.begin().then("animation.armored_orc.attack", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation STAB = RawAnimation.begin().then("animation.armored_orc.stab", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation KNOCKBACK = RawAnimation.begin().then("animation.armored_orc.knockback", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation CHARGE = RawAnimation.begin().then("animation.armored_orc.charge", Animation.LoopType.LOOP);

    private int attackTicksLeft = 0;
    private int attackAnimTicksLeft = 0;
    private int knockackTicksLeft = 0;
    private int lastAttackedType = 0;
    private boolean charging = false;

    public ArmoredOrcEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1f, false));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
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
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 4.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5f);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 2, this::predicate)
                .triggerableAnim("attack", ATTACK).triggerableAnim("stab", STAB).triggerableAnim("knockback", KNOCKBACK));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {
        if (state.isMoving()) {
            return state.setAndContinue(WALK);
        }
        return state.setAndContinue(IDLE);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.attackAnimTicksLeft > 0) {
            return super.damage(source, amount);
        } else {
            if (!this.getWorld().isClient) {
                this.triggerAnim("controller", "knockback");
                this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.HOSTILE);

                if (knockackTicksLeft == 0){
                    this.knockackTicksLeft = 8;

                    try {
                        float strength = 1.5f;
                        double x = this.getX() - source.getSource().getX();
                        double z = this.getZ() - source.getSource().getZ();

                        this.velocityDirty = true;
                        Vec3d vec3d = this.getVelocity();
                        Vec3d vec3d2 = new Vec3d(x, 0.0, z).normalize().multiply(strength);
                        source.getSource().setVelocity(vec3d.x / 2.0 - vec3d2.x, this.onGround ? Math.min(0.4, vec3d.y / 2.0 + strength) : vec3d.y, vec3d.z / 2.0 - vec3d2.z);
                        source.getSource().velocityModified = true;
                    } catch (NullPointerException ignored) {}
                }
            }
            return false;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {

            if (knockackTicksLeft > 0){
                knockackTicksLeft--;
            }

            if (attackAnimTicksLeft > 0) {
                this.getNavigation().stop();
                attackAnimTicksLeft--;
            }

            if (attackTicksLeft > 0) {
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
        if (attackAnimTicksLeft <= 0 && !this.getWorld().isClient && attackTicksLeft <= 0) {
            this.triggerAnim("controller", "attack");
            this.attackTicksLeft = 7;
            this.attackAnimTicksLeft = 14;
            this.lastAttackedType = 1;
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

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private double getSquaredMaxAttackDistance(LivingEntity entity) {
        return this.getWidth() * 2.0F * this.getWidth() * 2.0F + entity.getWidth();
    }
}
//charge - attack ze zadu
package net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.orcs;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.effect.ModEffects;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class SlasherOrcEntity extends ModOrcEntity {

    public static final RawAnimation WALK = RawAnimation.begin().then("move.walk", Animation.LoopType.LOOP);
    public static final RawAnimation IDLE = RawAnimation.begin().then("move.idle", Animation.LoopType.LOOP);

    public static final RawAnimation ATTACK_MELEE = RawAnimation.begin().then("attack.melee", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation ATTACK_STAB = RawAnimation.begin().then("attack.stab", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation DEATH = RawAnimation.begin().then("move.death", Animation.LoopType.HOLD_ON_LAST_FRAME);

    private int attackTicksLeft = 0;
    private int attackAnimTicksLeft = 0;
    private ATTACK_TYPE lastAttackedType = ATTACK_TYPE.NONE;

    public SlasherOrcEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1f, false));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 2, this::predicate)
                .triggerableAnim("attack_melee", ATTACK_MELEE).triggerableAnim("attack_stab", ATTACK_STAB).triggerableAnim("death", DEATH));
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
        if (!this.getWorld().isClient) {
            if (attackAnimTicksLeft > 0) {
                this.getNavigation().stop();
                attackAnimTicksLeft--;
            }

            if (attackTicksLeft > 0) {
                this.getNavigation().stop();

                /*if (this.getTarget() != null) {
                    this.getLookControl().lookAt(this.getTarget());
                }*/

                attackTicksLeft--;
                if (attackTicksLeft <= 0 && this.getTarget() != null) {
                    tryDelayedAttack(this.getTarget());
                    lastAttackedType = ATTACK_TYPE.NONE;
                }
            }
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        int i = random.nextInt(6);
        if (attackTicksLeft <= 0 && !this.getWorld().isClient) {

            if (i == 0) {
                this.triggerAnim("controller", "attack_stab");
                this.attackTicksLeft = 8;
                this.lastAttackedType = ATTACK_TYPE.STAB;
                this.attackAnimTicksLeft = 20;
            } else {
                this.triggerAnim("controller", "attack_melee");
                this.attackTicksLeft = 6;
                this.lastAttackedType = ATTACK_TYPE.GENERIC;
                this.attackAnimTicksLeft = 12;
            }
        }
        return true;
    }

    public void tryDelayedAttack(Entity target) {
        if (attackTicksLeft == 0 && target instanceof LivingEntity) {
            double squaredDistance = this.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
            double d = this.getSquaredMaxAttackDistance((LivingEntity) target);

            if (squaredDistance <= d) {
                if (lastAttackedType == ATTACK_TYPE.GENERIC) {
                    this.getNavigation().stop();
                    super.tryAttack(target);
                } else if (lastAttackedType == ATTACK_TYPE.STAB) {

                    this.getNavigation().stop();
                    int i;
                    float f = 2f;
                    float g = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);

                    f += EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity) target).getGroup());
                    g += (float) EnchantmentHelper.getKnockback(this);
                    if ((i = EnchantmentHelper.getFireAspect(this)) > 0) {
                        target.setOnFireFor(i * 4);
                    }

                    if (target.damage(this.getDamageSources().mobAttack(this), f)) {
                        if (g > 0.0f) {
                            ((LivingEntity) target).takeKnockback(g * 0.5f, MathHelper.sin(this.getYaw() * ((float) Math.PI / 180)), -MathHelper.cos(this.getYaw() * ((float) Math.PI / 180)));
                            this.setVelocity(this.getVelocity().multiply(0.6, 1.0, 0.6));
                        }
                        ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(ModEffects.BLEEDING, 100, 3, true, false, true));
                        this.applyDamageEffects(this, target);
                        this.onAttacking(target);
                    }
                }
            }
        }
    }

    public enum ATTACK_TYPE {
        NONE,
        GENERIC,
        STAB
    }
}

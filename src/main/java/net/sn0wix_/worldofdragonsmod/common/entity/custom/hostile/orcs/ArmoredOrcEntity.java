package net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.orcs;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
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
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ArmoredOrcEntity extends ModOrcEntity {
    public static final RawAnimation WALK = RawAnimation.begin().then("animation.armored_orc.walk", Animation.LoopType.LOOP);
    public static final RawAnimation IDLE = RawAnimation.begin().then("animation.armored_orc.idle", Animation.LoopType.LOOP);
    public static final RawAnimation DEATH = RawAnimation.begin().then("animation.armored_orc.death", Animation.LoopType.HOLD_ON_LAST_FRAME);

    public static final RawAnimation ATTACK = RawAnimation.begin().then("animation.armored_orc.attack", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation STAB = RawAnimation.begin().then("animation.armored_orc.stab", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation KNOCKBACK = RawAnimation.begin().then("animation.armored_orc.knockback", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation CHARGE = RawAnimation.begin().then("animation.armored_orc.charge", Animation.LoopType.LOOP);

    private int attackTicksLeft = 0;
    private int attackAnimTicksLeft = 0;
    private ATTACK_TYPE lastAttackedType = ATTACK_TYPE.NONE;
    private int ticksToCharge = 0;
    private final HashMap<Entity, Integer> enitiesThrownBack = new HashMap<>();
    MeleeAttackGoal attackGoal;
    MeleeAttackGoal attackGoalCharge;
    public static final TrackedData<Boolean> CHARGING = DataTracker.registerData(ArmoredOrcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public ArmoredOrcEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, this.attackGoal = new MeleeAttackGoal(this, 1f, false));
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
            return this.dataTracker.get(CHARGING) ? state.setAndContinue(CHARGE) : state.setAndContinue(WALK);
        }

        return state.setAndContinue(IDLE);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.attackAnimTicksLeft > 0) {
            return super.damage(source, amount);
        } else {
            if (source.isIn(DamageTypeTags.IS_FALL) || source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) || source.isIn(DamageTypeTags.IS_DROWNING) || source.isIn(DamageTypeTags.IS_FREEZING) || source.isIn(DamageTypeTags.IS_FIRE)) {
                return super.damage(source, amount);
            }

            if (!this.getWorld().isClient) {
                if (!dataTracker.get(CHARGING)) {
                    this.triggerAnim("controller", "knockback");
                }

                this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.HOSTILE);

                if (!this.enitiesThrownBack.containsKey(source.getSource())) {
                    this.enitiesThrownBack.put(source.getSource(), 8);

                    try {
                        float strength = 1.5f;
                        double x = this.getX() - source.getSource().getX();
                        double z = this.getZ() - source.getSource().getZ();

                        this.velocityDirty = true;
                        Vec3d vec3d = this.getVelocity();
                        Vec3d vec3d2 = new Vec3d(x, 0.0, z).normalize().multiply(strength);
                        source.getSource().setVelocity(vec3d.x / 2.0 - vec3d2.x, this.isOnGround() ? Math.min(0.4, vec3d.y / 2.0 + strength) : vec3d.y, vec3d.z / 2.0 - vec3d2.z);
                        source.getSource().velocityModified = true;
                    } catch (NullPointerException ignored) {
                    }
                }
            }
            return false;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            Iterator<Map.Entry<Entity, Integer>> iterator = this.enitiesThrownBack.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Entity, Integer> entry = iterator.next();
                Entity entity = entry.getKey();
                int knockbackTicksLeft = entry.getValue();

                knockbackTicksLeft--;
                this.enitiesThrownBack.put(entity, knockbackTicksLeft);

                if (knockbackTicksLeft == 0) {
                    iterator.remove();
                }
            }

            if (ticksToCharge > 0) {
                ticksToCharge++;
            }

            if (dataTracker.get(CHARGING)) {
                if (attackTicksLeft <= 0 && getTarget() != null) {
                    double squaredDistance = this.squaredDistanceTo(getTarget().getX(), getTarget().getY(), getTarget().getZ());
                    double d = this.getSquaredMaxAttackDistance(getTarget());

                    if (squaredDistance <= d) {
                        this.attackTicksLeft = 4;
                        this.attackAnimTicksLeft = 16;
                        this.lastAttackedType = ATTACK_TYPE.STAB;
                        this.triggerAnim("controller", "stab");
                    }
                }

                if (attackTicksLeft == 0 && getTarget() == null) {
                    dataTracker.set(CHARGING, false);
                }
            }


            if (shouldCharge(this, this.getTarget()) && !dataTracker.get(CHARGING)) {
                this.goalSelector.remove(attackGoal);
                this.goalSelector.add(2, this.attackGoalCharge = new MeleeAttackGoal(this, 2f, false));
                this.dataTracker.set(CHARGING, true);
                this.getWorld().playSound(null, getBlockPos(), SoundEvents.ITEM_TRIDENT_RETURN, SoundCategory.HOSTILE, 50f, 1f);
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
            this.lastAttackedType = ATTACK_TYPE.GENERIC;
        }
        return true;
    }

    public void tryDelayedAttack(Entity target) {
        if (attackTicksLeft == 0 && target instanceof LivingEntity) {
            if (lastAttackedType == ATTACK_TYPE.GENERIC) {
                double squaredDistance = this.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
                double d = this.getSquaredMaxAttackDistance((LivingEntity) target);
                lastAttackedType = ATTACK_TYPE.NONE;

                if (squaredDistance <= d) {
                    this.getNavigation().stop();
                    super.tryAttack(target);
                }
            } else if (lastAttackedType == ATTACK_TYPE.STAB) {
                double squaredDistance = this.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
                double d = this.getSquaredMaxAttackDistance((LivingEntity) target);
                lastAttackedType = ATTACK_TYPE.NONE;
                dataTracker.set(CHARGING, false);
                this.goalSelector.remove(attackGoalCharge);
                this.goalSelector.add(2, attackGoal);
                this.getWorld().playSound(null, getBlockPos(), SoundEvents.ITEM_TRIDENT_HIT, SoundCategory.HOSTILE);

                if (squaredDistance <= d) {
                    this.getNavigation().stop();

                    int i;
                    float f = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) * 2;
                    float g = 1;
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
                        this.applyDamageEffects(this, target);
                        this.onAttacking(target);
                    }
                }
            }
        }
    }

    private boolean shouldCharge(Entity attacker, Entity target) {
        if (attacker == null || target == null) {
            return false;
        }

        boolean bl = false;
        int x = (int) Math.abs(attacker.getPos().x - target.getPos().x);
        int z = (int) Math.abs(attacker.getPos().z - target.getPos().z);

        int chargeRange = 10;
        if (x >= chargeRange || z >= chargeRange) {
            if (ticksToCharge == 0) {
                ticksToCharge = 1;
            }

            if (random.nextInt((int) Math.round(Math.exp((double) - ticksToCharge / 100) * 1000)) == 0) {
                bl = true;
                ticksToCharge = 0;
            }
        }else {
            if (ticksToCharge > 0){
                ticksToCharge = 0;
            }
        }

        if (random.nextInt((int) Math.round(Math.exp((double) -x / 10) * 10000)) == 0) {
            bl = true;
        }

        if (random.nextInt((int) Math.round(Math.exp((double) -z / 10) * 10000)) == 0) {
            bl = true;
        }

        return bl;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        getDataTracker().startTracking(CHARGING, false);
    }

    public enum ATTACK_TYPE {
        NONE,
        GENERIC,
        STAB
    }
}

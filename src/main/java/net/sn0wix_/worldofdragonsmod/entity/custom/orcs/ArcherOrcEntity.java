package net.sn0wix_.worldofdragonsmod.entity.custom.orcs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMain;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class ArcherOrcEntity extends ModOrcEntity implements GeoEntity, RangedAttackMob {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public static final RawAnimation IDLE = RawAnimation.begin().then("move.idle", Animation.LoopType.LOOP);
    public static final RawAnimation WALK = RawAnimation.begin().then("move.walk", Animation.LoopType.LOOP);

    public static final RawAnimation ATTACK_MELEE = RawAnimation.begin().then("attack.melee", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation ATTACK_SHOOT = RawAnimation.begin().then("attack.shoot", Animation.LoopType.PLAY_ONCE);


    private MeleeAttackGoal meleeAttackGoal;
    private ProjectileAttackGoal projectileAttackGoal;
    private int meleeAttackTicksLeft = 0;
    private int meleeAttackAnimTicksLeft = 0;
    private int shootAttackTicksLeft = 0;
    private int shootAttackAnimTicksLeft = 0;
    private final int minShootRange;
    private boolean shooting = false;
    private final int maxShootRange;

    public ArcherOrcEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.minShootRange = this.random.nextBetween(4, 6);
        this.maxShootRange = 23;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, this.meleeAttackGoal = new MeleeAttackGoal(this, 1f, false));
        this.projectileAttackGoal = new ProjectileAttackGoal(this, 0f, 45, 50, 50);
        //this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));

        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, SnowGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PigEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, WanderingTraderEntity.class, true));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 2, this::predicate).triggerableAnim("attack_melee", ATTACK_MELEE)
                .triggerableAnim("attack_shoot", ATTACK_SHOOT));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {
        if (state.isMoving()) {
            return state.setAndContinue(WALK);
        }
        return state.setAndContinue(IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            if (shootAttackTicksLeft > 0) {
                this.getNavigation().stop();

                /*if (this.getTarget() != null) {
                    this.getLookControl().lookAt(this.getTarget());
                }*/

                shootAttackTicksLeft--;
                if (shootAttackTicksLeft == 0 && shooting) {
                    delayedShoot(this.getTarget());
                }
            }

            if (shootAttackAnimTicksLeft > 0) {
                this.getNavigation().stop();
                shootAttackAnimTicksLeft--;
            }

            if (meleeAttackTicksLeft > 0) {
                this.getNavigation().stop();

                /*if (this.getTarget() != null) {
                    this.getLookControl().lookAt(this.getTarget());
                }*/

                meleeAttackTicksLeft--;
                if (meleeAttackTicksLeft == 0 && this.getTarget() != null && !shooting) {
                    tryDelayedMeleeAttack(this.getTarget());
                }
            }

            if (meleeAttackAnimTicksLeft > 0) {
                this.getNavigation().stop();
                meleeAttackAnimTicksLeft--;
            }

            if (this.getTarget() != null && shouldSwitchAttackModes(this, getTarget())) {
                if (shooting) {
                    this.goalSelector.remove(meleeAttackGoal);
                    this.goalSelector.add(2, projectileAttackGoal);
                } else {
                    this.goalSelector.remove(projectileAttackGoal);
                    this.goalSelector.add(2, meleeAttackGoal);
                }
            }
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (meleeAttackTicksLeft == 0 && !this.getWorld().isClient && meleeAttackAnimTicksLeft == 0 && shootAttackAnimTicksLeft == 0 && shootAttackTicksLeft == 0 && !shooting) {
            this.triggerAnim("controller", "attack_melee");
            this.meleeAttackTicksLeft = 10;
            this.meleeAttackAnimTicksLeft = 18;
        }
        return true;
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        WorldOfDragonsMain.LOGGER.info("shoot");
        if (meleeAttackTicksLeft == 0 && !this.getWorld().isClient && meleeAttackAnimTicksLeft == 0 && shootAttackAnimTicksLeft == 0 && shootAttackTicksLeft == 0 && shooting) {
            this.triggerAnim("controller", "attack_shoot");
            this.shootAttackTicksLeft = 26;
            this.shootAttackAnimTicksLeft = 37;
        }
    }

    private void delayedShoot(Entity target) {
        if (shootAttackTicksLeft == 0 && target instanceof LivingEntity) {
            ArrowEntity arrow = new ArrowEntity(this.getWorld(), this);
            arrow.setPos(getX(), getY() + getEyeHeight(getPose()) - 1, getZ());
            arrow.setNoGravity(true);
            double d = target.getX() - this.getX();
            double e = target.getEyeHeight(target.getPose()) + target.getY() - arrow.getY();
            double f = target.getZ() - this.getZ();
            double g = Math.sqrt(d * d + f * f);
            arrow.setVelocity(d, e + g * (double) 0.2f, f, (float) 1.6, 1);
            this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
            this.world.spawnEntity(arrow);
        } else if (shootAttackTicksLeft == 0 && target == null) {
            ArrowEntity arrow = new ArrowEntity(this.getWorld(), this);
            arrow.setPos(getX(), getY() + getEyeHeight(getPose()) - 1, getZ());
            this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
            this.world.spawnEntity(arrow);
        }
    }

    private boolean shouldSwitchAttackModes(Entity attacker, Entity target) {
        if (attacker == null || target == null) {
            return false;
        }

        boolean bl = false;
        int x = (int) Math.abs(attacker.getPos().x - target.getPos().x);
        int y = (int) Math.abs(attacker.getPos().y - target.getPos().y);
        int z = (int) Math.abs(attacker.getPos().z - target.getPos().z);

        if (x > minShootRange || y > minShootRange || z > minShootRange) {
            if (x > maxShootRange - 2 || y > maxShootRange - 2 || z > maxShootRange - 2) {
                shooting = false;
                bl = true;
            } else {
                if (!shooting) {
                    shooting = true;
                    bl = true;
                }
            }
        } else if (x < minShootRange || y < minShootRange || z < minShootRange) {
            if (shooting) {
                shooting = false;
                bl = true;
            }
        }
        return bl;
    }

    public void tryDelayedMeleeAttack(Entity target) {
        if (meleeAttackTicksLeft == 0 && target instanceof LivingEntity) {
            double squaredDistance = this.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
            double d = this.getSquaredMaxAttackDistance((LivingEntity) target);

            if (squaredDistance <= d) {
                this.getNavigation().stop();
                super.tryAttack(target);
            }
        }
    }

    private double getSquaredMaxAttackDistance(LivingEntity entity) {
        return this.getWidth() * 2.0F * this.getWidth() * 2.0F + entity.getWidth();
    }
}

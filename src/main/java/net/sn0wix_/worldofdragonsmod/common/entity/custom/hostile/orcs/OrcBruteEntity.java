package net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.orcs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.entity.ai.MMEntityMoveHelper;
import net.sn0wix_.worldofdragonsmod.common.entity.ai.MMPathNavigateGround;
import net.sn0wix_.worldofdragonsmod.common.util.blockWaves.BlockWave;
import net.sn0wix_.worldofdragonsmod.common.util.blockWaves.BlockWaves;
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
    public final float maxIndirectSmashDistance = 3;
    private ATTACK_TYPE lastAttackedType = ATTACK_TYPE.NONE;

    public OrcBruteEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        moveControl = new MMEntityMoveHelper(this, 90);
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
        if (getTarget() != null) {
            getLookControl().lookAt(getTarget());
        }

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

        if (getTarget() != null && random.nextInt(50) == 0 && canSmashIndirectly(getTarget())) {
            startSmashingIf();
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (random.nextInt(6) == 0) {
            startSmashingIf();
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
            } else if (lastAttackedType == ATTACK_TYPE.SMASH && getWorld() instanceof ServerWorld serverWorld) {
                BlockWaves.addWave(new BlockWave(0.1f, 250, getPos(), new Vec3d(getLookControl().getLookX(), getLookControl().getLookY(), getLookControl().getLookZ()), getYaw(), 4, 4, serverWorld));
            }
        }
    }

    public void startSmashingIf() {
        if ((attackAnimTicksLeft <= 0 && !this.getWorld().isClient && attackTicksLeft <= 0 && getTarget() != null)) {
            this.triggerAnim("controller", "smash");
            this.attackTicksLeft = 8;
            this.attackAnimTicksLeft = 20;
            this.lastAttackedType = ATTACK_TYPE.SMASH;
        }
    }

    public boolean canSmashIndirectly(Entity target) {
        if (target != null && attackAnimTicksLeft == 0 && attackTicksLeft == 0) {
            double x = target.getX() - getX();
            double y = target.getY() - getY();
            double z = target.getZ() - getZ();

            double c = Math.sqrt(x * x + z * z);
            double cy = Math.sqrt(c * c + y * y);

            return c >= maxIndirectSmashDistance && cy >= maxIndirectSmashDistance;
        }
        return false;
    }


    /*if (!this.getWorld().isClient) {
            Box box = this.getBoundingBox().expand(2);
            this.getWorld().getOtherEntities(this, box).forEach(entity ->
                    entity.damage(this.getDamageSources().generic(), 5f)
            );
        }*/
    @Override
    protected EntityNavigation createNavigation(World world) {
        return new MMPathNavigateGround(this, world);
    }


    public enum ATTACK_TYPE {
        NONE,
        GENERIC,
        SMASH
    }
}

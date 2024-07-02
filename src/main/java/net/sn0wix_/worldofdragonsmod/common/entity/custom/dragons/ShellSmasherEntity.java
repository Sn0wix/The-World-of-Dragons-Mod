package net.sn0wix_.worldofdragonsmod.common.entity.custom.dragons;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.entity.ai.MMEntityMoveHelper;
import net.sn0wix_.worldofdragonsmod.common.entity.controlling.PlayerControllable;
import net.sn0wix_.worldofdragonsmod.common.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.InstancedAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class ShellSmasherEntity extends ControllableDragonEntity implements GeoEntity, ShellSmasherAnimations, PlayerControllable {

    //Animations
    public final AnimatableInstanceCache cache = new InstancedAnimatableInstanceCache(this);

    //Data
    public static final TrackedData<Boolean> HAS_ARMOR = DataTracker.registerData(ShellSmasherEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> HAS_SADDLE = DataTracker.registerData(ShellSmasherEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> IS_SLEEPING = DataTracker.registerData(ShellSmasherEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> IS_FLYING = DataTracker.registerData(ShellSmasherEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> IS_GLIDING = DataTracker.registerData(ShellSmasherEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> IS_SWIMMING = DataTracker.registerData(ShellSmasherEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public boolean canMove = true;
    public boolean canHavePassengers = true;


    public ShellSmasherEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        moveControl = new MMEntityMoveHelper(this, 45);
    }

    @Override
    public void initGoals() {
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.7));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6);
    }

    //Geckolib
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "generic", 2, this::predicate)
                .triggerableAnim("start_sleeping", ENTER_SLEEP).triggerableAnim("stop_sleeping", WAKE_UP));
    }


    public PlayState predicate(AnimationState<ShellSmasherEntity> state) {
        if (getMoveAnim() != null && getIdleAnim() != null) {
            return state.setAndContinue(state.isMoving() ? getMoveAnim() : getIdleAnim());
        } else {
            if (getMoveAnim() == null) {
                return state.setAndContinue(getIdleAnim());
            } else {
                return state.setAndContinue(getMoveAnim());
            }
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.isSneaking() && isOwner(player)) {
            return ActionResult.success(toggleSleep());
        }

        if (!this.hasPassengers() && dataTracker.get(HAS_SADDLE) && !dataTracker.get(IS_SLEEPING)) {
            player.startRiding(this);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    protected boolean canStartRiding(Entity entity) {
        return super.canStartRiding(entity) && !dataTracker.get(IS_SLEEPING);
    }

    //Movement
    @Override
    public void tick() {
        super.tick();
        if (hasPassengers() && !canHavePassengers) {
            getPassengerList().forEach(Entity::dismountVehicle);
        }
    }

    //Dismounting
    @Nullable
    private Vec3d locateSafeDismountingPos(Vec3d offset, LivingEntity passenger) {
        double d = this.getX() + offset.x;
        double e = this.getBoundingBox().minY;
        double f = this.getZ() + offset.z;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        block0:
        for (EntityPose entityPose : passenger.getPoses()) {
            mutable.set(d, e, f);
            double g = this.getBoundingBox().maxY + 0.75;
            do {
                double h = this.getWorld().getDismountHeight(mutable);
                if ((double) mutable.getY() + h > g) continue block0;
                if (Dismounting.canDismountInBlock(h)) {
                    Box box = passenger.getBoundingBox(entityPose);
                    Vec3d vec3d = new Vec3d(d, (double) mutable.getY() + h, f);
                    if (Dismounting.canPlaceEntityAt(this.getWorld(), passenger, box.offset(vec3d))) {
                        passenger.setPose(entityPose);
                        return vec3d;
                    }
                }
                mutable.move(Direction.UP);
            } while ((double) mutable.getY() < g);
        }
        return null;
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d vec3d = AbstractHorseEntity.getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.RIGHT ? 90.0f : -90.0f));
        Vec3d vec3d2 = this.locateSafeDismountingPos(vec3d, passenger);
        if (vec3d2 != null) {
            return vec3d2;
        }
        Vec3d vec3d3 = AbstractHorseEntity.getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.LEFT ? 90.0f : -90.0f));
        Vec3d vec3d4 = this.locateSafeDismountingPos(vec3d3, passenger);
        if (vec3d4 != null) {
            return vec3d4;
        }
        return this.getPos();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(HAS_ARMOR, false);
        dataTracker.startTracking(HAS_SADDLE, true);
        dataTracker.startTracking(IS_SLEEPING, false);
        dataTracker.startTracking(IS_GLIDING, false);
        dataTracker.startTracking(IS_FLYING, false);
        dataTracker.startTracking(IS_SWIMMING, false);
    }


    //Other and helper methods

    @Override
    public RawAnimation getIdleAnim() {
        if (dataTracker.get(IS_SLEEPING)) {
            return SLEEP;
        }
        if (dataTracker.get(IS_GLIDING)) {
            return null;
        }
        if (dataTracker.get(IS_FLYING)) {
            return FLY;
        }
        if (dataTracker.get(IS_SWIMMING)) {
            return null;
        }
        return IDLE;
    }

    @Override
    public RawAnimation getMoveAnim() {
        if (dataTracker.get(IS_SLEEPING)) {
            return null;
        }
        if (dataTracker.get(IS_GLIDING)) {
            return GLIDE;
        }
        if (dataTracker.get(IS_FLYING)) {
            return FLY;
        }
        if (dataTracker.get(IS_SWIMMING)) {
            return null;
        }
        return WALK;
    }

    public boolean toggleSleep() {
        if (dataTracker.get(IS_SWIMMING) || dataTracker.get(IS_FLYING) || dataTracker.get(IS_GLIDING)) {
            return false;
        }
        if (dataTracker.get(IS_SLEEPING)) {
            triggerAnim("generic", "stop_sleeping");
            initGoals();
            canMove = true;
            canHavePassengers = true;
            dataTracker.set(IS_SLEEPING, false);
        } else {
            triggerAnim("generic", "start_sleeping");
            goalSelector.clear(goal -> true);
            getNavigation().stop();
            canMove = false;
            canHavePassengers = false;
            dataTracker.set(IS_SLEEPING, true);
        }
        return true;
    }

    @Override
    public @Nullable ItemStack getPickBlockStack() {
        return new ItemStack(ModItems.SHELL_SMASHER_EGG);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public EntityView method_48926() {
        return this.getEntityWorld();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean isPushable() {
        return !this.hasPassengers() || !dataTracker.get(IS_SLEEPING);
    }
}

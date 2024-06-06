package net.sn0wix_.worldofdragonsmod.common.entity.custom.dragons;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.entity.ai.MMEntityMoveHelper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.InstancedAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public abstract class TameableDragonEntity extends TameableEntity implements GeoEntity {
    //Animations
    public final AnimatableInstanceCache cache = new InstancedAnimatableInstanceCache(this);

    public static final RawAnimation WALK = RawAnimation.begin().then("move.walk", Animation.LoopType.LOOP);
    public static final RawAnimation IDLE = RawAnimation.begin().then("move.idle", Animation.LoopType.LOOP);

    public static final RawAnimation FLY = RawAnimation.begin().then("move.fly", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation GLIDE = RawAnimation.begin().then("move.glide", Animation.LoopType.PLAY_ONCE);

    public static final RawAnimation SLEEP = RawAnimation.begin().then("sleep.idle", Animation.LoopType.LOOP);
    public static final RawAnimation ENTER_SLEEP = RawAnimation.begin().then("sleep.enter", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation WAKE_UP = RawAnimation.begin().then("sleep.wake_up", Animation.LoopType.PLAY_ONCE);


    //Data
    public static final TrackedData<Boolean> HAS_ARMOR = DataTracker.registerData(TameableDragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> HAS_SADDLE = DataTracker.registerData(TameableDragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> IS_SLEEPING = DataTracker.registerData(TameableDragonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public TameableDragonEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        moveControl = new MMEntityMoveHelper(this, 45);
    }

    public PlayState genericMovementPredicate(AnimationState<? extends TameableDragonEntity> state) {
        if (!dataTracker.get(IS_SLEEPING)) {
            if (state.isMoving()) {
                return state.setAndContinue(WALK);
            }
            return state.setAndContinue(IDLE);
        }

        return PlayState.CONTINUE;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(HAS_ARMOR, false);
        dataTracker.startTracking(HAS_SADDLE, true);
        dataTracker.startTracking(IS_SLEEPING, false);
    }

    @Nullable
    @Override
    public abstract ItemStack getPickBlockStack();

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
}

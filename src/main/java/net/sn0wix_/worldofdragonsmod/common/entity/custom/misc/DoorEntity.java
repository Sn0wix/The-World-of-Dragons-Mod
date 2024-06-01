package net.sn0wix_.worldofdragonsmod.common.entity.custom.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Objects;

public class DoorEntity extends Entity implements GeoEntity {
    //TODO fix hitbox tracking and add trigger command
    public static final TrackedData<Boolean> IS_OPENED = DataTracker.registerData(DoorEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final RawAnimation OPEN_ANIMATION = RawAnimation.begin().then("open", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation OPENED_ANIMATION = RawAnimation.begin().then("opened", Animation.LoopType.HOLD_ON_LAST_FRAME);
    private final SingletonAnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);


    public DoorEntity(EntityType<?> type, World world) {
        super(type, world);
        ignoreCameraFrustum = true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> {
            if (dataTracker.get(IS_OPENED)) {
                return state.setAndContinue(OPENED_ANIMATION);
            } else {
                return PlayState.CONTINUE;
            }
        }).triggerableAnim("open", OPEN_ANIMATION));
    }

    @Override
    public void triggerAnim(String controllerName, String animName) {
        WorldOfDragons.LOGGER.info("DOOR ANIM " + animName);
        if (!this.getWorld().isClient() && Objects.equals(animName, "open")) {
            WorldOfDragons.LOGGER.info("data true");
            dataTracker.set(IS_OPENED, true);
        }

        GeoEntity.super.triggerAnim(controllerName, animName);
    }

    //not so important things
    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(IS_OPENED, Boolean.FALSE);
    }

    @Override
    public boolean canBeHitByProjectile() {
        return false;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        dataTracker.set(IS_OPENED, nbt.getBoolean("IsOpened"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putBoolean("IsOpened", dataTracker.get(IS_OPENED));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}

package net.sn0wix_.worldofdragonsmod.entity.custom.misc;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class IronChestEntity extends ModChestEntity implements GeoEntity {
    private final RawAnimation OPEN_ANIMATION = RawAnimation.begin().thenPlayAndHold("animation.iron_chest.open");

    private AnimatableInstanceCache cache =  new SingletonAnimatableInstanceCache(this);

    public IronChestEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if (isOpened()){
            tAnimationState.setAndContinue(OPEN_ANIMATION);
        }

        if (tAnimationState.getController().getAnimationState() == AnimationController.State.PAUSED){
            animationTick = tAnimationState.getAnimationTick();
        }

        tAnimationState.getAnimationTick();

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
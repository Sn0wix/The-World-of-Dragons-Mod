package net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public abstract class GeoHostileEntity extends HostileEntity implements GeoEntity {
    protected final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    protected GeoHostileEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public double getSquaredMaxAttackDistance(LivingEntity entity) {
        return this.getWidth() * 2.0F * this.getWidth() * 2.0F + entity.getWidth();
    }

    public <T extends GeoAnimatable> PlayState walkIdlePredicate(AnimationState<T> state, RawAnimation walk, RawAnimation idle) {
        if (state.isMoving()) {
            return state.setAndContinue(walk);
        }

        return state.setAndContinue(idle);
    }
}

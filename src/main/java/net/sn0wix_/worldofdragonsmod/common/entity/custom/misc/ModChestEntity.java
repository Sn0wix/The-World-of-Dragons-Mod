package net.sn0wix_.worldofdragonsmod.common.entity.custom.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragonsMain;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class ModChestEntity extends Entity implements GeoEntity {
    private final RawAnimation OPEN_ANIMATION;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private final Identifier MODEL_RESOURCE;
    private final Identifier TEXTURE_RESOURCE;
    private final Identifier ANIMATION_RESOURCE;

    public ModChestEntity(EntityType<?> type, World world, RawAnimation animation, String model, String texture, String animationResource) {
        super(type, world);
        this.OPEN_ANIMATION = animation;
        this.MODEL_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, model);
        this.TEXTURE_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, texture);
        this.ANIMATION_RESOURCE = new Identifier(WorldOfDragonsMain.MOD_ID, animationResource);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        return super.interact(player, hand);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<ModChestEntity> modChestEntityAnimationState) {
        return isAlive() ? PlayState.CONTINUE : PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public Identifier getModelResource() {
        return MODEL_RESOURCE;
    }

    public Identifier getTextureResource() {
        return TEXTURE_RESOURCE;
    }

    public Identifier getAnimationResource() {
        return ANIMATION_RESOURCE;
    }

    @Override
    protected void initDataTracker() {}

    @Override
    public void pushAwayFrom(Entity entity) {}

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {}

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {}
}

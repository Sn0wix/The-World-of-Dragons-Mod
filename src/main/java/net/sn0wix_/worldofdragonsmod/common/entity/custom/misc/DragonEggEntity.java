package net.sn0wix_.worldofdragonsmod.common.entity.custom.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.dragons.ControllableDragonEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.Objects;

public class DragonEggEntity extends Entity implements GeoEntity {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public final Item item;
    public final EntityType<? extends ControllableDragonEntity> DRAGON_ENTITY_TYPE;

    public DragonEggEntity(EntityType<? extends Entity> type, World world, Item item, EntityType<? extends ControllableDragonEntity> dragon) {
        super(type, world);
        this.item = item;
        this.DRAGON_ENTITY_TYPE = dragon;
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!getWorld().isClient()) {
            //TODO add proper hatching system
            if (DRAGON_ENTITY_TYPE == null) {
                getWorld().spawnEntity(new ItemEntity(getWorld(), getX(), getY(), getZ(), new ItemStack(item)));
            } else if (source.getAttacker() instanceof PlayerEntity player){
                Objects.requireNonNull(DRAGON_ENTITY_TYPE.spawn((ServerWorld) getWorld(), getBlockPos(), SpawnReason.SPAWN_EGG)).setOwner(player);
            }
        }

        this.discard();
        return super.damage(source, amount);
    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(item);
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    public void pushAwayFrom(net.minecraft.entity.Entity entity) {
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}

package net.sn0wix_.worldofdragonsmod.common.entity.custom.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class ModChestEntity extends Entity implements GeoEntity {
    public static final TrackedData<Boolean> IS_OPENED = DataTracker.registerData(ModChestEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int openedFor = 0;
    private final int maxOpenTicks = 90;
    private final RawAnimation OPEN_ANIMATION;
    public final Identifier LOOT_TABLE;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public ModChestEntity(EntityType<?> type, World world, String animation) {
        super(type, world);
        this.OPEN_ANIMATION = RawAnimation.begin().thenPlayAndHold(animation);
        LOOT_TABLE = new Identifier(WorldOfDragons.MOD_ID, "chests/" + type.getName().getString());
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof ServerPlayerEntity entity) {
            interact(entity, entity.getActiveHand());
        }

        return false;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player instanceof ServerPlayerEntity && !dataTracker.get(IS_OPENED)) {
            setOpened();
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public void tick() {
        super.tick();

        if (openedFor > 0) {
            openedFor--;

            if (openedFor == 0) {
                kill();
            }
        }
    }

    public void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        try {
            if (!getWorld().isClient()) {
                LootTable lootTable = this.getWorld().getServer().getLootManager().getLootTable(LOOT_TABLE);
                LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld) this.getWorld()).add(LootContextParameters.THIS_ENTITY, this).add(LootContextParameters.ORIGIN, this.getPos()).add(LootContextParameters.DAMAGE_SOURCE, damageSource).addOptional(LootContextParameters.KILLER_ENTITY, damageSource.getAttacker()).addOptional(LootContextParameters.DIRECT_KILLER_ENTITY, damageSource.getSource());
                if (causedByPlayer && damageSource.getAttacker() instanceof PlayerEntity player) {
                    builder = builder.add(LootContextParameters.LAST_DAMAGE_PLAYER, player).luck(player.getLuck());
                }
                LootContextParameterSet lootContextParameterSet = builder.build(LootContextTypes.ENTITY);
                lootTable.generateLoot(lootContextParameterSet, random.nextLong(), this::dropStack);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    //Not so important things
    @Override
    public void kill() {
        super.kill();
        dropLoot(this.getDamageSources().genericKill(), true);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(IS_OPENED, false);
    }

    public void setOpened() {
        dataTracker.set(IS_OPENED, true);
        openedFor = maxOpenTicks;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        dataTracker.set(IS_OPENED, nbt.getBoolean("IsOpened"));
        openedFor = nbt.getInt("OpenedFor");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putBoolean("IsOpened", true);
        nbt.putInt("OpenedFor", openedFor);
    }


    //geckolib
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<ModChestEntity> modChestEntityAnimationState) {
        modChestEntityAnimationState.setAnimation(OPEN_ANIMATION);
        return dataTracker.get(IS_OPENED) ? PlayState.CONTINUE : PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}

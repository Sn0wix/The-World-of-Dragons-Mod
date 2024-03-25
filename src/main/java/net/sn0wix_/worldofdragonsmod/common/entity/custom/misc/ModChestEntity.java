package net.sn0wix_.worldofdragonsmod.common.entity.custom.misc;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.client.particle.packetDecoders.ChestBreakParticleDecoder;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.s2c.particles.PacketParticleTypes;
import org.jetbrains.annotations.Nullable;
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
    private final int dropLootAfter;
    private final int maxOpenedForTicks;
    private final RawAnimation OPEN_ANIMATION;
    public final Identifier LOOT_TABLE;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);


    public ModChestEntity(EntityType<? extends Entity> entityType, World world, String animation, int maxOpenedForTicks, int dropLootAfter) {
        super(entityType, world);
        this.OPEN_ANIMATION = RawAnimation.begin().thenPlayAndHold(animation);
        this.LOOT_TABLE = new Identifier(WorldOfDragons.MOD_ID, "chests/" + Registries.ENTITY_TYPE.getId(entityType).getPath());
        this.maxOpenedForTicks = maxOpenedForTicks;
        this.dropLootAfter = maxOpenedForTicks - dropLootAfter;
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

        if (!getWorld().isClient()) {
            if (openedFor > 0) {
                openedFor--;

                if (openedFor == 0) {
                    spawnParticles(256, false);
                    kill();
                }

                if (openedFor == dropLootAfter) {
                    spawnParticles(256, true);
                    dropLoot(this.getDamageSources().genericKill(), true);
                }

            } else if (openedFor < 0) {
                kill();
            }
        }
    }

    public void spawnParticles(int range, boolean puf) {
        if (!getWorld().isClient) {
            getWorld().getPlayers().forEach(player -> {
                if (player.isInRange(this, range)) {
                    ChestBreakParticleDecoder.sendToClient(this.getId(), (ServerPlayerEntity) player, puf, PacketParticleTypes.CHEST_BREAK);
                }
            });
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

    @Nullable
    @Override
    public ItemEntity dropStack(ItemStack stack, float yOffset) {
        if (stack.isEmpty()) {
            return null;
        }
        if (this.getWorld().isClient) {
            return null;
        }
        ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY() + (double) yOffset, this.getZ(), stack);
        itemEntity.setToDefaultPickupDelay();

        itemEntity.setVelocity(itemEntity.getVelocity());
        Direction direction = getHorizontalFacing();
        float i = 0.2f;

        if (direction == Direction.SOUTH) {
            itemEntity.setVelocity(itemEntity.getVelocity().add(0, 0, i));
        } else if (direction == Direction.NORTH) {
            itemEntity.setVelocity(itemEntity.getVelocity().add(0, 0, -i));
        } else if (direction == Direction.EAST) {
            itemEntity.setVelocity(itemEntity.getVelocity().add(i, 0, 0));
        } else if (direction == Direction.WEST) {
            itemEntity.setVelocity(itemEntity.getVelocity().add(-i, 0, 0));
        }

        this.getWorld().spawnEntity(itemEntity);
        return itemEntity;
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


    //Not so important things
    @Nullable
    @Override
    public ItemEntity dropStack(ItemStack stack) {
        return this.dropStack(stack, 0.5f);
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
        openedFor = maxOpenedForTicks;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        dataTracker.set(IS_OPENED, nbt.getBoolean("IsOpened"));
        openedFor = nbt.getInt("OpenedFor");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putBoolean("IsOpened", dataTracker.get(IS_OPENED));
        nbt.putInt("OpenedFor", openedFor);
    }
}

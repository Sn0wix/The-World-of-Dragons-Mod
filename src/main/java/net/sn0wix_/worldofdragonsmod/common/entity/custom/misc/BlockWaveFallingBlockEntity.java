package net.sn0wix_.worldofdragonsmod.common.entity.custom.misc;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.util.ModDamageSources;

public class BlockWaveFallingBlockEntity extends Entity {
    private BlockPos startingPos;

    public static final TrackedData<BlockState> BLOCK_STATE = DataTracker.registerData(BlockWaveFallingBlockEntity.class, TrackedDataHandlerRegistry.BLOCK_STATE);

    public BlockWaveFallingBlockEntity(EntityType<? extends BlockWaveFallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    public BlockWaveFallingBlockEntity spawn(World world, BlockPos blockPos, BlockState state) {
        world.spawnEntity(this);
        this.startingPos = blockPos.up();
        this.dataTracker.set(BLOCK_STATE, state);
        this.setVelocity(0,0.8,0);
        this.move(MovementType.SELF, getVelocity());
        return this;
    }

    @Override
    public void tick() {
        super.tick();

        if (startingPos != null && getBlockPos().equals(startingPos)) {
            this.kill();
        }

        //Gravity
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }

        this.move(MovementType.SELF, this.getVelocity());
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(BLOCK_STATE, Blocks.SAND.getDefaultState());
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.damage(ModDamageSources.of(getWorld(), ModDamageSources.BLOCK_WAVE), 1);
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("BlockState")) {
            dataTracker.set(BLOCK_STATE, NbtHelper.toBlockState(this.getWorld().createCommandRegistryWrapper(RegistryKeys.BLOCK), nbt.getCompound("BlockState")));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("BlockState", NbtHelper.fromBlockState(dataTracker.get(BLOCK_STATE)));
    }

    public BlockPos getStartingPos() {
        return startingPos == null ? new BlockPos(0,0,0) : startingPos;
    }
}

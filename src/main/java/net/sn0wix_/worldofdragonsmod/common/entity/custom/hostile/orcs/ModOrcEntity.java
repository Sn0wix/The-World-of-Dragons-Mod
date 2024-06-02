package net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.orcs;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.entity.ai.MMPathNavigateGround;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.GeoHostileEntity;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.BlockWaveFallingBlockEntity;
import net.sn0wix_.worldofdragonsmod.common.util.ModDamageSources;
import net.sn0wix_.worldofdragonsmod.common.world.biome.ModBiomes;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public abstract class ModOrcEntity extends GeoHostileEntity {
    private boolean infected = false;

    public ModOrcEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(4, new WanderAroundGoal(this, 1f));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));

        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, SnowGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PigEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, WanderingTraderEntity.class, true));
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isOf(ModDamageSources.BLOCK_WAVE)) {
            try {
                BlockWaveFallingBlockEntity entity = (BlockWaveFallingBlockEntity) source.getAttacker();
                if (entity.getOwner() instanceof ModOrcEntity) {
                    return false;
                }
            } catch (ClassCastException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return super.damage(source, amount);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    public boolean isInfected() {
        return this.getWorld().getBiome(getBlockPos()).matchesKey(ModBiomes.INFECTED_LANDS) || infected;
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new MMPathNavigateGround(this, world);
    }
}

package net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.playerNPC;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PlayerNPCEntity extends PathAwareEntity {
    public final Text NAME;

    public PlayerNPCEntity(EntityType<? extends PathAwareEntity> entityType, World world, Text name) {
        super(entityType, world);
        NAME = name;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(3, new WanderAroundGoal(this, 1f));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1f));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, (float) getAttributeBaseValue(EntityAttributes.GENERIC_FOLLOW_RANGE)));

        this.goalSelector.add(1, new MeleeAttackGoal(this, 1f, false));
        this.targetSelector.add(2, new RevengeGoal(this, PlayerNPCEntity.class));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, HostileEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl = super.tryAttack(target);
        if (bl) {
            swingHand(this.getActiveHand(), true);
        }
        return bl;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.tickHandSwing();
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }

    @Override
    public Text getDisplayName() {
        return NAME;
    }

    @Override
    public boolean shouldRenderName() {
        return true;
    }

    @Override
    protected Text getDefaultName() {
        return NAME;
    }

    @Nullable
    @Override
    public Text getCustomName() {
        return NAME;
    }
}

package net.sn0wix_.worldofdragonsmod.entity.custom.misc;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ModChestEntity extends PathAwareEntity {
    private boolean opened = false;
    public double animationTick = 0;

    protected ModChestEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    public void open(){
        opened = true;
    }
    public void close(){
        opened = false;
    }

    public boolean isOpened(){
        return opened;
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        if (this.isAlive()){
            open();
        }

        return super.interactAt(player, hitPos, hand);
    }

    @Override
    public void tick() {
        if (animationTick >= 85){
            this.kill();
        }
        super.tick();
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f);
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        open();
        super.onDamaged(damageSource);
    }

    /*@Override
    public void kill() {
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 255, 255, true, false, false));
    }*/
}

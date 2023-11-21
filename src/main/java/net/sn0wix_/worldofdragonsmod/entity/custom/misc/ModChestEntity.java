package net.sn0wix_.worldofdragonsmod.entity.custom.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMain;

public class ModChestEntity extends Entity {
    protected static final TrackedData<Boolean> OPENED = DataTracker.registerData(ModChestEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public ModChestEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public boolean damage(DamageSource source, float amount) {
        WorldOfDragonsMain.LOGGER.info("damage");
        /*if (source.isOf(DamageTypes.OUT_OF_WORLD)) {
            WorldOfDragonsMain.LOGGER.info("kill");
            this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
            this.discard();
            return true;
        }*/

        if (!world.isClient() && !dataTracker.get(OPENED)){
            dataTracker.set(OPENED, true);
            WorldOfDragonsMain.LOGGER.info("damage doneeeee");
            return true;
        }

        return false;
    }

    public void tick() {
        super.tick();
        WorldOfDragonsMain.LOGGER.info(String.valueOf(dataTracker.get(OPENED)));
    }

    public ActionResult interact(PlayerEntity player, Hand hand) {
        WorldOfDragonsMain.LOGGER.info("interact");
        if (!world.isClient() && !dataTracker.get(OPENED)) {
            WorldOfDragonsMain.LOGGER.info("interact doneeee");
            dataTracker.set(OPENED, true);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public void pushAwayFrom(Entity entity) {}

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(OPENED, false);
    }

    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Opened")) {
            this.dataTracker.set(OPENED, nbt.getBoolean("Opened"));
        }
    }

    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putBoolean("Opened", this.getDataTracker().get(OPENED));
    }
}

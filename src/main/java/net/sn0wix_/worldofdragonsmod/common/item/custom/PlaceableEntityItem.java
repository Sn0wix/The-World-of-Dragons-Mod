package net.sn0wix_.worldofdragonsmod.common.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

public class PlaceableEntityItem<T extends Entity> extends Item {
    public final EntityType<T> ENTITY_TYPE;

    public PlaceableEntityItem(Settings settings, EntityType<T> entityType) {
        super(settings);
        this.ENTITY_TYPE = entityType;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient() && context.getPlayer() != null) {
            try {
                Entity entity = ENTITY_TYPE.create((ServerWorld) context.getWorld(), context.getStack().getNbt(), null, context.getBlockPos().up(), SpawnReason.SPAWN_EGG, true, false);
                entity.setYaw(context.getPlayer().getHorizontalFacing().getOpposite().asRotation());
                context.getWorld().spawnEntity(entity);

                if (!context.getPlayer().isCreative()) {
                    context.getStack().decrement(1);
                }
                return ActionResult.SUCCESS;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }

        return ActionResult.PASS;
    }
}

package net.sn0wix_.worldofdragonsmod.common.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.ModChestEntity;

public class ChestItem extends Item {
    public final EntityType<ModChestEntity> ENTITY_TYPE;

    public ChestItem(Settings settings, EntityType<ModChestEntity> entityType) {
        super(settings);
        this.ENTITY_TYPE = entityType;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient() && context.getPlayer() != null) {
            ModChestEntity chest = ENTITY_TYPE.spawn((ServerWorld) context.getWorld(), context.getBlockPos().up(), SpawnReason.SPAWN_EGG);

            Direction direction = context.getPlayer().getHorizontalFacing();

            if (chest != null) {
                if (direction == Direction.SOUTH) {
                    chest.setYaw(-180);
                } else if (direction == Direction.NORTH) {
                    chest.setYaw(0);
                } else if (direction == Direction.EAST) {
                    chest.setYaw(90);
                } else if (direction == Direction.WEST) {
                    chest.setYaw(-90);
                }
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}

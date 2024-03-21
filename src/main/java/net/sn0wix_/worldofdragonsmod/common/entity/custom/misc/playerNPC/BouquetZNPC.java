package net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.playerNPC;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class BouquetZNPC extends PlayerNPCEntity {
    public BouquetZNPC(EntityType<? extends PathAwareEntity> entityType, World world) {
        super((EntityType<PathAwareEntity>) entityType, world, Text.of("BouquetZ"));
    }
}

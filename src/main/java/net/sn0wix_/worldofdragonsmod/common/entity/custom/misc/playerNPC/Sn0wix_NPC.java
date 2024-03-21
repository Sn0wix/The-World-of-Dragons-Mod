package net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.playerNPC;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class Sn0wix_NPC extends PlayerNPCEntity {
    public Sn0wix_NPC(EntityType<? extends PathAwareEntity> entityType, World world) {
        super((EntityType<PathAwareEntity>) entityType, world, Text.of("Sn0wix_"));
    }
}

package net.sn0wix_.worldofdragonsmod.common.blocks.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.blocks.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<MassiveDoorBlockEntity> MASSIVE_DOOR = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "massive_door"),
            FabricBlockEntityTypeBuilder.create(MassiveDoorBlockEntity::new, ModBlocks.MASSIVE_DOOR).build());


    public static void registerAllBlockEntities() {
        WorldOfDragons.LOGGER.info("Registering Block Entities for " + WorldOfDragons.MOD_ID);
    }
}

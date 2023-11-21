package net.sn0wix_.worldofdragonsmod.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMain;
import net.sn0wix_.worldofdragonsmod.blocks.entity.PickupBlockEntity;
import net.sn0wix_.worldofdragonsmod.blocks.entity.ShrekJukeboxBlockEntity;

public class ModBlockEntities {
    public static final BlockEntityType<ShrekJukeboxBlockEntity> SHREK_JUKEBOX_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(WorldOfDragonsMain.MOD_ID, "globe_tile_entity"),
            FabricBlockEntityTypeBuilder.create(ShrekJukeboxBlockEntity::new, ModBlocks.SHREK_JUKEBOX).build());

    public static final BlockEntityType<PickupBlockEntity> PICKUP_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(WorldOfDragonsMain.MOD_ID, "pickup_block_entity"),
            FabricBlockEntityTypeBuilder.create(PickupBlockEntity::new, ModBlocks.PICKUP_BLOCK).build());

    public static void registerModBlockEntities() {
        WorldOfDragonsMain.LOGGER.info("Registering block entities for " + WorldOfDragonsMain.MOD_ID);
    }
}

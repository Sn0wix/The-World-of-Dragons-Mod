package net.sn0wix_.worldofdragonsmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMod;
import net.sn0wix_.worldofdragonsmod.entity.custom.orcs.*;
import net.sn0wix_.worldofdragonsmod.entity.custom.misc.IronChestEntity;

public class ModEntities {
    //small bug with entity moving while done attacking but still playing animation
    public static final EntityType<ArmoredOrcEntity> ARMORED_ORC = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragonsMod.MOD_ID, "armored_orc"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArmoredOrcEntity::new).dimensions(EntityDimensions.fixed(1.3f, 2.5f)).build());

    public static final EntityType<GoblinEntity> GOBLIN = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragonsMod.MOD_ID, "goblin"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GoblinEntity::new).dimensions(EntityDimensions.fixed(1.1f, 2.1f)).build());

    public static final EntityType<ArcherOrcEntity> ARCHER_ORC = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragonsMod.MOD_ID, "archer_orc"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArcherOrcEntity::new).dimensions(EntityDimensions.fixed(1.3f, 2.2f)).build());

    public static final EntityType<FlyerOrcEntity> FLYER_ORC = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragonsMod.MOD_ID, "flyer_orc"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FlyerOrcEntity::new).dimensions(EntityDimensions.fixed(1.3f, 2.2f)).build());

    public static final EntityType<OrcBossEntity> ORC_BOSS = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragonsMod.MOD_ID, "orc_boss"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OrcBossEntity::new).dimensions(EntityDimensions.fixed(1.3f, 2.2f)).build());

    public static final EntityType<OrcBruteEntity> ORC_BRUTE = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragonsMod.MOD_ID, "orc_brute"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OrcBruteEntity::new).dimensions(EntityDimensions.fixed(1.3f, 2.2f)).build());

    public static final EntityType<SlasherOrcEntity> SLASHER_ORC = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragonsMod.MOD_ID, "slasher_orc"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SlasherOrcEntity::new).dimensions(EntityDimensions.fixed(1.3f, 3.1f)).build());

    public static final EntityType<OrcMageEntity> ORC_MAGE = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragonsMod.MOD_ID, "orc_mage"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OrcMageEntity::new).dimensions(EntityDimensions.fixed(1.3f, 2.2f)).build());

    /*public static final EntityType<OrcWargEntity> ORC_WARG = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragonsMod.MOD_ID, "orc_warg"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OrcWargEntity::new).dimensions(EntityDimensions.fixed(1.3f, 2.2f)).build());*/




    public static final EntityType<IronChestEntity> IRON_CHEST_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragonsMod.MOD_ID, "iron_chest"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, IronChestEntity::new).dimensions(EntityDimensions.fixed(2.2f, 2f)).build());

    public static void registerModEntities(){
        WorldOfDragonsMod.LOGGER.info("Registering entities for " + WorldOfDragonsMod.MOD_ID);
    }
}

package net.sn0wix_.worldofdragonsmod.common.entity;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.hostile.LavaElementalEntity;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.ExplodingCubeProjectile;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.ModChestEntity;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.playerNPC.BouquetZNPC;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.playerNPC.BubbaGump7NPC;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.playerNPC.Sn0wix_NPC;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.orcs.*;
import software.bernie.geckolib.core.animation.RawAnimation;

public class ModEntities {
    public static final EntityType<ArmoredOrcEntity> ARMORED_ORC = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "armored_orc"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArmoredOrcEntity::new).dimensions(EntityDimensions.fixed(1.3f, 2.5f)).build());

    public static final EntityType<GoblinEntity> GOBLIN = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "goblin"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GoblinEntity::new).dimensions(EntityDimensions.fixed(1.1f, 2.1f)).build());

    public static final EntityType<ArcherOrcEntity> ARCHER_ORC = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "archer_orc"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArcherOrcEntity::new).dimensions(EntityDimensions.fixed(1.3f, 3.3f)).build());

    public static final EntityType<FlyerOrcEntity> FLYER_ORC = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "flyer_orc"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FlyerOrcEntity::new).dimensions(EntityDimensions.fixed(1.3f, 2.2f)).build());

    public static final EntityType<OrcBossEntity> ORC_BOSS = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "orc_boss"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OrcBossEntity::new).dimensions(EntityDimensions.fixed(1.3f, 2.2f)).build());

    public static final EntityType<OrcBruteEntity> ORC_BRUTE = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "orc_brute"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OrcBruteEntity::new).dimensions(EntityDimensions.fixed(2.1f, 3.5f)).build());

    public static final EntityType<SlasherOrcEntity> SLASHER_ORC = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "slasher_orc"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SlasherOrcEntity::new).dimensions(EntityDimensions.fixed(1.3f, 3.1f)).build());

    public static final EntityType<OrcMageEntity> ORC_MAGE = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "orc_mage"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OrcMageEntity::new).dimensions(EntityDimensions.fixed(1.1f, 2.6f)).build());

    /*public static final EntityType<OrcWargEntity> ORC_WARG = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragonsMod.MOD_ID, "orc_warg"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, OrcWargEntity::new).dimensions(EntityDimensions.fixed(1.3f, 2.2f)).build());*/

    public static final EntityType<LavaElementalEntity> LAVA_ELEMENTAL = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "lava_elemental"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, LavaElementalEntity::new).dimensions(EntityDimensions.fixed(3f, 4.4f)).build());


    public static final EntityType<Sn0wix_NPC> SN0WIX_ = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "sn0wix_"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, Sn0wix_NPC::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());

    public static final EntityType<BubbaGump7NPC> BUBBAGUMP7 = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "bubbagump7"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BubbaGump7NPC::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());

    public static final EntityType<BouquetZNPC> BOUQUETZ = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "bouquetz"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BouquetZNPC::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());


    public static final EntityType<ModChestEntity> IRON_CHEST_ENTITY = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "iron_chest"),
            getModChestEntityType((type, world) -> new ModChestEntity(type, world, "animation.iron_chest.open")));

    public static final EntityType<ExplodingCubeProjectile> EXPLODING_MAGMA_PROJECTILE = Registry.register(Registries.ENTITY_TYPE, new Identifier(WorldOfDragons.MOD_ID, "exploding_magma_projectile"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ExplodingCubeProjectile::new).dimensions(EntityDimensions.fixed(1f, 1f)).fireImmune().build());

    public static void registerModEntities() {
        WorldOfDragons.LOGGER.info("Registering entities for " + WorldOfDragons.MOD_ID);
    }

    private static EntityType<ModChestEntity> getModChestEntityType(EntityType.EntityFactory<ModChestEntity> factory) {
        return new EntityType<>(factory, SpawnGroup.MISC, true, true, false, true, ImmutableSet.of(), EntityDimensions.fixed(1f, 1f), 5, 3, FeatureFlags.VANILLA_FEATURES);
    }
}

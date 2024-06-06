package net.sn0wix_.worldofdragonsmod.common.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.blocks.ModBlocks;
import net.sn0wix_.worldofdragonsmod.common.entity.ModEntities;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.dragons.TameableDragonEntity;
import net.sn0wix_.worldofdragonsmod.common.item.custom.*;
import net.sn0wix_.worldofdragonsmod.common.sounds.ModSounds;

public class ModItems {
    //Spawn Eggs
    public static final Item ARMORED_ORC_SPAWN_EGG = registerSpawnEgg(ModEntities.ARMORED_ORC, "058602", "777777");
    public static final Item GOBLIN_SPAWN_EGG = registerSpawnEgg(ModEntities.GOBLIN, "058602", "744d01");
    public static final Item ORC_BRUTE_SPAWN_EGG = registerSpawnEgg(ModEntities.ORC_BRUTE, "007317", "565656");
    public static final Item SLASHER_ORC_SPAWN_EGG = registerSpawnEgg(ModEntities.SLASHER_ORC, "026c17", "8a8a8a");
    public static final Item ORC_WARG_SPAWN_EGG = registerSpawnEgg(ModEntities.ORC_WARG, "027423", "028728");
    public static final Item ORC_MAGE_SPAWN_EGG = registerSpawnEgg(ModEntities.ORC_MAGE, "078400", "ffffff");
    public static final Item SNAPPER_SPAWN_EGG = registerSpawnEgg(ModEntities.SNAPPER, "614870", "b49741");


    //Chests
    public static final Item COMMON_CHEST = registerItem("common_chest", new PlaceableEntityItem<>(new FabricItemSettings().maxCount(1), ModEntities.COMMON_CHEST));
    public static final Item IRON_CHEST = registerItem("iron_chest", new PlaceableEntityItem<>(new FabricItemSettings().maxCount(1), ModEntities.IRON_CHEST));
    public static final Item GOLDEN_CHEST = registerItem("golden_chest", new PlaceableEntityItem<>(new FabricItemSettings().maxCount(1), ModEntities.GOLDEN_CHEST));

    //Dragon egg items
    public static final Item SHELL_SMASHER_EGG = registerDragonEgg("shell_smasher_egg", ModEntities.SHELL_SMASHER_EGG);
    public static final Item FINFLAPPER_EGG = registerDragonEgg("finflapper_egg", ModEntities.FINFLAPPER_EGG);
    public static final Item NAGA_EGG = registerDragonEgg("naga_egg", ModEntities.NAGA_EGG);
    public static final Item RALAK_EGG = registerDragonEgg("ralak_egg", ModEntities.RALAK_EGG);
    public static final Item SANDSPITTER_EGG = registerDragonEgg("sandspitter_egg", ModEntities.SANDSPITTER_EGG);
    public static final Item SKYSERPENT_EGG = registerDragonEgg("skyserpent_egg", ModEntities.SKYSERPENT_EGG);
    public static final Item VOLCANIC_WINGWALKER_EGG = registerDragonEgg("volcanic_wingwalker_egg", ModEntities.VOLCANIC_WINGWALKER_EGG);


    //Other entity items
    public static final Item MASSIVE_DOOR = registerItem("door", new BlockItem(ModBlocks.MASSIVE_DOOR, new FabricItemSettings().maxCount(1)));


    public static final Item RESISTANCE_UPGRADE = registerItem("resistance_upgrade", new Item(new FabricItemSettings().maxCount(16)));
    public static final Item HEATH_UPGRADE = registerItem("health_upgrade", new Item(new FabricItemSettings().maxCount(16)));
    public static final Item DAMAGE_UPGRADE = registerItem("damage_upgrade", new Item(new FabricItemSettings().maxCount(16).maxDamage(3)));
    public static final Item SPEED_UPGRADE = registerItem("speed_upgrade", new Item(new FabricItemSettings().maxCount(16)));
    public static final Item FIRE_UPGRADE = registerItem("fire_upgrade", new Item(new FabricItemSettings().maxCount(16)));
    public static final Item STAMINA_UPGRADE = registerItem("stamina_upgrade", new Item(new FabricItemSettings().maxCount(16)));


    public static final Item BIG_EMERALD = registerItem("big_emerald", new FakeEnchantedItem(new FabricItemSettings()));
    public static final Item FROZEN_FISH = registerItem("frozen_fish", new Item(new FabricItemSettings()));
    public static final Item CHARGE_BATTERY = registerItem("charge_battery", new ChargeBatteryItem(ModToolMaterial.ELECTRIC, 0.0f, 1.8f, new FabricItemSettings().maxCount(1)));
    public static final Item BOTTLE_OF_ELECTRIC_CHARGE = registerItem("bottle_of_electric_charge", new Item(new FabricItemSettings().maxCount(16)));
    public static final Item SLAB_OF_MEAT = registerItem("slab_of_meat", new Item(new FabricItemSettings()));
    public static final Item FISH_TREAT = registerItem("fish_treat", new Item(new FabricItemSettings()));
    public static final Item FROZEN_MEAT = registerItem("frozen_meat", new Item(new FabricItemSettings()));
    public static final Item THE_ROCK = registerItem("rock", new ThrowableRockItem(new FabricItemSettings(), false));
    public static final Item BLOOD = registerItem("blood", new Item(new FabricItemSettings()));
    public static final Item BURNT_MEAT = registerItem("burnt_meat", new Item(new FabricItemSettings()));
    public static final Item METALLIC_SUBSTANCE = registerItem("metallic_substance", new Item(new FabricItemSettings()));
    public static final Item NAGA_VENOM = registerItem("naga_venom", new Item(new FabricItemSettings()));
    public static final Item CONDUCTIVE_METAL = registerItem("conductive_metal", new Item(new FabricItemSettings()));
    public static final Item GHOST_GEL = registerItem("ghost_gel", new Item(new FabricItemSettings()));
    public static final Item MEAT = registerItem("meat", new Item(new FabricItemSettings()));
    public static final Item LAVA_ROCK = registerItem("lava_rock", new ThrowableRockItem(new FabricItemSettings(), true));
    public static final Item WITHERED_MEAT = registerItem("withered_meat", new Item(new FabricItemSettings()));
    public static final Item BUNDLE_OF_SEA_WHEAT = registerItem("bundle_of_sea_wheat", new Item(new FabricItemSettings()));
    public static final Item POWDERED_SAND = registerItem("powdered_sand", new Item(new FabricItemSettings()));
    public static final Item FISH_SNACK = registerItem("fish_snack", new Item(new FabricItemSettings()));
    public static final Item BOTTLE_OF_MIST = registerItem("bottle_of_mist", new Item(new FabricItemSettings().maxCount(16)));
    public static final Item PROCESSED_MEAT = registerItem("processed_meat", new Item(new FabricItemSettings()));
    public static final Item LEACH = registerItem("leach", new Item(new FabricItemSettings()));


    public static final Item SCRAP = registerItem("scrap", new Item(new FabricItemSettings()));
    public static final Item DRAGON_SCALES = registerItem("dragon_scales", new Item(new FabricItemSettings()));
    public static final Item DRAGON_TOOTH = registerItem("dragon_tooth", new Item(new FabricItemSettings()));
    public static final Item DRAGON_MEMBRANE = registerItem("dragon_membrane", new Item(new FabricItemSettings()));
    public static final Item DRAGON_BONE = registerItem("dragon_bone", new Item(new FabricItemSettings()));
    public static final Item DRAGON_HEART = registerItem("dragon_heart", new Item(new FabricItemSettings()));
    public static final Item DRAGON_SADDLE = registerItem("dragon_saddle", new Item(new FabricItemSettings()));
    public static final Item DRAGON_WINGS = registerItem("dragon_wings", new Item(new FabricItemSettings()));
    public static final Item DRAGON_NECTAR = registerItem("dragon_nectar", new DragonNectarItem(new FabricItemSettings().food(FoodComponents.ENCHANTED_GOLDEN_APPLE)));
    public static final Item DRAGON_BLOOD = registerItem("dragon_blood", new DragonBloodItem(new FabricItemSettings().food(FoodComponents.GOLDEN_APPLE)));
    public static final Item SIMPLE_SWORD = registerItem("simple_sword", new SwordItem(ToolMaterials.WOOD, 4, -2.4F, new FabricItemSettings()));
    public static final Item FLAWLESS_SWORD = registerItem("flawless_sword", new SwordItem(ToolMaterials.STONE, 5, -2.4F, new FabricItemSettings()));
    public static final Item STEEL_SWORD = registerItem("steel_sword", new SwordItem(ToolMaterials.IRON, 6, -2.4F, new FabricItemSettings()));
    public static final Item RUINIC_SWORD = registerItem("ruinic_sword", new SwordItem(ToolMaterials.DIAMOND, 7, -2.4F, new FabricItemSettings()));
    public static final Item DRAGON_STEEL_SWORD = registerItem("dragon_steel_sword", new SwordItem(ToolMaterials.NETHERITE, 5, -2.4F, new FabricItemSettings()));
    public static final Item SIMPLE_AXE = registerItem("simple_axe", new ModAxeItem(ToolMaterials.WOOD, 7, -3.2F, new FabricItemSettings()));
    public static final Item FLAWLESS_AXE = registerItem("flawless_axe", new ModAxeItem(ToolMaterials.STONE, 9, -3.2F, new FabricItemSettings()));
    public static final Item STEEL_AXE = registerItem("steel_axe", new ModAxeItem(ToolMaterials.IRON, 9, -3.1F, new FabricItemSettings()));
    public static final Item RUINIC_AXE = registerItem("ruinic_axe", new ModAxeItem(ToolMaterials.DIAMOND, 9, -3.0F, new FabricItemSettings()));
    public static final Item DRAGON_STEEL_AXE = registerItem("dragon_steel_axe", new ModAxeItem(ToolMaterials.NETHERITE, 10, -3.0F, new FabricItemSettings()));
    public static final Item DRAGON_TREAT = registerItem("dragon_treat", new Item(new FabricItemSettings()));
    public static final Item MUSIC_DISC_SHREK = registerItem("music_disc_shrek", new MusicDiscItem(15, ModSounds.SHREK, new FabricItemSettings().maxCount(1), 200));


    private static Item registerItem(String name, Item item) {
        Item item1 = Registry.register(Registries.ITEM, new Identifier(WorldOfDragons.MOD_ID, name), item);
        ModItemGroup.addToGroup(item);
        return item1;
    }

    private static Item registerDragonEgg(String name, EntityType<? extends Entity> eggEntity) {
        Item item1 = Registry.register(Registries.ITEM, new Identifier(WorldOfDragons.MOD_ID, name), new DragonEggItem<>(new FabricItemSettings().maxCount(1), "misc/eggs/" + name, eggEntity));
        ModItemGroup.addEgg(item1);
        return item1;
    }

    private static Item registerSpawnEgg(EntityType<? extends MobEntity> type, String hexPrimary, String hexSecondary) {
        Item item1 = Registry.register(Registries.ITEM, new Identifier(WorldOfDragons.MOD_ID, type.getUntranslatedName() + "_spawn_egg"),
                new SpawnEggItem(type, Integer.valueOf(hexPrimary, 16), Integer.valueOf(hexSecondary, 16), new FabricItemSettings()));
        ModItemGroup.addEgg(item1);
        return item1;
    }

    public static void registerModItems() {
        WorldOfDragons.LOGGER.info("Registering Mod Items for " + WorldOfDragons.MOD_ID);
    }
}

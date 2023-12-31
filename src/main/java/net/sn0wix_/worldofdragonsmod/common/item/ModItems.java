package net.sn0wix_.worldofdragonsmod.common.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragonsMain;
import net.sn0wix_.worldofdragonsmod.common.item.custom.*;
import net.sn0wix_.worldofdragonsmod.common.sounds.ModSounds;

public class ModItems {

    public static final Item COMMON_CHEST_GENERATOR = registerItem("common_chest_generator",new CommonChestGeneratorItem(
            new FabricItemSettings().maxCount(1)));

    public static final Item RARE_CHEST_GENERATOR = registerItem("rare_chest_generator",new RareChestGeneratorItem(
            new FabricItemSettings().maxCount(1)));

    public static final Item ARMOR_UPGRADE = registerItem("armor_upgrade",new Item(new FabricItemSettings().maxCount(1)));
    public static final Item DAMAGE_UPGRADE = registerItem("damage_upgrade",new Item(new FabricItemSettings().maxCount(1).maxDamage(4)));
    public static final Item SPEED_UPGRADE = registerItem("speed_upgrade",new Item(new FabricItemSettings().maxCount(1)));
    public static final Item FIRE_UPGRADE = registerItem("fire_upgrade",new Item(new FabricItemSettings().maxCount(1)));
    public static final Item STAMINA_UPGRADE = registerItem("stamina_upgrade",new Item(new FabricItemSettings().maxCount(1)));


    public static final Item BIG_EMERALD = registerItem("big_emerald",new BigEmeraldItem(new FabricItemSettings()));
    public static final Item FROZEN_FISH = registerItem("frozen_fish",new Item(new FabricItemSettings()));
    public static final Item CHARGE_BATTERY = registerItem("charge_battery",new ChargeBatteryItem(ModToolMaterial.ELECTRIC,0.0f,1.8f,new FabricItemSettings().maxCount(1)));
    public static final Item BOTTLE_OF_ELECTRIC_CHARGE = registerItem("bottle_of_electric_charge",new Item(new FabricItemSettings().maxCount(16)));
    public static final Item SLAB_OF_MEAT = registerItem("slab_of_meat",new Item(new FabricItemSettings()));
    public static final Item UNKNOWN_FISH = registerItem("unknown_fish",new Item(new FabricItemSettings()));
    public static final Item FROZEN_MEAT = registerItem("frozen_meat",new Item(new FabricItemSettings()));
    public static final Item ROCK = registerItem("rock",new Item(new FabricItemSettings()));
    public static final Item BLOOD = registerItem("blood",new Item(new FabricItemSettings()));
    public static final Item BURNT_MEAT = registerItem("burnt_meat",new Item(new FabricItemSettings()));
    public static final Item METALLIC_SUBSTANCE = registerItem("metallic_substance",new Item(new FabricItemSettings()));
    public static final Item NAGA_VENOM = registerItem("naga_venom",new Item(new FabricItemSettings()));
    public static final Item CONDUCTIVE_METAL = registerItem("conductive_metal",new Item(new FabricItemSettings()));
    public static final Item GHOST_GEL = registerItem("ghost_gel",new Item(new FabricItemSettings()));
    public static final Item MEAT = registerItem("meat",new Item(new FabricItemSettings()));
    public static final Item LAVA_ROCK = registerItem("lava_rock",new Item(new FabricItemSettings()));
    public static final Item WITHERED_MEAT = registerItem("red_meat",new Item(new FabricItemSettings()));
    public static final Item BUNDLE_OF_SEA_WHEAT = registerItem("bundle_of_sea_wheat",new Item(new FabricItemSettings()));
    public static final Item POWDERED_SAND = registerItem("powdered_sand",new Item(new FabricItemSettings()));
    public static final Item COMMON_LOOT = registerItem("common_loot",new Item(new FabricItemSettings()));
    public static final Item RARE_LOOT = registerItem("rare_loot",new Item(new FabricItemSettings()));
    public static final Item FISH_SNACK = registerItem("fish_snack",new Item(new FabricItemSettings()));
    public static final Item BOTTLE_OF_MIST = registerItem("bottle_of_mist",new Item(new FabricItemSettings().maxCount(16)));
    public static final Item PROCESSED_MEAT = registerItem("processed_meat",new Item(new FabricItemSettings()));
    public static final Item LEACH = registerItem("leach",new Item(new FabricItemSettings()));



    public static final Item SCRAP = registerItem("scrap",new Item(new FabricItemSettings()));
    public static final Item DRAGON_SCALES = registerItem("dragon_scales",new Item(new FabricItemSettings()));
    public static final Item DRAGON_TOOTH = registerItem("dragon_tooth",new Item(new FabricItemSettings()));
    public static final Item DRAGON_MEMBRANE = registerItem("dragon_membrane",new Item(new FabricItemSettings()));
    public static final Item DRAGON_BONE = registerItem("dragon_bone",new Item(new FabricItemSettings()));
    public static final Item DRAGON_HEART = registerItem("dragon_heart",new Item(new FabricItemSettings()));
    public static final Item DRAGON_SADDLE = registerItem("dragon_saddle",new Item(new FabricItemSettings()));
    public static final Item DRAGON_WINGS = registerItem("dragon_wings",new Item(new FabricItemSettings()));
    public static final Item DRAGON_NECTAR = registerItem("dragon_nectar",new DragonNectarItem(new FabricItemSettings().food(FoodComponents.ENCHANTED_GOLDEN_APPLE)));
    public static final Item DRAGON_BLOOD = registerItem("dragon_blood",new DragonBloodItem(new FabricItemSettings().food(FoodComponents.GOLDEN_APPLE)));
    public static final Item SIMPLE_SWORD = registerItem("simple_sword",new SwordItem(ToolMaterials.WOOD,4,-2.4F,new FabricItemSettings()));
    public static final Item FLAWLESS_SWORD = registerItem("flawless_sword",new SwordItem(ToolMaterials.STONE,5,-2.4F,new FabricItemSettings()));
    public static final Item STEEL_SWORD = registerItem("steel_sword",new SwordItem(ToolMaterials.IRON,6,-2.4F,new FabricItemSettings()));
    public static final Item RUINIC_SWORD = registerItem("ruinic_sword",new SwordItem(ToolMaterials.DIAMOND,7,-2.4F,new FabricItemSettings()));
    public static final Item DRAGON_STEEL_SWORD = registerItem("dragon_steel_sword",new SwordItem(ToolMaterials.NETHERITE,5,-2.4F,new FabricItemSettings()));
    public static final Item SIMPLE_AXE = registerItem("simple_axe",new ModAxeItem(ToolMaterials.WOOD,7,-3.2F,new FabricItemSettings()));
    public static final Item FLAWLESS_AXE = registerItem("flawless_axe",new ModAxeItem(ToolMaterials.STONE,9,-3.2F,new FabricItemSettings()));
    public static final Item STEEL_AXE = registerItem("steel_axe",new ModAxeItem(ToolMaterials.IRON,9,-3.1F,new FabricItemSettings()));
    public static final Item RUINIC_AXE = registerItem("ruinic_axe",new ModAxeItem(ToolMaterials.DIAMOND,9,-3.0F,new FabricItemSettings()));
    public static final Item DRAGON_STEEL_AXE = registerItem("dragon_steel_axe",new ModAxeItem(ToolMaterials.NETHERITE,10,-3.0F,new FabricItemSettings()));
    public static final Item DRAGON_TREAT = registerItem("dragon_treat",new Item(new FabricItemSettings()));
    public static final Item MUSIC_DISC_SHREK = registerItem("music_disc_shrek",new MusicDiscItem(15, ModSounds.SHREK, new FabricItemSettings().maxCount(1),200));


    private static Item registerItem(String name,Item item){
        Item item1 = Registry.register(Registries.ITEM,new Identifier(WorldOfDragonsMain.MOD_ID,name),item);
        addToItemGroup(item1);
        return item1;
    }

    public static void registerModItems(){
        WorldOfDragonsMain.LOGGER.info("Registering Mod Items for " + WorldOfDragonsMain.MOD_ID);
    }

    private static void addToItemGroup(Item item){
        ItemGroupEvents.modifyEntriesEvent(ModItemGroup.WORLD_OF_DRAGONS_ITEM_GROUP).register(entries -> entries.add(item));
    }
}

package net.sn0wix_.worldofdragonsmod.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMod;
import net.sn0wix_.worldofdragonsmod.blocks.custom.ChargeBlock;
import net.sn0wix_.worldofdragonsmod.blocks.custom.PickupBlock;
import net.sn0wix_.worldofdragonsmod.item.ModItemGroup;

public class ModBlocks {

    public static final Block PICKUP_BLOCK = registerBlock("pickup_block",
            new PickupBlock(FabricBlockSettings.of(Material.WOOD).noCollision()),ModItemGroup.WORLD_OF_DRAGONS_ITEM_GROUP);

    public static final Block XRAY_BLOCK = registerBlock("xray_block",
            new Block(FabricBlockSettings.of(Material.BARRIER)),ModItemGroup.WORLD_OF_DRAGONS_ITEM_GROUP);

    public static final Block CHARGE_BLOCK = registerBlock("charge_block",
            new ChargeBlock(FabricBlockSettings.of(Material.STONE)),ModItemGroup.WORLD_OF_DRAGONS_ITEM_GROUP);

    private static Block registerBlock(String name, Block block, ItemGroup itemGroup){
        registerBlockItem(name, block, itemGroup);
        return Registry.register(Registries.BLOCK, new Identifier(WorldOfDragonsMod.MOD_ID, name),block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup itemGroup){
        Item item = Registry.register(Registries.ITEM, new Identifier(WorldOfDragonsMod.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(item));
        return item;
    }

    public static void registerModBlocks(){
        WorldOfDragonsMod.LOGGER.info("Registerion Mod Blocks for " + WorldOfDragonsMod.MOD_ID);
    }
}

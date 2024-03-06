package net.sn0wix_.worldofdragonsmod.common.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragonsMain;
import net.sn0wix_.worldofdragonsmod.common.blocks.custom.ChargeBlock;
import net.sn0wix_.worldofdragonsmod.common.item.ModItemGroup;

public class ModBlocks {
    public static final Block XRAY_BLOCK = registerBlock("xray_block",
            new Block(FabricBlockSettings.copyOf(Blocks.BARRIER)));

    public static final Block CHARGE_BLOCK = registerBlock("charge_block",
            new ChargeBlock(FabricBlockSettings.copy(Blocks.STONE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(WorldOfDragonsMain.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, new Identifier(WorldOfDragonsMain.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
        ModItemGroup.addToGroup(item);
    }

    public static void registerModBlocks() {
        WorldOfDragonsMain.LOGGER.info("Registerion Mod Blocks for " + WorldOfDragonsMain.MOD_ID);
    }
}

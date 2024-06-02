package net.sn0wix_.worldofdragonsmod.common.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.blocks.custom.ChargeBlock;
import net.sn0wix_.worldofdragonsmod.common.blocks.custom.MassiveDoorBlock;
import net.sn0wix_.worldofdragonsmod.common.item.ModItemGroup;

public class ModBlocks {
    public static final Block CHARGE_BLOCK = registerBlock("charge_block", new ChargeBlock(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block MASSIVE_DOOR = registerBlockWithoutItem("massive_door", new MassiveDoorBlock(AbstractBlock.Settings.create().nonOpaque().noBlockBreakParticles()));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(WorldOfDragons.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(WorldOfDragons.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, new Identifier(WorldOfDragons.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
        ModItemGroup.addToGroup(item);
    }

    public static void registerModBlocks() {
        WorldOfDragons.LOGGER.info("Registering Mod Blocks for " + WorldOfDragons.MOD_ID);
    }
}

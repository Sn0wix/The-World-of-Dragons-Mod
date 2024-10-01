package net.sn0wix_.worldofdragonsmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.sn0wix_.worldofdragonsmod.common.item.ModItems;

public class ModModelGenerator extends FabricModelProvider {
    public ModModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.COMMON_CHEST, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_CHEST, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLDEN_CHEST, Models.GENERATED);
        itemModelGenerator.register(ModItems.MASSIVE_DOOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.HEATH_UPGRADE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FISH_TREAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.RESISTANCE_UPGRADE, Models.GENERATED);
        itemModelGenerator.register(ModItems.JONAS_COIN, Models.GENERATED);
    }
}

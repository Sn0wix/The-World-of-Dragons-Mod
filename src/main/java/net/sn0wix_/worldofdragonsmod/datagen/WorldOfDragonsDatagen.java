package net.sn0wix_.worldofdragonsmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.sn0wix_.worldofdragonsmod.datagen.ModModelGenerator;
import net.sn0wix_.worldofdragonsmod.datagen.ModTagsGenerator;

public class WorldOfDragonsDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelGenerator::new);
		pack.addProvider(ModTagsGenerator.ItemGenerator::new);
	}
}

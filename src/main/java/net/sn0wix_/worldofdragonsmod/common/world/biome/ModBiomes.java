package net.sn0wix_.worldofdragonsmod.common.world.biome;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.TheNetherBiomeCreator;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;

public class ModBiomes {
    //Rename to death island
    public static final RegistryKey<Biome> INFECTED_LANDS = RegistryKey.of(RegistryKeys.BIOME, new Identifier(WorldOfDragons.MOD_ID, "infected_lands"));

    public static void bootstrap(Registerable<Biome> context) {
        RegistryEntryLookup<PlacedFeature> featureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntryLookup<ConfiguredCarver<?>> carverRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);

        context.register(INFECTED_LANDS, TheNetherBiomeCreator.createBasaltDeltas(featureRegistryEntryLookup, carverRegistryEntryLookup));
    }
}

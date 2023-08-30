package net.sn0wix_.worldofdragonsmod;

import net.fabricmc.api.ModInitializer;
import net.sn0wix_.worldofdragonsmod.blocks.ModBlocks;
import net.sn0wix_.worldofdragonsmod.effect.ModEffects;
import net.sn0wix_.worldofdragonsmod.entity.ModEntities;
import net.sn0wix_.worldofdragonsmod.item.ModItems;
import net.sn0wix_.worldofdragonsmod.sounds.ModSounds;
import net.sn0wix_.worldofdragonsmod.util.ModRegisteries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class WorldOfDragonsMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(WorldOfDragonsMod.MOD_ID);
	public static final String MOD_ID= "worldofdragonsmod";


	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEffects.registerEffects();
		ModSounds.registerModSounds();

		ModRegisteries.registerModStuffs();

		ModEntities.registerModEntities();

		GeckoLib.initialize();
	}
}

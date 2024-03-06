package net.sn0wix_.worldofdragonsmod.common;

import net.fabricmc.api.ModInitializer;
import net.sn0wix_.worldofdragonsmod.common.blocks.ModBlocks;
import net.sn0wix_.worldofdragonsmod.common.config.Config;
import net.sn0wix_.worldofdragonsmod.common.config.ConfigFile;
import net.sn0wix_.worldofdragonsmod.common.effect.ModEffects;
import net.sn0wix_.worldofdragonsmod.common.entity.ModEntities;
import net.sn0wix_.worldofdragonsmod.common.item.ModItemGroup;
import net.sn0wix_.worldofdragonsmod.common.item.ModItems;
import net.sn0wix_.worldofdragonsmod.common.networking.ModPackets;
import net.sn0wix_.worldofdragonsmod.common.particle.ModParticles;
import net.sn0wix_.worldofdragonsmod.common.sounds.ModSounds;
import net.sn0wix_.worldofdragonsmod.common.util.ModRegisteries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class WorldOfDragonsMain implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(WorldOfDragonsMain.MOD_ID);
    public static final String MOD_ID = "worldofdragonsmod";
    public static Config CONFIG;

    @Override
    public void onInitialize() {
        if (ConfigFile.checkConfig()) {
            CONFIG = ConfigFile.readConfig();
        } else {
            CONFIG = new Config();
        }

        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModEffects.registerEffects();
        ModSounds.registerModSounds();
        ModRegisteries.registerModStuffs();
        ModEntities.registerModEntities();
        ModParticles.registerParticles();
        ModPackets.registerC2SPackets();
        ModItemGroup.registerItemGroup();

        GeckoLib.initialize();
    }
}

package net.sn0wix_.worldofdragonsmod.common;

import net.fabricmc.api.ModInitializer;
import net.sn0wix_.worldofdragonsmod.common.blocks.ModBlocks;
import net.sn0wix_.worldofdragonsmod.common.blocks.entity.ModBlockEntities;
import net.sn0wix_.worldofdragonsmod.common.effect.ModEffects;
import net.sn0wix_.worldofdragonsmod.common.entity.ModEntities;
import net.sn0wix_.worldofdragonsmod.common.events.ServerEvents;
import net.sn0wix_.worldofdragonsmod.common.item.ModItemGroup;
import net.sn0wix_.worldofdragonsmod.common.item.ModItems;
import net.sn0wix_.worldofdragonsmod.common.networking.ModPackets;
import net.sn0wix_.worldofdragonsmod.common.particle.ModParticles;
import net.sn0wix_.worldofdragonsmod.common.sounds.ModSounds;
import net.sn0wix_.worldofdragonsmod.common.util.ModRegisteries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class WorldOfDragons implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(WorldOfDragons.MOD_ID);
    public static final String MOD_ID = "worldofdragonsmod";

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModBlockEntities.registerAllBlockEntities();
        ModEffects.registerEffects();
        ModSounds.registerModSounds();
        ModRegisteries.registerModStuffs();
        ModEntities.registerModEntities();
        ModParticles.registerParticles();
        ModPackets.registerC2SPackets();
        ModItemGroup.registerItemGroup();
        ServerEvents.registerEvents();

        GeckoLib.initialize();
    }
}

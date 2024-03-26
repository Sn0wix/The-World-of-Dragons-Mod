package net.sn0wix_.worldofdragonsmod.common.events.tick;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.sn0wix_.worldofdragonsmod.common.util.blockWaves.BlockWaves;

public class ServerEndTickEvent implements ServerTickEvents.EndTick {
    @Override
    public void onEndTick(MinecraftServer server) {
        BlockWaves.tickWaves();
    }
}

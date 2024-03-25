package net.sn0wix_.worldofdragonsmod.common.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.sn0wix_.worldofdragonsmod.common.events.tick.ServerEndTickEvent;

public class ServerEvents {
    public static void registerEvents() {
        ServerTickEvents.END_SERVER_TICK.register(new ServerEndTickEvent());
    }
}

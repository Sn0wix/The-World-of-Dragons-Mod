package net.sn0wix_.worldofdragonsmod.client.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.sn0wix_.worldofdragonsmod.client.events.tick.ClientEndTickEvent;

public class ClientEvents {
    public static void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(new ClientEndTickEvent());
    }
}

package net.sn0wix_.worldofdragonsmod.common.networking;


import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.s2c.particles.SpawnParticlesPacket;

public class ModPackets {
    public static final Identifier SPAWN_PARTICLES = new Identifier(WorldOfDragons.MOD_ID, "spawn_particles");


    public static void registerC2SPackets(){

    }

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(SPAWN_PARTICLES, SpawnParticlesPacket::receive);
    }
}

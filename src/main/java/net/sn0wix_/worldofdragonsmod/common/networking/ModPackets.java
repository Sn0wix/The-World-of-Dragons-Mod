package net.sn0wix_.worldofdragonsmod.common.networking;


import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragonsMain;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.SpawnExplodeParticlesPacket;

public class ModPackets {
    public static final Identifier EXPLODING_CUBE_PARTICLES = new Identifier(WorldOfDragonsMain.MOD_ID, "exploding_cube_particles");


    public static void registerC2SPackets(){

    }

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(EXPLODING_CUBE_PARTICLES, SpawnExplodeParticlesPacket::receive);
    }
}

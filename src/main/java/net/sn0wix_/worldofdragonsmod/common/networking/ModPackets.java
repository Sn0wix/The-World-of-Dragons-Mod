package net.sn0wix_.worldofdragonsmod.common.networking;


import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.ExplodingCubeParticlesPacket;

public class ModPackets {
    public static final Identifier EXPLODING_CUBE_PARTICLES = new Identifier(WorldOfDragons.MOD_ID, "exploding_cube_particles");


    public static void registerC2SPackets(){

    }

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(EXPLODING_CUBE_PARTICLES, ExplodingCubeParticlesPacket::receive);
    }
}

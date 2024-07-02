package net.sn0wix_.worldofdragonsmod.common.networking;


import io.netty.channel.ChannelHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.c2s.KeyPacket;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.s2c.particles.SpawnParticlesPacket;

public class ModPackets {
    public static final Identifier SPAWN_PARTICLES = new Identifier(WorldOfDragons.MOD_ID, "spawn_particles");
    public static final Identifier KEY = new Identifier(WorldOfDragons.MOD_ID, "key");


    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(KEY, KeyPacket::receive);
    }

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(SPAWN_PARTICLES, SpawnParticlesPacket::receive);
    }
}

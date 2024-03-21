package net.sn0wix_.worldofdragonsmod.common.networking.packets.s2c.particles;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.sn0wix_.worldofdragonsmod.common.networking.ModPackets;

public class SpawnParticlesPacket {
    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        byte type = packetByteBuf.asByteBuf().getByte(0);

        for (PacketParticleTypes packetParticleType : PacketParticleTypes.values()) {
            if (packetParticleType.getType() == type) {
                minecraftClient.execute(() -> packetParticleType.decode(packetByteBuf));
            }
        }
    }

    public static void send(PacketParticleTypes type, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeByte(type.getType());
        ServerPlayNetworking.send(player, ModPackets.SPAWN_PARTICLES, buffer);
    }

    public static void send(ServerPlayerEntity player, PacketByteBuf byteBuf) {
        ServerPlayNetworking.send(player, ModPackets.SPAWN_PARTICLES, byteBuf);
    }

    public static PacketByteBuf getBuf(PacketParticleTypes type) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeByte(type.getType());
        return buffer;
    }
}

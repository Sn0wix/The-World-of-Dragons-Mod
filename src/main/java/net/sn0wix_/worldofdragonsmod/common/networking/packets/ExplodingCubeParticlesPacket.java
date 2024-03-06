package net.sn0wix_.worldofdragonsmod.common.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.ExplodingCubeProjectile;
import net.sn0wix_.worldofdragonsmod.common.networking.ModPackets;

public class ExplodingCubeParticlesPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        int i = packetByteBuf.getInt(0);

        client.executeSync(() -> {
            if (client.world != null && client.world.getEntityById(i) instanceof ExplodingCubeProjectile projectile) {
                projectile.spawnExplodingParticles(projectile.getX(), projectile.getY(), projectile.getZ(), client);
            }
        });
    }

    public static void send(int id, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(id);
        ServerPlayNetworking.send(player, ModPackets.EXPLODING_CUBE_PARTICLES, buffer);
    }
}

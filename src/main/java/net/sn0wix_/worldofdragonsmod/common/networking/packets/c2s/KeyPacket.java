package net.sn0wix_.worldofdragonsmod.common.networking.packets.c2s;


import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.sn0wix_.worldofdragonsmod.common.entity.controlling.PlayerControllable;

public class KeyPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if (player.getVehicle() instanceof PlayerControllable playerControllable) {
            server.execute(() -> playerControllable.handlePacket(buf));
        }
    }
}

package net.sn0wix_.worldofdragonsmod.client.particle.packetDecoders;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.s2c.particles.PacketParticleTypes;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.s2c.particles.SpawnParticlesPacket;

public abstract class EntityParticlePacketDecoder extends ParticlePacketDecoder {
    @Override
    public void decode(PacketByteBuf byteBuf) {
        int i = byteBuf.getInt(1);
        Entity entity = MinecraftClient.getInstance().world.getEntityById(i);

        if (MinecraftClient.getInstance().world != null && isInstanceOf(entity)) {
            spawnParticles(entity.getX(), entity.getY(), entity.getZ(), entity, MinecraftClient.getInstance());
        }
    }

    public static void sendToClient(int id, ServerPlayerEntity player, PacketParticleTypes type) {
        PacketByteBuf buffer = SpawnParticlesPacket.getBuf(type);
        buffer.writeInt(id);
        SpawnParticlesPacket.send(player, buffer);
    }


    public abstract void spawnParticles(double x, double y, double z, Entity entity, MinecraftClient client);


    /**
     * This works like a null check, so you can just return entity != null;
     */
    public abstract boolean isInstanceOf(Entity entity);
}

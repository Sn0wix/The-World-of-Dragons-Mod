package net.sn0wix_.worldofdragonsmod.client.particle.packetDecoders;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.sn0wix_.worldofdragonsmod.client.particle.ParticleSpawnUtil;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.ModChestEntity;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.s2c.particles.PacketParticleTypes;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.s2c.particles.SpawnParticlesPacket;

public class ChestBreakParticleDecoder extends EntityParticlePacketDecoder {
    @Override
    public void decode(PacketByteBuf byteBuf) {
        if (MinecraftClient.getInstance().world != null) {
            int i = byteBuf.getInt(1);
            boolean gold = byteBuf.getBoolean(5);

            Entity entity = MinecraftClient.getInstance().world.getEntityById(i);

            if (isInstanceOf(entity)) {
                ParticleSpawnUtil.spawnChestBreakParticles(entity.getX(), entity.getY(), entity.getZ(), gold, entity, MinecraftClient.getInstance());
            }
        }
    }

    @Override
    public boolean isInstanceOf(Entity entity) {
        return entity instanceof ModChestEntity;
    }

    public static void sendToClient(int id, ServerPlayerEntity player, boolean gold, PacketParticleTypes type) {
        PacketByteBuf buffer = SpawnParticlesPacket.getBuf(type);
        buffer.writeInt(id);
        buffer.writeBoolean(gold);
        SpawnParticlesPacket.send(player, buffer);
    }

    @Override
    public void spawnParticles(double x, double y, double z, Entity entity, MinecraftClient client) {

    }
}

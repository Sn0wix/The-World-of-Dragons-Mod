package net.sn0wix_.worldofdragonsmod.client.particle.packetDecoders;

import net.minecraft.network.PacketByteBuf;

public abstract class ParticlePacketDecoder {
    public abstract void decode(PacketByteBuf byteBuf);
}

package net.sn0wix_.worldofdragonsmod.common.networking.packets.s2c.particles;

import net.minecraft.network.PacketByteBuf;
import net.sn0wix_.worldofdragonsmod.client.particle.packetDecoders.BlockBreakParticleDecoder;
import net.sn0wix_.worldofdragonsmod.client.particle.packetDecoders.ExplodingCubeProjectileParticleDecoder;
import net.sn0wix_.worldofdragonsmod.client.particle.packetDecoders.ParticlePacketDecoder;

public enum PacketParticleTypes {
    EXPLODING_PROJECTILE((byte) 0, new ExplodingCubeProjectileParticleDecoder()),
    BLOCK_BREAK((byte) 2, new BlockBreakParticleDecoder());

    private final byte type;
    private final ParticlePacketDecoder decoder;

    PacketParticleTypes(byte type, ParticlePacketDecoder decoder) {
        this.type = type;
        this.decoder = decoder;
    }

    public byte getType() {
        return type;
    }

    public void decode(PacketByteBuf byteBuf) {
        decoder.decode(byteBuf);
    }
}

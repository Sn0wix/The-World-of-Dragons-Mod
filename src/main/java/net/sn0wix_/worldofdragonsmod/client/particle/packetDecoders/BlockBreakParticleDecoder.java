package net.sn0wix_.worldofdragonsmod.client.particle.packetDecoders;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.sn0wix_.worldofdragonsmod.client.particle.ParticleSpawnUtil;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;

public class BlockBreakParticleDecoder extends ParticlePacketDecoder {
    @Override
    public void decode(PacketByteBuf byteBuf) {
        BlockPos pos = byteBuf.readBlockPos();
        MinecraftClient.getInstance().execute(() -> {
            if (MinecraftClient.getInstance().world != null) {
                WorldOfDragons.LOGGER.info(MinecraftClient.getInstance().world.getBlockState(pos).toString());
                //TODO fix error no bounds for empty shape
                ParticleSpawnUtil.addBlockBreakParticles(MinecraftClient.getInstance().world.getBlockState(pos), MinecraftClient.getInstance().particleManager, MinecraftClient.getInstance().world, MinecraftClient.getInstance().world.getBlockState(pos).getCollisionShape(MinecraftClient.getInstance().world, pos).getBoundingBox());
            }
        });
    }
}

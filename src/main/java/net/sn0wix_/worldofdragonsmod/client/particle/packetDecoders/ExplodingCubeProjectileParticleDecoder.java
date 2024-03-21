package net.sn0wix_.worldofdragonsmod.client.particle.packetDecoders;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.projectile.ExplodingCubeProjectile;

public class ExplodingCubeProjectileParticleDecoder extends EntityParticlePacketDecoder {
    @Override
    public void spawnParticles(double x, double y, double z, Entity entity, MinecraftClient client) {
        ExplodingCubeProjectile projectile = (ExplodingCubeProjectile) entity;
        projectile.spawnExplodingParticles(x, y, z, client);
    }

    @Override
    public boolean isInstanceOf(Entity entity) {
        return entity instanceof ExplodingCubeProjectile;
    }
}

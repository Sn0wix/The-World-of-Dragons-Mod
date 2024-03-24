package net.sn0wix_.worldofdragonsmod.client.particle;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.ModChestEntity;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.projectile.ExplodingCubeProjectile;

import java.util.Random;

public class ParticleSpawnUtil {
    public static final Random random = new Random();

    public static void spawnChestBreakParticles(double x, double y, double z, boolean puf, Entity entity, MinecraftClient client) {
        //gold particle pri otevreni - puf, po "kilnuti" campfire particly ale o hodne min XD
        if (client.world != null && entity instanceof ModChestEntity) {
            ParticleManager manager = client.particleManager;

            if (!puf) {
                for (int i = 0; i < (96 - 69) * 2 + random.nextInt(4); i++) {
                    manager.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x + getRandomDouble(2), y + getRandomDouble(2), z + getRandomDouble(2), getRandomDouble(25), getRandomDouble(25), getRandomDouble(25));
                }
            } else {

            }
        }
    }

    public static void spawnMagmaProjectileParticles(double x, double y, double z, MinecraftClient client, ExplodingCubeProjectile projectile) {
        if (client.world != null) {
            ParticleManager manager = client.particleManager;

            for (int i = 0; i <= random.nextInt(4) + 11; i++) {
                manager.addParticle(ParticleTypes.LAVA, x, y, z, 0, 0, 0);
                manager.addParticle(ParticleTypes.SMOKE, x, y, z, getRandomDouble(20), getRandomDouble(20), getRandomDouble(20));
            }

            for (int i = 0; i <= 20; i++) {
                manager.addParticle(ParticleTypes.FLAME, x, y, z, getRandomDouble(5), getRandomDouble(5), getRandomDouble(5));
            }

            addBlockBreakParticles(Blocks.MAGMA_BLOCK.getDefaultState(), manager, client.world, projectile.getBoundingBox());
        }
    }

    public static void addBlockBreakParticles(BlockState state, ParticleManager manager, ClientWorld world, Box box) {
        if (state.isAir() || !state.hasBlockBreakParticles()) {
            return;
        }

        double maxX = box.maxX;
        double maxY = box.maxY;
        double maxZ = box.maxZ;

        double minX = box.minX;
        double minY = box.minY;
        double minZ = box.minZ;

        double d = Math.min(1.0, maxX - minX);
        double e = Math.min(1.0, maxY - minY);
        double f = Math.min(1.0, maxZ - minZ);
        int i = Math.max(2, MathHelper.ceil(d / 0.25));
        int j = Math.max(2, MathHelper.ceil(e / 0.25));
        int k = Math.max(2, MathHelper.ceil(f / 0.25));
        for (int l = 0; l < i; ++l) {
            for (int m = 0; m < j; ++m) {
                for (int n = 0; n < k; ++n) {
                    double g = ((double) l + 0.5) / (double) i;
                    double h = ((double) m + 0.5) / (double) j;
                    double o = ((double) n + 0.5) / (double) k;
                    double p = g * d + minX;
                    double q = h * e + minY;
                    double r = o * f + minZ;
                    manager.addParticle(new BlockDustParticle(world, p, q, r, g - 0.5, h - 0.5, o - 0.5, state));
                }
            }
        }
    }


    public static void spawnBleedParticles(Entity entity, Random random) {
        MinecraftClient.getInstance().execute(() -> {
            if (entity instanceof PlayerEntity player && player.getWorld() instanceof ClientWorld clientWorld) {
                if (player.getWorld().isClient()) {
                    for (int i = 0; i < 7; i++) {
                        MinecraftClient.getInstance().particleManager.addParticle(new BlockDustParticle(clientWorld, player.getX(),
                                player.getY() + random.nextDouble(1.8), player.getZ(), random.nextInt(10) * (random.nextBoolean() ? 1 : -1),
                                random.nextInt(8) * (random.nextBoolean() ? 1 : -1), random.nextInt(10) * (random.nextBoolean() ? 1 : -1)
                                , Blocks.REDSTONE_BLOCK.getDefaultState()));
                    }
                }
            }
        });
    }

    //Helper methods
    private static double getRandomDouble(float divisor) {
        return random.nextBoolean() ? -random.nextDouble() / divisor : random.nextDouble() / divisor;
    }

    private static double getRandomDouble() {
        return random.nextBoolean() ? -random.nextDouble() : random.nextDouble();
    }

    private static float generateRandomFloat(double min, double max) {
        return (float) (min + (max - min) * random.nextFloat());
    }
}

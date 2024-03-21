package net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.sn0wix_.worldofdragonsmod.client.particle.ParticleSpawnUtil;
import net.sn0wix_.worldofdragonsmod.client.particle.packetDecoders.EntityParticlePacketDecoder;
import net.sn0wix_.worldofdragonsmod.client.particle.packetDecoders.ExplodingCubeProjectileParticleDecoder;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.s2c.particles.PacketParticleTypes;

public class ExplodingCubeProjectile extends ExplosiveProjectileEntity {
    public ParticleEffect mainParticle = ParticleTypes.FLAME;
    public ParticleEffect secondaryParticle = ParticleTypes.SMOKE;
    public ParticleEffect additionParticle = ParticleTypes.LAVA;
    private int ticksToExplode = 120;

    public ExplodingCubeProjectile(EntityType<? extends ExplodingCubeProjectile> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (!getWorld().isClient()) {
            ticksToExplode--;

            if (ticksToExplode <= 0) {
                createExplosion();
            }
        } else {
            spawnIdleParticles();
        }
    }

    @Override
    public void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        createExplosion();
    }

    private void createExplosion() {
        getWorld().createExplosion(this, getDamageSources().create(DamageTypes.EXPLOSION), new NoBlocksDestroyBehavior(), getPos(), 1.5f, true, World.ExplosionSourceType.BLOCK);
        if (!getWorld().isClient()) {
            getWorld().getPlayers().forEach(player -> {
                if (player.isInRange(this, 256)) {
                    EntityParticlePacketDecoder.sendToClient(this.getId(), (ServerPlayerEntity) player, PacketParticleTypes.EXPLODING_PROJECTILE);
                }
            });
        }

        getWorld().playSound(null, getX(), getY(), getZ(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 8f, 1);
        getWorld().playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.BLOCKS, 1.5f, 1);
        this.remove(RemovalReason.KILLED);
    }

    public void spawnExplodingParticles(double x, double y, double z, MinecraftClient client) {
        ParticleSpawnUtil.spawnMagmaProjectileParticles(x, y, z, client, this);
    }

    public void spawnIdleParticles() {
        getWorld().addParticle(mainParticle, getX(), getRandomBodyY(), getZ(), getRandomDouble(20), getRandomDouble(20), getRandomDouble(20));

        for (int i = 0; i < 5; i++) {
            getWorld().addParticle(secondaryParticle, getX() + getRandomDoubleBetween(getBoundingBox().minX, getBoundingBox().maxX), getRandomBodyY(), getZ() + getRandomDoubleBetween(getBoundingBox().minZ, getBoundingBox().maxZ), getRandomDouble(20), getRandomDouble(20), getRandomDouble(20));
        }
        if (random.nextInt(15) == 1) {
            getWorld().addParticle(additionParticle, getX(), getY(), getZ(), 0, 0, 0);
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("TicksToExplode", ticksToExplode);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("TicksToExplode")) {
            ticksToExplode = nbt.getInt("TicksToExplode");
        }
    }

    private double getRandomDoubleBetween(double minValue, double maxValue) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("Invalid range. MinValue must be less than maxValue.");
        }

        return minValue + (maxValue - minValue) * random.nextDouble();
    }

    private double getRandomDouble(float divisor) {
        return random.nextBoolean() ? -random.nextDouble() / divisor : random.nextDouble() / divisor;
    }


    private static class NoBlocksDestroyBehavior extends net.minecraft.world.explosion.ExplosionBehavior {
        @Override
        public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
            return false;
        }
    }
}

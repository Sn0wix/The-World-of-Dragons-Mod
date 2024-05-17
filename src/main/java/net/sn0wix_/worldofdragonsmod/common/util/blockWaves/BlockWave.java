package net.sn0wix_.worldofdragonsmod.common.util.blockWaves;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.DragonBreathParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.sn0wix_.worldofdragonsmod.client.particle.packetDecoders.EntityParticlePacketDecoder;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.BlockWaveFallingBlockEntity;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.s2c.particles.PacketParticleTypes;
import net.sn0wix_.worldofdragonsmod.common.networking.packets.s2c.particles.SpawnParticlesPacket;

import java.util.ArrayList;
import java.util.ListIterator;

public class BlockWave {
    private final ServerWorld world;
    private boolean hasFinished = false;
    private final float distancePerTick;
    private final int ticksToFinish;
    private double deltaX;
    private double deltaZ;
    private final Entity owner;
    private final float damage;

    private int currentTick = 0;
    public final ArrayList<Vec3d> vectors = new ArrayList<>(8);
    public final ArrayList<BlockPos> thrownPositions = new ArrayList<>();
    public final ArrayList<BlockPos> holesMadeByWave = new ArrayList<>();
    public final ArrayList<Entity> movedEntities = new ArrayList<>();

    //TODO fix water behavior

    public BlockWave(float tickDistance, int ticksToFinish, Vec3d pos1, Vec3d pos2, int vectorCount, int nextRowAfter, ServerWorld world, float damage, Entity owner) {
        this.distancePerTick = tickDistance;
        this.ticksToFinish = ticksToFinish;
        this.world = world;
        this.owner = owner;
        this.damage = damage;

        addVectors(pos1, pos2, vectorCount, nextRowAfter);
    }

    public void tick() {
        currentTick++;

        if (currentTick > ticksToFinish) {
            hasFinished = true;
            return;
        }

        ListIterator<Vec3d> iterator = vectors.listIterator();
        while (iterator.hasNext()) {
            Vec3d vec3d = iterator.next();
            BlockPos pos = new BlockPos((int) vec3d.getX(), (int) vec3d.getY(), (int) vec3d.getZ());
            BlockState stateDown = world.getBlockState(pos.down());

            if (!hasCollision(stateDown, pos)) {
                if (!hasCollision(world.getBlockState(pos.down(2)), pos.down(2))) {
                    //is in air, remove
                    iterator.remove();
                    continue;
                } else {
                    if (!holesMadeByWave.contains(pos.down()) && !BlockWaves.containsHole(pos.down())) {
                        //step down
                        vec3d = new Vec3d(vec3d.getX(), vec3d.getY() - 1, vec3d.getZ());
                    }
                }
            }

            if (hasCollision(world.getBlockState(pos), pos)) {
                if (hasCollision(world.getBlockState(pos.up()), pos.up())) {
                    //hit a wall, remove
                    iterator.remove();
                    continue;
                } else {
                    if (!holesMadeByWave.contains(pos.down()) && !BlockWaves.containsHole(pos.down())) {
                        //step up
                        vec3d = new Vec3d(vec3d.getX(), vec3d.getY() + 1, vec3d.getZ());
                    }
                }
            }

            if (!thrownPositions.contains(pos) && !holesMadeByWave.contains(pos.down()) && !BlockWaves.containsHole(pos.down())) {
                Vec3d finalVec3d1 = vec3d;
                world.getPlayers().forEach(player -> {
                    if (isInRange(256, finalVec3d1, player.getPos())) {
                        SpawnParticlesPacket.send(player, SpawnParticlesPacket.getBuf(PacketParticleTypes.BLOCK_BREAK).writeBlockPos(pos.down()));
                    }
                });
                world.playSound(null, pos.down(), stateDown.getSoundGroup().getBreakSound(), SoundCategory.BLOCKS);
                world.setBlockState(pos.down(), stateDown.getFluidState().getBlockState(), Block.NOTIFY_ALL, 0);
                thrownPositions.add(pos);
                holesMadeByWave.add(pos.down());
                BlockWaves.addHole(pos.down());

                Vec3d velocity = new Vec3d(0, 0.3, 0);
                BlockWaveFallingBlockEntity entity = BlockWaveFallingBlockEntity.spawnFromBlock(world, pos.down(), stateDown, owner, damage, this);
                entity.dropItem = false;
                entity.setFallingBlockPos(pos.down());
                entity.setVelocity(velocity);
                entity.move(MovementType.SELF, velocity);
                entity.velocityModified = true;
                entity.velocityDirty = true;
                entity.tick();
            }

            iterator.remove();
            iterator.add(moveVec(vec3d));
        }

        if (vectors.isEmpty()) {
            hasFinished = true;
        }
    }

    public void finishWave() {
        BlockWaves.removeAllHoles(holesMadeByWave);
    }

    public boolean hasCollision(BlockState state, BlockPos pos) {
        return !state.getCollisionShape(world, pos).isEmpty();
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public Vec3d moveVec(Vec3d vec) {
        return vec.add(deltaX * distancePerTick, 0, deltaZ * distancePerTick);
    }

    public void addVectors(Vec3d pos1, Vec3d pos2, int count, int nextRowAfter) {
        //TODO add correct spawning
        Vec3d normalized = new Vec3d(pos2.getX() - pos1.getX(), 0, pos2.getZ() - pos1.getZ()).normalize();
        this.deltaX = normalized.getX();
        this.deltaZ = normalized.getZ();
        vectors.add(pos1);
        vectors.add(calculateVec3Clockwise(pos1, pos2));
        vectors.add(calculateVec3CounterClockwise(pos1, pos2));
    }

    public Vec3d calculateVec3Clockwise(Vec3d vector1, Vec3d vector2) {
        double deltaX = vector2.getX() - vector1.getX();
        double deltaZ = vector2.getZ() - vector1.getZ();

        // Rotate (deltaX, deltaZ) 90 degrees clockwise
        double rotatedX = deltaZ;
        double rotatedY = -deltaX;

        Vec3d normalized = new Vec3d(rotatedX, 0, rotatedY).normalize();

        return new Vec3d(vector1.getX() + normalized.getX(), vector1.getY(), vector1.getZ() + normalized.getZ());
    }

    public Vec3d calculateVec3CounterClockwise(Vec3d vector1, Vec3d vector2) {
        double deltaX = vector2.getX() - vector1.getX();
        double deltaZ = vector2.getZ() - vector1.getZ();

        // Rotate (deltaX, deltaZ) 90 degrees counter clockwise
        double rotatedX = -deltaZ;
        double rotatedY = deltaX;

        Vec3d normalized = new Vec3d(rotatedX, 0, rotatedY).normalize();

        return new Vec3d(vector1.getX() + normalized.getX(), vector1.getY(), vector1.getZ() + normalized.getZ());
    }

    public boolean isInRange(double radius, Vec3d pos1, Vec3d pos2) {
        double dx = pos1.getX() - pos2.getX();
        double dy = pos1.getY() - pos2.getY();
        double dz = pos1.getZ() - pos2.getZ();
        double distanceSquared = dx * dx + dy * dy + dz * dz;
        return distanceSquared <= radius * radius;
    }
}

package net.sn0wix_.worldofdragonsmod.common.util.blockWaves;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.ListIterator;

public class BlockWave {
    private final ServerWorld world;
    private boolean hasFinished = false;
    private final float distancePerTick;
    private final int ticksToFinish;
    private double deltaX;
    private double deltaZ;

    private int currentTick = 0;
    public final ArrayList<Vec3d> vectors = new ArrayList<>(8);
    public final ArrayList<BlockPos> usedPositions = new ArrayList<>();


    public BlockWave(float tickDistance, int ticksToFinish, Vec3d pos1, Vec3d pos2, int vectorCount, int nextRowAfter, ServerWorld world) {
        this.distancePerTick = tickDistance;
        this.ticksToFinish = ticksToFinish;
        this.world = world;

        addVectors(pos1, pos2, vectorCount, nextRowAfter);
    }

    public void tick() {
        currentTick++;

        if (currentTick == ticksToFinish) {
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
                    //step down
                    vec3d = new Vec3d(vec3d.getX(), vec3d.getY() - 1, vec3d.getZ());
                }
            }
            //TODO fix wave ending upon slow speeds

            if (hasCollision(world.getBlockState(pos), pos)) {
                if (hasCollision(world.getBlockState(pos.up()), pos.up())) {
                    //hit a wall, remove
                    iterator.remove();
                    continue;
                } else {
                    //step up
                    vec3d = new Vec3d(vec3d.getX(), vec3d.getY() + 1, vec3d.getZ());
                }
            }

            if (!usedPositions.contains(pos)) {
                world.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
                usedPositions.add(pos);

                Vec3d velocity = new Vec3d(0, 0.5, 0);
                FallingBlockEntity entity = FallingBlockEntity.spawnFromBlock(world, pos.down(), stateDown);
                entity.collidedSoftly = false;
                entity.dropItem = false;
                entity.setFallingBlockPos(pos.down());
                entity.setVelocity(velocity);
                entity.move(MovementType.SELF, velocity);
                entity.velocityModified = true;
                entity.velocityDirty = true;
                entity.tick();
                //new BlockWaveFallingBlockEntity(ModEntities.BLOCK_WAVE_FALLING_BLOCK, world).spawn(world, pos.down(), stateDown);
            }

            iterator.remove();
            iterator.add(moveVec(vec3d));
        }

        if (vectors.isEmpty()) {
            hasFinished = true;
        }
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
}

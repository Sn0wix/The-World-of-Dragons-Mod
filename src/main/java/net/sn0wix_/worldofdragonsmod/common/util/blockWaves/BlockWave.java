package net.sn0wix_.worldofdragonsmod.common.util.blockWaves;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
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

            world.getPlayers().forEach(player -> {
                world.spawnParticles(player, ParticleTypes.DRAGON_BREATH, true, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 1, 0, 0, 0, 0);
            });

            iterator.remove();
            iterator.add(moveVec(vec3d));
        }
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public Vec3d moveVec(Vec3d vec) {
        return vec.add(deltaX * distancePerTick, 0, deltaZ * distancePerTick);
    }

    public void addVectors(Vec3d pos1, Vec3d pos2, int count, int nextRowAfter) {
        Vec3d normalized = new Vec3d(pos2.getX() - pos1.getX(), 0, pos2.getZ() - pos1.getZ()).normalize();
        this.deltaX = normalized.getX();
        this.deltaZ = normalized.getZ();
        vectors.add(pos1);
        Vec3d vec3 = calculateVector3(pos1, pos2);

        double vec3deltaX = vec3.getX();
        double vec3deltaZ = vec3.getX();
        byte direction = 1;
        //TODO test
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < count / 2; j++) {
                vectors.add(new Vec3d(vec3deltaX * direction * j, pos1.getY(), vec3deltaZ * direction * j));
            }
            direction -= 1;
        }
    }

    public Vec3d calculateVector3(Vec3d vector1, Vec3d vector2) {
        double deltaX = vector2.getX() - vector1.getX();
        double deltaZ = vector2.getZ() - vector1.getZ();

        // Rotate (deltaX, deltaZ) 90 degrees clockwise
        double rotatedX = deltaZ;
        double rotatedY = -deltaX;

        Vec3d normalized = new Vec3d(rotatedX, 0, rotatedY).normalize();

        return new Vec3d(vector1.getX() + normalized.getX(), vector1.getY(), vector1.getZ() + normalized.getZ());
    }
}

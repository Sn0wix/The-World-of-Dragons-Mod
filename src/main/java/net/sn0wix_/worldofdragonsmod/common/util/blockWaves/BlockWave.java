package net.sn0wix_.worldofdragonsmod.common.util.blockWaves;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
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


    public BlockWave(float tickDistance, int ticksToFinish, Vec3d pos1, Vec3d pos2, float rotation, int vectorCount, int nextRowAfter, ServerWorld world) {
        this.distancePerTick = tickDistance;
        this.ticksToFinish = ticksToFinish;
        this.world = world;

        addVectors(pos1, pos2, vectorCount, nextRowAfter, rotation);
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

    public Vec3d rotateVec(Vec3d vectorToRotate, Vec3d pivotPoint, double angleInRadians) {
        double x = vectorToRotate.x - pivotPoint.x;
        double y = vectorToRotate.y - pivotPoint.y;

        return new Vec3d(x * Math.cos(angleInRadians) - y * Math.sin(angleInRadians) + pivotPoint.x, pivotPoint.getY(), x * Math.sin(angleInRadians) + y * Math.cos(angleInRadians) + pivotPoint.y);

        /*Vec3d translatedVector = new Vec3d(vectorToRotate.getX() - pivotPoint.getX(), vectorToRotate.getY(), vectorToRotate.getZ() - pivotPoint.getZ());

        // Calculate sine and cosine of the angle
        double sinTheta = Math.sin(angleInRadians);
        double cosTheta = Math.cos(angleInRadians);

        // Perform the rotation
        Vec3d rotatedVector = new Vec3d(translatedVector.getX() * cosTheta - translatedVector.getZ() * sinTheta, vectorToRotate.getY(), translatedVector.getX() * sinTheta + translatedVector.getZ() * cosTheta);

        // Translate the rotated vector back to its original position
        return new Vec3d(rotatedVector.getX() + pivotPoint.getX(), vectorToRotate.getY(), rotatedVector.getZ() + pivotPoint.getZ());*/
    }

    public void addVectors(Vec3d pos1, Vec3d pos2, int count, int nextRowAfter, float rotation) {
        Vec3d normalized = new Vec3d(pos2.getX() - pos1.getX(), 0, pos2.getZ() - pos1.getZ()).normalize();
        this.deltaX = normalized.getX();
        this.deltaZ = normalized.getZ();

        double x = pos1.getX();
        double y = pos1.getY();
        double z = pos1.getZ();
        int a = 1;

        int half = count / 2;
        int nextRow = 0;

        ArrayList<Vec3d> notAlignedVectors = new ArrayList<>(count);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < half; j++) {
                if (nextRow == nextRowAfter) {
                    nextRow = 0;
                    z--;
                }

                Vec3d vecToAdd = new Vec3d(x, y, z + 0.1);

                if (!notAlignedVectors.contains(vecToAdd)) {
                    notAlignedVectors.add(vecToAdd);
                }

                x += a;
                nextRow++;
            }

            a = -1;
            nextRow = 0;
            x = pos1.getX();
            z = pos1.getZ();
        }

        notAlignedVectors.forEach(vec3d -> {
            Vec3d vec = vec3d.subtract(pos1);
            vec = vec.rotateX(rotation);
            vec = vec.add(pos1);
            vectors.add(vec);
        });




        /*double vDeltaX = deltaZ;
        double vDeltaZ = deltaX;
        vectors.add(new Vec3d(pos1.getX() + vDeltaX, pos1.getY(), pos1.getZ() + vDeltaZ));*/

        //int half = count / 2;
        //int nextRow = 0;
        /*for (int j = 0; j < half; j++) {
            vectors.add(new Vec3d(pos1.getX() + vDeltaX, pos1.getY(), pos1.getZ() + vDeltaZ));
            ++vDeltaX;
            ++vDeltaZ;
        }*/

        /*nextRow++;

        if (nextRow == nextRowAfter) {
            nextRow = 0;
            --vDeltaZ;
        }
        for (int i = 0; i < 2; i++) {
            ASDASD

            nextRow = 0;
            vDeltaX = -deltaZ;
            vDeltaZ = -deltaX;
        }*/
    }

    /*int half = vectorCount / 2;
    int nextRow = 0;
    int direction = 1;

        for (int i = 0; i < 2; i++) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for (int j = 0; j < half; j++) {
            x = x + 1;

            if (nextRow == Math.abs(nextRowAfter)) {
                z--;
            }

            vectors.put(new Vec3d(x, y, z), null);
            nextRow++;
        }

        nextRow = -nextRowAfter;
        direction = -1;
    }*/
}

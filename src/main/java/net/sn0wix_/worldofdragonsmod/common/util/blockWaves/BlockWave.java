package net.sn0wix_.worldofdragonsmod.common.util.blockWaves;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationCalculator;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;

public class BlockWave {
    private final ServerWorld world;
    private boolean hasFinished = false;
    private final float distancePerTick;
    private final int ticksToFinish;
    private final double rotationInRads;
    private final int count;
    private final int nextRowAfter;

    private int currentTick = 0;
    public Vec3d startVec;
    public final HashMap<Vec3d, BlockPos> vectors = new HashMap<>(8);


    public BlockWave(float tickDistance, int ticksToFinish, Vec3d startVec, double rotationInRads, int vectorCount, int nextRowAfter, ServerWorld world) {
        this.distancePerTick = tickDistance;
        this.ticksToFinish = ticksToFinish;
        this.startVec = startVec;
        /**You can use MathHelper.RADIANS_PER_DEGREE * yourRotation when converting, or Math.toRadians(yourRotation);*/
        this.rotationInRads = rotationInRads;
        this.count = vectorCount;
        this.nextRowAfter = nextRowAfter;
        this.world = world;

        addVectors(startVec, rotationInRads, vectors);
    }

    public BlockWave(float tickDistance, int ticksToFinish, Vec3d startVec, Vec3d pos1, Vec3d pos2, int vectorCount, int nextRowAfter, ServerWorld world) {
        this.distancePerTick = tickDistance;
        this.ticksToFinish = ticksToFinish;
        this.startVec = startVec;
        this.rotationInRads = calculateRotation(pos1, pos2);
        this.count = vectorCount;
        this.nextRowAfter = nextRowAfter;
        this.world = world;

        addVectors(startVec, rotationInRads, vectors);
    }

    public void tick() {
        currentTick++;

        if (currentTick == ticksToFinish) {
            hasFinished = true;
            return;
        }

        for (Map.Entry<Vec3d, BlockPos> entry : vectors.entrySet()) {
            Vec3d vec3d = entry.getKey();

            world.getPlayers().forEach(player -> {
                world.spawnParticles(player, ParticleTypes.CLOUD, true, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 1, 0, 0, 0, 0);
            });

            vectors.remove(vec3d);
            vectors.put(moveVec(vec3d), null);
        }
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public Vec3d moveVec(Vec3d vec) {
        double deltaX = distancePerTick * Math.cos(rotationInRads);
        double deltaZ = distancePerTick * Math.sin(rotationInRads);
        return vec.add(deltaX, 0, deltaZ);
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

    public static void addVectors(Vec3d startVec, double rotationInRads, HashMap<Vec3d, BlockPos> vec3ds) {
        vec3ds.put(startVec, null);
    }

    public static double calculateRotation(Vec3d vector1, Vec3d vector2) {
//TODO
        return 0f;
    }
}

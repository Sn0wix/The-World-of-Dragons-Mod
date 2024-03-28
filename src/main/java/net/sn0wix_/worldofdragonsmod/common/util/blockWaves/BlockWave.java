package net.sn0wix_.worldofdragonsmod.common.util.blockWaves;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class BlockWave {
    private final ServerWorld world;
    private boolean hasFinished = false;
    private final float distancePerTick;
    private final int ticksToFinish;
    private double deltaX;
    private double deltaZ;
    private final int count;
    private final int nextRowAfter;

    private int currentTick = 0;
    public Vec3d startVec;
    public final ArrayList<Vec3d> vectors = new ArrayList<>(8);


    public BlockWave(float tickDistance, int ticksToFinish, Vec3d pos1, Vec3d pos2, int vectorCount, int nextRowAfter, ServerWorld world) {
        this.distancePerTick = tickDistance;
        this.ticksToFinish = ticksToFinish;
        this.startVec = pos1;
        this.count = vectorCount;
        this.nextRowAfter = nextRowAfter;
        this.world = world;

        addVectors(startVec, pos1, pos2, vectors);
    }

    public void tick() {
        currentTick++;

        if (currentTick == ticksToFinish) {
            hasFinished = true;
            return;
        }

        for (Vec3d vec3d : vectors) {
            world.getPlayers().forEach(player -> {
                world.spawnParticles(player, ParticleTypes.CLOUD, true, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 1, 0, 0, 0, 0);
            });

            vectors.remove(vec3d);
            vectors.add(moveVec(vec3d));
        }
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public Vec3d moveVec(Vec3d vec) {
        return vec.add(deltaX * distancePerTick, 0, deltaZ * distancePerTick);
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

    public void addVectors(Vec3d startVec, Vec3d pos1, Vec3d pos2, ArrayList<Vec3d> vec3ds) {
        Vec3d normalized = new Vec3d(pos2.getX() - pos1.getX(), 0, pos2.getZ() - pos1.getZ()).normalize();
        this.deltaX = normalized.getX();
        this.deltaZ = normalized.getZ();
        vec3ds.add(startVec);
    }
}

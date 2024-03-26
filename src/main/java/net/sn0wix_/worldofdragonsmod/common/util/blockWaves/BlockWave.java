package net.sn0wix_.worldofdragonsmod.common.util.blockWaves;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class BlockWave {
    private boolean hasFinished = false;
    public final ArrayList<Vec3d> vectors = new ArrayList<>(8);
    public void tick() {
        vectors.forEach(vec3d -> {

        });
    }

    public void spawn(BlockPos pos, float rotation, int vectorCount) {
        for (int i = 0; i < vectorCount; i++) {

        }
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    public Vec3d moveVec(Vec3d vec) {
        return vec;
    }
}

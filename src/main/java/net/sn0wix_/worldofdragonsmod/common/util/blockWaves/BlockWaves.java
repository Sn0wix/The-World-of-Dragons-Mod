package net.sn0wix_.worldofdragonsmod.common.util.blockWaves;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BlockWaves {
    private static final ArrayList<BlockWave> BLOCK_WAVES = new ArrayList<>();
    private static final ArrayList<BlockPos> HOLES_MADE_BY_WAVES = new ArrayList<>();

    public static void tickWaves() {
        if (!BLOCK_WAVES.isEmpty()) {
            Iterator<BlockWave> iterator = BLOCK_WAVES.iterator();
            while (iterator.hasNext()) {
                BlockWave wave = iterator.next();

                if (wave.hasFinished()) {
                    wave.finishWave();
                    iterator.remove();
                    continue;
                }

                wave.tick();
            }
        }
    }

    public static void removeWave(BlockWave wave) {
        BLOCK_WAVES.remove(wave);
    }

    public static void addWave(BlockWave wave) {
        BLOCK_WAVES.add(wave);
    }


    public static void removeAllHoles(List<BlockPos> holesToRemove) {
        HOLES_MADE_BY_WAVES.removeAll(holesToRemove);
    }

    public static void addAllHoles(List<BlockPos> holesToAdd) {
        HOLES_MADE_BY_WAVES.addAll(holesToAdd);
    }

    public static void addHole(BlockPos pos) {
        HOLES_MADE_BY_WAVES.add(pos);
    }

    public static void removeHole(BlockPos pos) {
        HOLES_MADE_BY_WAVES.remove(pos);
    }

    public static boolean containsHole(BlockPos pos) {
        return HOLES_MADE_BY_WAVES.contains(pos);
    }
}

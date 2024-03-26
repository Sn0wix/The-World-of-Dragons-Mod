package net.sn0wix_.worldofdragonsmod.common.util.blockWaves;

import java.util.ArrayList;
import java.util.Iterator;

public class BlockWaves {
    public static final ArrayList<BlockWave> BLOCK_WAVES = new ArrayList<>();

    public static void tickWaves() {
        if (!BLOCK_WAVES.isEmpty()) {
            Iterator<BlockWave> iterator = BLOCK_WAVES.iterator();
            while (iterator.hasNext()) {
                BlockWave wave = iterator.next();

                if (wave.hasFinished()) {
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
}

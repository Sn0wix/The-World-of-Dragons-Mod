package net.sn0wix_.worldofdragonsmod.common.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragonsMain;

public class ModEffects {

    public static final StatusEffect BLEEDING = Registry.register(Registries.STATUS_EFFECT, new Identifier(WorldOfDragonsMain.MOD_ID, "bleeding"), new BleedingEffect());


    public static void registerEffects() {
        WorldOfDragonsMain.LOGGER.info("Registering Effects for " + WorldOfDragonsMain.MOD_ID);
    }
}

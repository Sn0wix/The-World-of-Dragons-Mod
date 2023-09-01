package net.sn0wix_.worldofdragonsmod.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMain;

public class ModEffects {

    public static StatusEffect BLEEDING;

    private static StatusEffect registerStatusEffect(String name) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(WorldOfDragonsMain.MOD_ID, name),
                new BleedingEffect(StatusEffectCategory.HARMFUL, 11808842));
    }

    public static void registerEffects() {
        BLEEDING = registerStatusEffect("bleeding");
    }
}

package net.sn0wix_.worldofdragonsmod.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.sn0wix_.worldofdragonsmod.common.util.ModDamageSources;
import net.sn0wix_.worldofdragonsmod.client.particle.ParticleSpawnUtil;

import java.util.Random;

public class BleedingEffect extends StatusEffect {
    Random random = new Random();
    public BleedingEffect() {
        super(StatusEffectCategory.HARMFUL, 11808842);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient) {
            entity.damage(ModDamageSources.of(entity.getWorld(), ModDamageSources.DAMAGE_SOURCE_BLEEDING), (float) (amplifier + 1) / 4);
        } else {
            ParticleSpawnUtil.spawnBleedParticles(entity, random);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}

package net.sn0wix_.worldofdragonsmod.effect;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.sn0wix_.worldofdragonsmod.util.ModDamageSources;

import java.util.Random;

public class BleedingEffect extends StatusEffect {
    Random random = new Random();
    public BleedingEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient) {
            entity.damage(ModDamageSources.of(entity.getWorld(), ModDamageSources.DAMAGE_SOURCE_BLEEDING), (float) (amplifier + 1) / 4);
        } else {
            if (entity instanceof PlayerEntity player && player.getWorld() instanceof ClientWorld clientWorld) {
                if (player.getWorld().isClient()) {
                    for (int i = 0; i < 7; i++) {
                        MinecraftClient.getInstance().particleManager.addParticle(new BlockDustParticle(clientWorld, player.getX(),
                                player.getY() + random.nextDouble(1.8), player.getZ(), random.nextInt(10) * (random.nextBoolean() ? 1 : -1),
                                random.nextInt(8) * (random.nextBoolean() ? 1 : -1),random.nextInt(10) * (random.nextBoolean() ? 1 : -1)
                                , Blocks.REDSTONE_BLOCK.getDefaultState()));
                    }
                }
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}

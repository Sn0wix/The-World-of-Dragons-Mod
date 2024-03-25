package net.sn0wix_.worldofdragonsmod.client.events.tick;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.sn0wix_.worldofdragonsmod.client.particle.ParticleSpawnUtil;
import net.sn0wix_.worldofdragonsmod.common.effect.ModEffects;

import java.util.Random;

public class ClientEndTickEvent implements ClientTickEvents.EndTick {
    public static final Random RANDOM = new Random();


    //TODO test
    @Override
    public void onEndTick(MinecraftClient client) {
        if (client.world != null && client.world.getPlayers().size() > 1) {
            client.world.getPlayers().forEach(abstractClientPlayerEntity -> {
                if (abstractClientPlayerEntity.hasStatusEffect(ModEffects.BLEEDING)) {
                    ParticleSpawnUtil.spawnBleedParticles(abstractClientPlayerEntity, RANDOM);
                }
            });
        }
    }
}

package net.sn0wix_.worldofdragonsmod.common.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;

public class ModParticles {
    public static final DefaultParticleType BLEED_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles(){
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(WorldOfDragons.MOD_ID, "bleed"), BLEED_PARTICLE);
    }
}

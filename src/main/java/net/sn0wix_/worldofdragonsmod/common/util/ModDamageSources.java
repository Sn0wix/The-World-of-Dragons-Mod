package net.sn0wix_.worldofdragonsmod.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;

public class ModDamageSources {
    public static final RegistryKey<DamageType> BLEEDING = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(WorldOfDragons.MOD_ID, "bleeding"));
    public static final RegistryKey<DamageType> BLOCK_WAVE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(WorldOfDragons.MOD_ID, "block_wave"));


    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }

    public static DamageSource of(World world, RegistryKey<DamageType> key, Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), attacker);
    }
}

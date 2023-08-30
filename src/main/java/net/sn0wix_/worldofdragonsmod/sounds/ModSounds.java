package net.sn0wix_.worldofdragonsmod.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMod;

public class ModSounds {
    public static SoundEvent BOSS_FINAL_2 = registerSoundEvent("boss_final_1");
    public static SoundEvent BOSS_FIRE = registerSoundEvent("boss_fire");
    public static SoundEvent BOSS_1 = registerSoundEvent("boss_1");
    public static SoundEvent BOSS_2 = registerSoundEvent("boss_2");
    public static SoundEvent BOSS_3 = registerSoundEvent("boss_3");
    public static SoundEvent DEATH = registerSoundEvent("death");
    public static SoundEvent DEATH_DUNGEON = registerSoundEvent("death_dungeon");
    public static SoundEvent DESERT = registerSoundEvent("desert");
    public static SoundEvent ELDER = registerSoundEvent("elder");
    public static SoundEvent FINAL = registerSoundEvent("final");
    public static SoundEvent FIRE = registerSoundEvent("fire");
    public static SoundEvent ICE = registerSoundEvent("ice");
    public static SoundEvent SHIP = registerSoundEvent("ship");
    public static SoundEvent BACKGROUND = registerSoundEvent("background");
    public static SoundEvent ENDERGUARDIAN_THEME = registerSoundEvent("enderguardian_theme");
    public static SoundEvent HARBINGER_THEME = registerSoundEvent("harbinger_theme");
    public static SoundEvent INGIS_THEME = registerSoundEvent("ignis_theme");
    public static SoundEvent LEVIATHAN_THEME = registerSoundEvent("leviathan_theme");
    public static SoundEvent MONSTROSITY_THEME = registerSoundEvent("monstrosity_theme");
    public static SoundEvent EARTHEN = registerSoundEvent("earthen");
    public static SoundEvent HYDRA = registerSoundEvent("hydra");
    public static SoundEvent MASTER_ELDER_DRAGON = registerSoundEvent("master_elder_dragon");
    public static SoundEvent RIMESKULL = registerSoundEvent("rimeskull");
    public static SoundEvent SHIMMERSCALE = registerSoundEvent("shimmerscale");
    public static SoundEvent TITAN_BOSS = registerSoundEvent("titan_boss");


    public static SoundEvent ARK_BOSS_ICE = registerSoundEvent("ark_boss_ice");
    public static SoundEvent ARK_BOSS_LOW = registerSoundEvent("ark_boss_low");
    public static SoundEvent ARK_DESERT_TITAN = registerSoundEvent("ark_desert_titan");
    public static SoundEvent ARK_FOREST_TITAN = registerSoundEvent("ark_forest_titan");
    public static SoundEvent ARK_TITAN = registerSoundEvent("titan");
    public static SoundEvent ARK_ENDING = registerSoundEvent("ark_ending");
    public static SoundEvent ARK_BOSS_ELDER = registerSoundEvent("ark_boss_elder");


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(WorldOfDragonsMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds(){
        WorldOfDragonsMod.LOGGER.info("Registering sounds for " + WorldOfDragonsMod.MOD_ID);
    }
}

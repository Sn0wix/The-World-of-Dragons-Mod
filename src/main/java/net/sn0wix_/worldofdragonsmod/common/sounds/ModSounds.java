package net.sn0wix_.worldofdragonsmod.common.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;

public class ModSounds {
    //Copirighted
    public static final SoundEvent DEATH_DUNGEON = registerSoundEvent("death_dungeon");
    public static final SoundEvent DESERT = registerSoundEvent("desert");

    //TODO rename: fallen warrior theme
    public static final SoundEvent HARBINGER_THEME = registerSoundEvent("harbinger_theme");
    //TODO rename: nether drake
    public static final SoundEvent INGIS_THEME = registerSoundEvent("ignis_theme");
    public static final SoundEvent LEVIATHAN_THEME = registerSoundEvent("leviathan_theme");
    public static final SoundEvent EARTHEN = registerSoundEvent("earthen");
    public static final SoundEvent HYDRA = registerSoundEvent("hydra");
    public static final SoundEvent MASTER_ELDER_DRAGON = registerSoundEvent("master_elder_dragon");
    public static final SoundEvent RIMESKULL = registerSoundEvent("rimeskull");
    public static final SoundEvent SHIMMERSCALE = registerSoundEvent("shimmerscale");
    public static final SoundEvent TITAN_BOSS = registerSoundEvent("titan_boss");
    public static final SoundEvent SHREK = registerSoundEvent("shrek");
    public static final SoundEvent DESERT_DUNGEON = registerSoundEvent("desert_dungeon");
    public static final SoundEvent FIRE_DUNGEON = registerSoundEvent("fire_dungeon");
    public static final SoundEvent ICE_DUNGEON = registerSoundEvent("ice_dungeon");
    public static final SoundEvent JUNGLE_DUNGEON = registerSoundEvent("jungle_dungeon");
    public static final SoundEvent SWAMP_DUNGEON = registerSoundEvent("swamp_dungeon");


    //TODO make texture pack
    public static final SoundEvent ARK_LEVIATHAN = registerSoundEvent("ark_leviathan");
    public static final SoundEvent ARK_BOSS_ICE = registerSoundEvent("ark_boss_ice");
    public static final SoundEvent ARK_BOSS_LOW = registerSoundEvent("ark_boss_low");
    public static final SoundEvent ARK_FOREST_TITAN = registerSoundEvent("ark_forest_titan");
    public static final SoundEvent ARK_TITAN = registerSoundEvent("titan");
    public static final SoundEvent ARK_ENDING = registerSoundEvent("ark_ending");
    public static final SoundEvent ARK_BOSS_ELDER = registerSoundEvent("ark_boss_elder");


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(WorldOfDragons.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        WorldOfDragons.LOGGER.info("Registering sounds for " + WorldOfDragons.MOD_ID);
    }
}

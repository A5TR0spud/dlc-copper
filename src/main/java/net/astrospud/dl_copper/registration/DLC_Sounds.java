package net.astrospud.dl_copper.registration;

import net.astrospud.dl_copper.DL_Copper;
import net.astrospud.dl_copper.entities.MoltenCopperEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class DLC_Sounds {

    public static SoundEvent JETPACK_IGNITE;
    public static SoundEvent JETFALL_SAVE;
    public static SoundEvent JETPACK_BURN;
    public static SoundEvent JETFALL_BURN;


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(DL_Copper.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void init() {
        DL_Copper.LOGGER.info("Sounds - Registering");
        JETPACK_IGNITE = registerSoundEvent("jetpack_ignite");
        JETFALL_SAVE = registerSoundEvent("jetfall_save");
        JETPACK_BURN = registerSoundEvent("jetpack_burn");
        JETFALL_BURN = registerSoundEvent("jetfall_burn");
        DL_Copper.LOGGER.info("Sounds - Registered");
    }
}

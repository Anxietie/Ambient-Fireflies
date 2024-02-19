package com.mod.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static com.mod.AmbientFireflies.MODID;

public class SoundRegister {
	public static final SoundEvent FIREFLY_CATCH = SoundEvent.of(new Identifier(MODID, "firefly_catch"));
	public static final SoundEvent FIREFLY_RELEASE = SoundEvent.of(new Identifier(MODID, "firefly_release"));
	public static final SoundEvent FIREFLY_DEATH = SoundEvent.of(new Identifier(MODID, "firefly_death"));
	public static final SoundEvent FIREFLY_AMBIENT = SoundEvent.of(new Identifier(MODID, "firefly_ambient"));

	public static void registerSounds() {
		register("firefly_catch", FIREFLY_CATCH);
		register("firefly_release", FIREFLY_RELEASE);
		register("firefly_death", FIREFLY_DEATH);
		register("firefly_ambient", FIREFLY_AMBIENT);
	}

	private static SoundEvent register(String id, SoundEvent soundEvent) {
		return Registry.register(Registries.SOUND_EVENT, new Identifier(MODID, id), soundEvent);
	}
}

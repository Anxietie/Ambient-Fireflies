package com.mod;

import com.mod.registry.EntityRegister;
import com.mod.registry.ItemRegister;
import com.mod.registry.ParticleRegister;
import com.mod.registry.SoundRegister;
import com.mod.world.gen.ModEntityGeneration;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmbientFireflies implements ModInitializer {
	public static final String MODID = "anxff";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Fireflies initializing");

		EntityRegister.registerEntities();
		ModEntityGeneration.addSpawns();
		LOGGER.info("Fireflies entities registered");

		ItemRegister.registerItems();
		ItemRegister.registerItemGroups();
		LOGGER.info("Fireflies items registered");

		SoundRegister.registerSounds();
		LOGGER.info("Fireflies sounds registered");

		ParticleRegister.registerParticles();
		LOGGER.info("Fireflies particles registered");
	}
}
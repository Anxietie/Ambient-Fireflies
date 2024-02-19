package com.mod.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.mod.AmbientFireflies.MODID;

public class ParticleRegister {
	public static final DefaultParticleType FIREFLY_EMISSION = FabricParticleTypes.simple();

	public static void registerParticles() {
		register("firefly_emission", FIREFLY_EMISSION);
	}

	private static DefaultParticleType register(String id, DefaultParticleType particleType) {
		return Registry.register(Registries.PARTICLE_TYPE, new Identifier(MODID, id), particleType);
	}
}

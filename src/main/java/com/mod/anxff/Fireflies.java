package com.mod.anxff;

import com.mod.anxff.registry.EntityRegister;
import com.mod.anxff.registry.ItemRegister;
import com.mod.anxff.registry.ParticleRegister;
import com.mod.anxff.registry.SoundRegister;
import com.mod.anxff.world.gen.ModEntityGeneration;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fireflies implements ModInitializer {
    public static final String MODID = "anxff";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        LOGGER.info("Fireflies initializing");

        EntityRegister.registerEntities();
        ModEntityGeneration.addSpawns();
        LOGGER.info("Fireflies entities registered");

        ItemRegister.registerItems();
        LOGGER.info("Fireflies items registered");

        SoundRegister.registerSounds();
        LOGGER.info("Fireflies sounds registered");

        ParticleRegister.registerParticles();
        LOGGER.info("Fireflies particles registered");
    }
}

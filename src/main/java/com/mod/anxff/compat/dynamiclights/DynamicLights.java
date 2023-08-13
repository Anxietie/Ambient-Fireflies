package com.mod.anxff.compat.dynamiclights;

import com.mod.anxff.registry.EntityRegister;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandler;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;

public class DynamicLights implements DynamicLightsInitializer {
    public void onInitializeDynamicLights() {
        DynamicLightHandlers.registerDynamicLightHandler(EntityRegister.FIREFLY, DynamicLightHandler.makeHandler((firefly) ->
            switch (firefly.getLuminance()) {
                default -> 0;
                case 1 -> 4;
                case 2 -> 6;
                case 3 -> 8;
            }, firefly -> false));
    }
}

package com.mod.anxff.client;

import com.mod.anxff.client.particle.FireflyParticle;
import com.mod.anxff.client.render.entity.FireflyEntityRenderer;
import com.mod.anxff.client.render.entity.model.ModEntityModelLayers;
import com.mod.anxff.client.render.entity.model.FireflyEntityModel;
import com.mod.anxff.registry.EntityRegister;
import com.mod.anxff.registry.ParticleRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FirefliesClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.FIREFLY, FireflyEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityRegister.FIREFLY, FireflyEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ParticleRegister.FIREFLY_EMISSION, FireflyParticle.Factory::new);
    }
}

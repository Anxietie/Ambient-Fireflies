package com.mod;

import com.mod.particle.FireflyParticle;
import com.mod.registry.EntityRegister;
import com.mod.registry.ParticleRegister;
import com.mod.render.entity.model.FireflyEntityModel;
import com.mod.render.entity.model.ModEntityModelLayers;
import com.mod.render.entity.FireflyEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class AmbientFirefliesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.FIREFLY, FireflyEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(EntityRegister.FIREFLY, FireflyEntityRenderer::new);

		ParticleFactoryRegistry.getInstance().register(ParticleRegister.FIREFLY_EMISSION, FireflyParticle.Factory::new);
	}
}
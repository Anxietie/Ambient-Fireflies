package com.mod.render.entity.model;

import com.google.common.collect.Sets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import java.util.Set;

import static com.mod.AmbientFireflies.MODID;

@Environment(EnvType.CLIENT)
public class ModEntityModelLayers {
	private static final String MAIN = "main";
	private static final Set<EntityModelLayer> LAYERS = Sets.newHashSet();
	public static final EntityModelLayer FIREFLY = registerMain("firefly");

	private static EntityModelLayer registerMain(String id) {
		return register(id, MAIN);
	}

	private static EntityModelLayer register(String id, String layer) {
		EntityModelLayer entityModelLayer = create(id, layer);
		if (!LAYERS.add(entityModelLayer)) {
			throw new IllegalStateException("Duplicate registration for " + entityModelLayer);
		}
		return entityModelLayer;
	}

	private static EntityModelLayer create(String id, String layer) {
		return new EntityModelLayer(new Identifier(MODID, id), layer);
	}
}

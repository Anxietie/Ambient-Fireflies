package com.mod.mixin.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

// took this straight from https://github.com/Ladysnake/Illuminations/blob/main/src/main/java/ladysnake/illuminations/mixin/RenderLayerAccessor.java
@Mixin(RenderLayer.class)
public interface RenderLayerMixin {
	@Invoker
	static RenderLayer.MultiPhase invokeOf(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, RenderLayer.MultiPhaseParameters phases) {
		throw new AssertionError();
	}
}

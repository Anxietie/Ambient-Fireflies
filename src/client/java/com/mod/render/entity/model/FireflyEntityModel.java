package com.mod.render.entity.model;

import com.mod.entity.FireflyEntity;
import com.mod.render.GlowRenderLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class FireflyEntityModel extends SinglePartEntityModel<FireflyEntity> {
	private final ModelPart root;
	private final ModelPart bone;

	public FireflyEntityModel(ModelPart root) {
		super(GlowRenderLayer::get);
		this.root = root;
		this.bone = root.getChild("bone");
	}

	@Override
	public ModelPart getPart() { return this.root; }

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 2).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 1.0F));
		return TexturedModelData.of(modelData, 4, 4);
	}

	@Override
	public void setAngles(FireflyEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bone.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}

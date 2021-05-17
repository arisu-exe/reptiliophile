// Made with Blockbench 3.8.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

package io.github.fallOut015.reptiliophile.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.fallOut015.reptiliophile.entity.passive.IguanaEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IguanaModel<T extends IguanaEntity> extends EntityModel<T> {
	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer tail;
	private final ModelRenderer upperLeftFoot;
	private final ModelRenderer lowerLeftFoot;
	private final ModelRenderer upperRightFoot;
	private final ModelRenderer lowerRightFoot;
	private final ModelRenderer cube_r1;

	public IguanaModel() {
		texWidth = 64;
		texHeight = 32;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 22.5F, -4.0F);
		head.texOffs(0, 12).addBox(-1.5F, -3.5F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		head.texOffs(12, 12).addBox(0.0F, -4.5F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, false);
		head.texOffs(18, 12).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 24.0F, 0.0F);
		body.texOffs(0, 0).addBox(-2.0F, -5.0F, -4.0F, 4.0F, 4.0F, 8.0F, 0.0F, false);
		body.texOffs(24, 0).addBox(0.0F, -7.0F, -4.0F, 0.0F, 2.0F, 7.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setPos(0.0F, 24.0F, 4.0F);
		tail.texOffs(0, 18).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 4.0F, 0.0F, false);
		tail.texOffs(12, 18).addBox(-0.5F, -2.0F, 4.0F, 1.0F, 2.0F, 4.0F, 0.0F, false);
		tail.texOffs(22, 18).addBox(0.0F, -1.0F, 8.0F, 0.0F, 1.0F, 3.0F, 0.0F, false);
		tail.texOffs(28, 18).addBox(-1.0F, -4.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		upperLeftFoot = new ModelRenderer(this);
		upperLeftFoot.setPos(0.75F, 22.0F, -2.0F);
		setRotationAngle(upperLeftFoot, 0.0F, 0.7854F, 0.6109F);
		upperLeftFoot.texOffs(36, 0).addBox(0.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);

		lowerLeftFoot = new ModelRenderer(this);
		lowerLeftFoot.setPos(2.0F, 23.0F, 1.0F);
		setRotationAngle(lowerLeftFoot, 0.0F, -0.7854F, 0.6109F);
		lowerLeftFoot.texOffs(36, 0).addBox(0.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);

		upperRightFoot = new ModelRenderer(this);
		upperRightFoot.setPos(-0.75F, 22.0F, -2.0F);
		setRotationAngle(upperRightFoot, 0.0F, 0.7854F, -0.6109F);

		lowerRightFoot = new ModelRenderer(this);
		lowerRightFoot.setPos(-2.0F, 23.0F, 1.0F);
		setRotationAngle(lowerRightFoot, 0.0F, -0.7854F, -0.6109F);
		lowerRightFoot.texOffs(36, 3).addBox(0.0F, -1.0F, 0.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(1.0F, 0.0F, 2.0F);
		upperRightFoot.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 3.1416F, 0.0F);
		cube_r1.texOffs(36, 3).addBox(1.0F, -1.0F, 2.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.lowerLeftFoot.yRot = MathHelper.cos(limbSwing * 3.6662F) * 1.4F * limbSwingAmount - 0.7854F;
		this.lowerRightFoot.yRot = MathHelper.sin(limbSwing * 3.6662F + (float)Math.PI) * 1.4F * limbSwingAmount - 0.7854F;
		this.upperLeftFoot.yRot = MathHelper.sin(limbSwing * 3.6662F) * 1.4F * limbSwingAmount + 0.7854F;
		this.upperRightFoot.yRot = MathHelper.cos(limbSwing * 3.6662F + (float)Math.PI) * 1.4F * limbSwingAmount + 0.7854F;
		this.tail.yRot = MathHelper.sin(limbSwing) * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		tail.render(matrixStack, buffer, packedLight, packedOverlay);
		upperLeftFoot.render(matrixStack, buffer, packedLight, packedOverlay);
		lowerLeftFoot.render(matrixStack, buffer, packedLight, packedOverlay);
		upperRightFoot.render(matrixStack, buffer, packedLight, packedOverlay);
		lowerRightFoot.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
package io.github.fallOut015.reptiliophile.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.fallOut015.reptiliophile.entity.passive.BeardedDragonEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeardedDragonModel<T extends BeardedDragonEntity> extends EntityModel<T> {
    public ModelRenderer head;
    public ModelRenderer bodyMiddle;
    public ModelRenderer tailFront;
    public ModelRenderer tongueA;
    public ModelRenderer bodyTop;
    public ModelRenderer legTopRight;
    public ModelRenderer legTopLeft;
    public ModelRenderer legBottomLeft;
    public ModelRenderer legBottomRight;
    public ModelRenderer legTopRightFoot;
    public ModelRenderer legTopLeftFoot;
    public ModelRenderer legBottomLeftFoot;
    public ModelRenderer legBottomRightFoot;
    public ModelRenderer tailMiddleA;
    public ModelRenderer tailMiddleB;
    public ModelRenderer tailEnd;

    public BeardedDragonModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.tongueA = new ModelRenderer(this, 0, 0);
        this.tongueA.setPos(0.0F, 0.0F, -2.0F);
        this.tongueA.addBox(-0.5F, 0.0F, -1.0F, 1, 0.1f, 2, 0.0F);
        this.tailFront = new ModelRenderer(this, 16, 0);
        this.tailFront.setPos(0.0F, 23.5F, 2.9F);
        this.tailFront.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 4, 0.0F);
        this.setRotateAngle(tailFront, 0.08726646259971647F, 0.0F, 0.0F);
        this.legTopRight = new ModelRenderer(this, 32, 0);
        this.legTopRight.setPos(-2.3F, 0.5F, -2.5F);
        this.legTopRight.addBox(-1.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(legTopRight, 0.0F, -0.7853981633974483F, 0.0F);
        this.legBottomRightFoot = new ModelRenderer(this, 0, 0);
        this.legBottomRightFoot.setPos(1.5F, 0.3F, 0.0F);
        this.legBottomRightFoot.addBox(-0.5F, 0.0F, -0.5F, 1, 0.1f, 1, 0.0F);
        this.tailMiddleA = new ModelRenderer(this, 54, 0);
        this.tailMiddleA.mirror = true;
        this.tailMiddleA.setPos(0.0F, -0.2F, 4.8F);
        this.tailMiddleA.addBox(-0.5F, -0.5F, -1.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(tailMiddleA, 0.17453292519943295F, 0.0F, 0.0F);
        this.legBottomLeftFoot = new ModelRenderer(this, 0, 0);
        this.legBottomLeftFoot.setPos(1.5F, 0.3F, 0.0F);
        this.legBottomLeftFoot.addBox(-0.5F, 0.0F, -0.5F, 1, 0.1f, 1, 0.0F);
        this.tailMiddleB = new ModelRenderer(this, 16, 0);
        this.tailMiddleB.mirror = true;
        this.tailMiddleB.setPos(0.0F, -0.2F, 1.3F);
        this.tailMiddleB.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(tailMiddleB, 0.3490658503988659F, 0.0F, 0.0F);
        this.legTopRightFoot = new ModelRenderer(this, 0, 0);
        this.legTopRightFoot.setPos(-1.5F, 0.3F, 0.0F);
        this.legTopRightFoot.addBox(-0.5F, 0.0F, -0.5F, 1, 0.1f, 1, 0.0F);
        this.legTopLeftFoot = new ModelRenderer(this, 0, 0);
        this.legTopLeftFoot.setPos(-1.5F, 0.3F, 0.0F);
        this.legTopLeftFoot.addBox(-0.5F, 0.0F, -0.5F, 1, 0.1f, 1, 0.0F);
        this.legTopLeft = new ModelRenderer(this, 38, 0);
        this.legTopLeft.setPos(-2.3F, 0.5F, 2.5F);
        this.legTopLeft.addBox(-1.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(legTopLeft, 0.0F, 0.7853981633974483F, 0.0F);
        this.tailEnd = new ModelRenderer(this, 24, 0);
        this.tailEnd.mirror = true;
        this.tailEnd.setPos(0.0F, -0.2F, 0.6F);
        this.tailEnd.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(tailEnd, 0.6981317007977318F, 0.0F, 0.0F);
        this.bodyTop = new ModelRenderer(this, 23, 0);
        this.bodyTop.setPos(0.0F, -1.5F, 0.0F);
        this.bodyTop.addBox(-1.0F, -0.5F, -2.5F, 2, 1, 5, 0.0F);
        this.legBottomLeft = new ModelRenderer(this, 44, 0);
        this.legBottomLeft.setPos(2.3F, 0.5F, 2.5F);
        this.legBottomLeft.addBox(-1.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(legBottomLeft, 0.0F, -0.7853981633974483F, 0.0F);
        this.bodyMiddle = new ModelRenderer(this, 2, 0);
        this.bodyMiddle.setPos(0.0F, 23.0F, 0.0F);
        this.bodyMiddle.addBox(-2.0F, -1.0F, -3.0F, 4, 2, 6, 0.0F);
        this.legBottomRight = new ModelRenderer(this, 50, 0);
        this.legBottomRight.setPos(2.3F, 0.5F, -2.5F);
        this.legBottomRight.addBox(-1.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(legBottomRight, 0.0F, 0.7853981633974483F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setPos(0.0F, 23.0F, -4.0F);
        this.head.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.head.addChild(this.tongueA);
        this.bodyMiddle.addChild(this.legTopRight);
        this.legBottomRight.addChild(this.legBottomRightFoot);
        this.tailFront.addChild(this.tailMiddleA);
        this.legBottomLeft.addChild(this.legBottomLeftFoot);
        this.tailMiddleA.addChild(this.tailMiddleB);
        this.legTopRight.addChild(this.legTopRightFoot);
        this.legTopLeft.addChild(this.legTopLeftFoot);
        this.bodyMiddle.addChild(this.legTopLeft);
        this.tailMiddleB.addChild(this.tailEnd);
        this.bodyMiddle.addChild(this.bodyTop);
        this.bodyMiddle.addChild(this.legBottomLeft);
        this.bodyMiddle.addChild(this.legBottomRight);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.legBottomLeft.yRot = MathHelper.cos(limbSwing * 3.6662F) * 1.4F * limbSwingAmount - 0.7853981633974483F;
        this.legTopRight.yRot = MathHelper.cos(limbSwing * 3.6662F + (float)Math.PI) * 1.4F * limbSwingAmount - 0.7853981633974483F;
        this.legBottomRight.yRot = MathHelper.cos(limbSwing * 3.6662F) * 1.4F * limbSwingAmount + 0.7853981633974483F;
        this.legTopLeft.yRot = MathHelper.cos(limbSwing * 3.6662F + (float)Math.PI) * 1.4F * limbSwingAmount + 0.7853981633974483F;
        this.tailFront.yRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.9F * limbSwingAmount;
    }
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.head, this.bodyMiddle, this.tailFront).forEach(modelRenderer -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    public void renderOnShoulder(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float p_228284_5_, float p_228284_6_, float p_228284_7_, float p_228284_8_, int p_228284_9_) {
        ImmutableList.of(this.head, this.bodyMiddle, this.tailFront).forEach(modelRenderer -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, 1.0f, 1.0f, 1.0f, 1.0f);
        });
    }
}
package io.github.fallOut015.reptiliophile.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.fallOut015.reptiliophile.entity.passive.ChameleonEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChameleonModel<T extends ChameleonEntity> extends AgeableModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer tailA;
    private final ModelRenderer legUpperLeftA;
    private final ModelRenderer legUpperRightA;
    private final ModelRenderer legLowerLeftA;
    private final ModelRenderer legLowerRightA;
    private final ModelRenderer fin;
    private final ModelRenderer head;
    private final ModelRenderer crownLeft;
    private final ModelRenderer crownRight;
    private final ModelRenderer leftEye;
    private final ModelRenderer rightEye;
    private final ModelRenderer tongue;
    private final ModelRenderer tailB;
    private final ModelRenderer tailC;
    private final ModelRenderer legUpperLeftB;
    private final ModelRenderer legUpperRightB;
    private final ModelRenderer legLowerLeftB;
    private final ModelRenderer legLowerRightB;

    public ChameleonModel() {
        super(true, 2.0f, 5.0f);

        this.texWidth = 64;
        this.texHeight = 32;
        this.fin = new ModelRenderer(this, 0, 8);
        this.fin.setPos(0.0F, -2.0F, 0.0F);
        this.fin.addBox(-0.5F, -0.5F, -2.0F, 1.0f, 1.0f, 4.0f);
        this.tailC = new ModelRenderer(this, 28, 0);
        this.tailC.mirror = true;
        this.tailC.setPos(0.0F, 0.5F, 3.0F);
        this.tailC.addBox(-0.5F, -1.0F, -1.0F, 1.0f, 2.0f, 2.0f);
        this.tongue = new ModelRenderer(this, 2, 20);
        this.tongue.mirror = true;
        this.tongue.setPos(0.0F, 0.0F, -2.0F);
        this.tongue.addBox(-0.5F, 0.0F, -1.0F, 1.0f, 0.1f, 2.0f);
        this.legUpperRightA = new ModelRenderer(this, 18, 9);
        this.legUpperRightA.setPos(-0.7F, 23.1F, -1.3F);
        this.legUpperRightA.addBox(-0.5F, -0.5F, -1.0F, 1.0f, 1.0f, 2.0f);
        this.setRotationOffset(legUpperRightA, 0.2617993877991494F, -0.4363323129985824F, 0.2617993877991494F);
        this.legUpperRightB = new ModelRenderer(this, 24, 10);
        this.legUpperRightB.setPos(-0.9F, 0.4F, 0.5F);
        this.legUpperRightB.addBox(-1.0F, -0.5F, -0.5F, 2.0f, 1.0f, 1.0f);
        this.setRotationOffset(legUpperRightB, 0.0F, 0.0F, -0.6108652381980153F);
        this.rightEye = new ModelRenderer(this, 4, 18);
        this.rightEye.setPos(-0.6F, -0.4F, 0.0F);
        this.rightEye.addBox(-0.5F, -0.5F, -0.5F, 1.0f, 1.0f, 1.0f);
        this.legLowerRightA = new ModelRenderer(this, 18, 15);
        this.legLowerRightA.setPos(-1.0F, 23.2F, 1.3F);
        this.legLowerRightA.addBox(-0.5F, -0.5F, -1.0F, 1.0f, 1.0f, 2.0f);
        this.setRotationOffset(legLowerRightA, -0.2617993877991494F, 0.2617993877991494F, 0.0F);
        this.legLowerRightB = new ModelRenderer(this, 24, 16);
        this.legLowerRightB.setPos(-0.5F, 0.1F, -0.5F);
        this.legLowerRightB.addBox(-1.0F, -0.5F, -0.5F, 2.0f, 1.0f, 1.0f);
        this.setRotationOffset(legLowerRightB, 0.0F, 0.0F, -0.2617993877991494F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 22.5F, 0.0F);
        this.body.addBox(-1.0F, -1.5F, -2.5F, 2.0f, 3.0f, 5.0f);
        this.leftEye = new ModelRenderer(this, 0, 18);
        this.leftEye.setPos(0.6F, -0.4F, 0.0F);
        this.leftEye.addBox(-0.5F, -0.5F, -0.5F, 1.0f, 1.0f, 1.0f);
        this.crownRight = new ModelRenderer(this, 2, 17);
        this.crownRight.setPos(0.4F, -1.5F, 0.75F);
        this.crownRight.addBox(-0.5F, -0.5F, 0.0F, 1.0f, 1.0f, 0.0f);
        this.setRotationOffset(crownRight, 0.0F, 0.5235987755982988F, 0.0F);
        this.tailB = new ModelRenderer(this, 22, 0);
        this.tailB.mirror = true;
        this.tailB.setPos(0.0F, 0.5F, 0.8F);
        this.tailB.addBox(-0.5F, -0.5F, 0.0F, 1.0f, 1.0f, 2.0f);
        this.setRotationOffset(tailB, 0.4363323129985824F, 0.0F, 0.0F);
        this.legLowerLeftA = new ModelRenderer(this, 18, 12);
        this.legLowerLeftA.setPos(1.0F, 23.2F, 1.3F);
        this.legLowerLeftA.addBox(-0.5F, -0.5F, -1.0F, 1.0f, 1.0f, 2.0f);
        this.setRotationOffset(legLowerLeftA, -0.2617993877991494F, -0.2617993877991494F, 0.0F);
        this.crownLeft = new ModelRenderer(this, 0, 17);
        this.crownLeft.setPos(-0.4F, -1.5F, 0.75F);
        this.crownLeft.addBox(-0.5F, -0.5F, 0.0F, 1.0f, 1.0f, 0.0f);
        this.setRotationOffset(crownLeft, 0.0F, -0.5235987755982988F, 0.0F);
        this.legUpperLeftA = new ModelRenderer(this, 18, 6);
        this.legUpperLeftA.setPos(0.7F, 23.1F, -1.3F);
        this.legUpperLeftA.addBox(-0.5F, -0.5F, -1.0F, 1.0f, 1.0f, 2.0f);
        this.setRotationOffset(legUpperLeftA, 0.2617993877991494F, 0.4363323129985824F, -0.2617993877991494F);
        this.legLowerLeftB = new ModelRenderer(this, 24, 13);
        this.legLowerLeftB.setPos(0.5F, 0.1F, -0.5F);
        this.legLowerLeftB.addBox(-1.0F, -0.5F, -0.5F, 2.0f, 1.0f, 1.0f);
        this.setRotationOffset(legLowerLeftB, 0.0F, 0.0F, 0.2617993877991494F);
        this.legUpperLeftB = new ModelRenderer(this, 24, 7);
        this.legUpperLeftB.setPos(0.9F, 0.4F, 0.5F);
        this.legUpperLeftB.addBox(-1.0F, -0.5F, -0.5F, 2.0f, 1.0f, 1.0f);
        this.setRotationOffset(legUpperLeftB, 0.0F, 0.0F, 0.6108652381980153F);
        this.tailA = new ModelRenderer(this, 16, 0);
        this.tailA.mirror = true;
        this.tailA.setPos(0.0F, 23.0F, 3.0F);
        this.tailA.addBox(-0.5F, -1.0F, -1.0F, 1.0f, 2.0f, 2.0f);
        this.head = new ModelRenderer(this, 0, 13);
        this.head.mirror = true;
        this.head.setPos(0.0F, 0.0F, -3.5F);
        this.head.addBox(-1.0F, -1.0F, -1.0F, 2.0f, 2.0f, 2.0f);
        this.body.addChild(this.fin);
        this.tailB.addChild(this.tailC);
        this.head.addChild(this.tongue);
        this.legUpperRightA.addChild(this.legUpperRightB);
        this.head.addChild(this.rightEye);
        this.legLowerRightA.addChild(this.legLowerRightB);
        this.head.addChild(this.leftEye);
        this.head.addChild(this.crownRight);
        this.tailA.addChild(this.tailB);
        this.head.addChild(this.crownLeft);
        this.legLowerLeftA.addChild(this.legLowerLeftB);
        this.legUpperLeftA.addChild(this.legUpperLeftB);
        this.body.addChild(this.head);
    }

    private void setRotationOffset(ModelRenderer renderer, float x, float y, float z) {
        renderer.xRot = x;
        renderer.yRot = y;
        renderer.zRot = z;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.legUpperRightA, this.legLowerRightA, this.body, this.legLowerLeftA, this.legUpperLeftA, this.tailA).forEach(modelRenderer -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.legLowerLeftA.yRot = MathHelper.cos(limbSwing * 3.6662F) * 1.4F * limbSwingAmount - 0.2617993877991494F;
        this.legUpperRightA.yRot = MathHelper.cos(limbSwing * 3.6662F + (float)Math.PI) * 1.4F * limbSwingAmount - 0.4363323129985824F;
        this.legLowerRightA.yRot = MathHelper.cos(limbSwing * 3.6662F) * 1.4F * limbSwingAmount + 0.2617993877991494F;
        this.legUpperLeftA.yRot = MathHelper.cos(limbSwing * 3.6662F + (float)Math.PI) * 1.4F * limbSwingAmount + 0.4363323129985824F;
        this.tailA.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.9F * limbSwingAmount;
    }
    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }
    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.legUpperRightA, this.legLowerRightA, this.body, this.legLowerLeftA, this.legUpperLeftA, this.tailA);
    }

    public void renderOnShoulder(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float p_228284_5_, float p_228284_6_, float p_228284_7_, float p_228284_8_, int p_228284_9_) {
//		this.setLivingAnimations(ChameleonModel.State.ON_SHOULDER);
//		this.setRotationAngles(ChameleonModel.State.ON_SHOULDER, p_228284_9_, p_228284_5_, p_228284_6_, 0.0F, p_228284_7_, p_228284_8_);
        ImmutableList.of(this.legUpperRightA, this.legLowerRightA, this.body, this.legLowerLeftA, this.legUpperLeftA, this.tailA).forEach(modelRenderer -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static enum State {
        OFF_SHOULDER,
        ON_SHOULDER
    }
}
package io.github.fallOut015.reptiliophile.client.renderer.entity.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.fallOut015.reptiliophile.client.renderer.entity.BeardedDragonRenderer;
import io.github.fallOut015.reptiliophile.client.renderer.entity.model.BeardedDragonModel;
import io.github.fallOut015.reptiliophile.entity.EntitiesReptiliophile;
import io.github.fallOut015.reptiliophile.entity.passive.BeardedDragonEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeardedDragonLayer <T extends PlayerEntity> extends LayerRenderer<T, PlayerModel<T>> {
    private final BeardedDragonModel<BeardedDragonEntity> beardedDragonModel = new BeardedDragonModel<BeardedDragonEntity>();

    public BeardedDragonLayer(IEntityRenderer<T, PlayerModel<T>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.renderBeardedDragon(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch, true);
        this.renderBeardedDragon(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch, false);
    }

    private void renderBeardedDragon(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch, boolean leftShoulderIn) {
        CompoundNBT compoundnbt = leftShoulderIn ? entitylivingbaseIn.getShoulderEntityLeft() : entitylivingbaseIn.getShoulderEntityRight();
        EntityType.byString(compoundnbt.getString("id")).filter((p_215344_0_) -> {
            return p_215344_0_ == EntitiesReptiliophile.BEARDED_DRAGON.get();
        }).ifPresent((p_229137_11_) -> {
            matrixStackIn.pushPose();
            matrixStackIn.translate(leftShoulderIn ? (double)0.4F : (double)-0.4F, entitylivingbaseIn.isCrouching() ? (double)-1.3F : -1.5D, 0.0D);

            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.beardedDragonModel.renderType(BeardedDragonRenderer.TEXTURE));
            this.beardedDragonModel.renderOnShoulder(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, limbSwing, limbSwingAmount, netHeadYaw, headPitch, entitylivingbaseIn.tickCount);
            matrixStackIn.popPose();
        });
    }
}
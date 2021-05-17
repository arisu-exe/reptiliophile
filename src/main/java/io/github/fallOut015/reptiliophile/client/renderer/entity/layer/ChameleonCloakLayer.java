package io.github.fallOut015.reptiliophile.client.renderer.entity.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.fallOut015.reptiliophile.client.renderer.entity.model.ChameleonCloakModel;
import io.github.fallOut015.reptiliophile.item.ItemsReptiliophile;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChameleonCloakLayer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    private static final ResourceLocation TEXTURE_CHAMELEON_CLOAK = new ResourceLocation("reptiliophile", "textures/entity/chameleon_cloak.png");
    private final ChameleonCloakModel<T> modelChameleonCloak = new ChameleonCloakModel<>();

    public ChameleonCloakLayer(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entitylivingbaseIn.isInvisible()) {
            return;
        }

        ItemStack itemstack = entitylivingbaseIn.getItemBySlot(EquipmentSlotType.CHEST);
        if (itemstack.getItem() == ItemsReptiliophile.CHAMELEON_CLOAK.get()) {
            ResourceLocation resourcelocation;
            if (entitylivingbaseIn instanceof AbstractClientPlayerEntity) {
                AbstractClientPlayerEntity abstractclientplayerentity = (AbstractClientPlayerEntity)entitylivingbaseIn;
                if (abstractclientplayerentity.isElytraLoaded() && abstractclientplayerentity.getElytraTextureLocation() != null) {
                    resourcelocation = abstractclientplayerentity.getElytraTextureLocation();
                } else if (abstractclientplayerentity.isCapeLoaded() && abstractclientplayerentity.getCloakTextureLocation() != null && abstractclientplayerentity.isModelPartShown(PlayerModelPart.CAPE)) {
                    resourcelocation = abstractclientplayerentity.getCloakTextureLocation();
                } else {
                    resourcelocation = TEXTURE_CHAMELEON_CLOAK;
                }
            } else {
                resourcelocation = TEXTURE_CHAMELEON_CLOAK;
            }

            matrixStackIn.pushPose();
            matrixStackIn.translate(0.0D, 0.0D, 0.125D);
            this.getParentModel().copyPropertiesTo(this.modelChameleonCloak);
            this.modelChameleonCloak.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, this.modelChameleonCloak.renderType(resourcelocation), false, itemstack.hasFoil());
            this.modelChameleonCloak.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
    }
}
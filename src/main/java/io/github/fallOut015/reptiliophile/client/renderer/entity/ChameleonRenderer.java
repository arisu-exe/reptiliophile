package io.github.fallOut015.reptiliophile.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.fallOut015.reptiliophile.client.renderer.entity.model.ChameleonModel;
import io.github.fallOut015.reptiliophile.entity.passive.ChameleonEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

import java.lang.reflect.Method;

@OnlyIn(Dist.CLIENT)
public class ChameleonRenderer extends MobRenderer<ChameleonEntity, ChameleonModel<ChameleonEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("reptiliophile", "textures/entity/chameleon.png");

    public ChameleonRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ChameleonModel<>(), 0.25f);
    }

    @SuppressWarnings("deprecation")
    ResourceLocation blockOnResource(ChameleonEntity entity, BlockState blockStateIn) {
        String location;
        try {
            TextureAtlasSprite texture = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(blockStateIn).getQuads(blockStateIn, Direction.UP, entity.getRandom()).get(0).getSprite();
            location = texture.getName().getNamespace().replaceFirst("minecraft:", "") + ":textures/" + texture.getName().getPath() + ".png";
        } catch(IndexOutOfBoundsException exception) {
            location = "reptiliophile:textures/entity/chameleon.png";
        }
        return new ResourceLocation(location);
    }

    public ResourceLocation getTextureLocation(ChameleonEntity entity) {
        if(entity.blendOff()) {
            return TEXTURE;
        } else if(entity.blend()) {
            entity.updateOn();
        }

        switch(entity.getOn().getBlock().getRegistryName().toString()) { //Had to convert to string for the switch statement.
            case "minecraft:air":
            case "minecraft:cave_air":
            case "minecraft:water":
            case "minecraft:flowing_water":
            case "minecraft:lava":
            case "minecraft:flowing_lava":
            case "minecraft:barrier":
                return TEXTURE;
            default:
                BlockState blockStateIn = entity.getOn();
                return blockOnResource(entity, blockStateIn);
        }
    }
    @Override
    public void render(ChameleonEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if(!entityIn.blendOff() && entityIn.getColor() != -1) {
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<ChameleonEntity, ChameleonModel<ChameleonEntity>>(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn))) return;
            matrixStackIn.pushPose();
            this.model.attackTime = this.getAttackAnim(entityIn, partialTicks);

            boolean shouldSit = entityIn.isPassenger() && (entityIn.getVehicle() != null && entityIn.getVehicle().shouldRiderSit());
            this.model.riding = shouldSit;
            this.model.young = entityIn.isBaby();
            float f = MathHelper.rotLerp(partialTicks, entityIn.yBodyRotO, entityIn.yBodyRot);
            float f1 = MathHelper.rotLerp(partialTicks, entityIn.yHeadRotO, entityIn.yHeadRot);
            float f2 = f1 - f;
            if (shouldSit && entityIn.getVehicle() instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entityIn.getVehicle();
                f = MathHelper.rotLerp(partialTicks, livingentity.yBodyRotO, livingentity.yBodyRot);
                f2 = f1 - f;
                float f3 = MathHelper.wrapDegrees(f2);
                if (f3 < -85.0F) {
                    f3 = -85.0F;
                }

                if (f3 >= 85.0F) {
                    f3 = 85.0F;
                }

                f = f1 - f3;
                if (f3 * f3 > 2500.0F) {
                    f += f3 * 0.2F;
                }

                f2 = f1 - f;
            }

            float f6 = MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot);
            if (entityIn.getPose() == Pose.SLEEPING) {
                Direction direction = entityIn.getBedOrientation();
                if (direction != null) {
                    float f4 = entityIn.getEyeHeight(Pose.STANDING) - 0.1F;
                    matrixStackIn.translate((double)((float)(-direction.getStepX()) * f4), 0.0D, (double)((float)(-direction.getStepZ()) * f4));
                }
            }

            float f7 = this.getBob(entityIn, partialTicks);
            this.setupRotations(entityIn, matrixStackIn, f7, f, partialTicks);
            matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
            this.scale(entityIn, matrixStackIn, partialTicks);
            matrixStackIn.translate(0.0D, (double)-1.501F, 0.0D);
            float f8 = 0.0F;
            float f5 = 0.0F;
            if (!shouldSit && entityIn.isAlive()) {
                f8 = MathHelper.lerp(partialTicks, entityIn.animationSpeedOld, entityIn.animationSpeed);
                f5 = entityIn.animationPosition - entityIn.animationSpeed * (1.0F - partialTicks);
                if (entityIn.isBaby()) {
                    f5 *= 3.0F;
                }

                if (f8 > 1.0F) {
                    f8 = 1.0F;
                }
            }

            this.model.prepareMobModel(entityIn, f5, f8, partialTicks);
            this.model.setupAnim(entityIn, f5, f8, f7, f2, f6);
            boolean flag = this.isBodyVisible(entityIn);
            boolean flag1 = !flag && !entityIn.isInvisibleTo(Minecraft.getInstance().player);
            if(entityIn.getOn().getBlock().getTags().contains(Tags.Blocks.GLASS.getName())) {
                flag1 = true;
            }
            RenderType rendertype = this.getRenderType(entityIn, flag, flag1, false); // update render code, the fourth "false" should be flag2 which controls outlines
            if (rendertype != null) {
                IVertexBuilder ivertexbuilder = bufferIn.getBuffer(rendertype);
                int i = getOverlayCoords(entityIn, this.getWhiteOverlayProgress(entityIn, partialTicks));
                float red = (float)(entityIn.getColor() >> 16 & 255) / 255.0F;
                float green = (float)(entityIn.getColor() >> 8 & 255) / 255.0F;
                float blue = (float)(entityIn.getColor() & 255) / 255.0F;
                this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, i, red, green, blue, flag1 ? 0.15F : 1.0F);
            }

            if (!entityIn.isSpectator()) {
                for(LayerRenderer<ChameleonEntity, ChameleonModel<ChameleonEntity>> layerrenderer : this.layers) {
                    layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f8, partialTicks, f7, f2, f6);
                }
            }

            matrixStackIn.popPose();
            net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(entityIn, entityIn.getDisplayName().plainCopy(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
            if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.shouldShowName(entityIn))) {
                this.renderNameTag(entityIn, renderNameplateEvent.getContent(), matrixStackIn, bufferIn, packedLightIn);
            }
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<ChameleonEntity, ChameleonModel<ChameleonEntity>>(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn));

            Entity entity = entityIn.getLeashHolder();
            if (entity != null) {
                try {
                    Method renderLeash = MobRenderer.class.getDeclaredMethod("renderLeash", Object.class, float.class, MatrixStack.class, IRenderTypeBuffer.class, Object.class);
                    renderLeash.setAccessible(true);
                    renderLeash.invoke((MobRenderer<ChameleonEntity, ChameleonModel<ChameleonEntity>>) this, entityIn, partialTicks, matrixStackIn, bufferIn, entity);
                    renderLeash.setAccessible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }
}
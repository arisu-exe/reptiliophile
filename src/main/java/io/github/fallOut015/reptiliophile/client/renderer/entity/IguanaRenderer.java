package io.github.fallOut015.reptiliophile.client.renderer.entity;

import io.github.fallOut015.reptiliophile.client.renderer.entity.model.BeardedDragonModel;
import io.github.fallOut015.reptiliophile.client.renderer.entity.model.IguanaModel;
import io.github.fallOut015.reptiliophile.entity.passive.BeardedDragonEntity;
import io.github.fallOut015.reptiliophile.entity.passive.IguanaEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IguanaRenderer extends MobRenderer<IguanaEntity, IguanaModel<IguanaEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("reptiliophile", "textures/entity/iguana.png");

    public IguanaRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new IguanaModel<>(), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(IguanaEntity entity) {
        return TEXTURE;
    }
}
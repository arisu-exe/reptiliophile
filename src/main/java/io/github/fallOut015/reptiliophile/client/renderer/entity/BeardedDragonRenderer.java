package io.github.fallOut015.reptiliophile.client.renderer.entity;

import io.github.fallOut015.reptiliophile.client.renderer.entity.model.BeardedDragonModel;
import io.github.fallOut015.reptiliophile.entity.passive.BeardedDragonEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeardedDragonRenderer extends MobRenderer<BeardedDragonEntity, BeardedDragonModel<BeardedDragonEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("reptiliophile", "textures/entity/bearded_dragon.png");

    public BeardedDragonRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BeardedDragonModel<>(), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(BeardedDragonEntity entity) {
        return TEXTURE;
    }
}
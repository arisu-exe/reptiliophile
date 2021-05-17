package io.github.fallOut015.reptiliophile.client.registry;

import io.github.fallOut015.reptiliophile.client.renderer.entity.BeardedDragonRenderer;
import io.github.fallOut015.reptiliophile.client.renderer.entity.ChameleonRenderer;
import io.github.fallOut015.reptiliophile.client.renderer.entity.IguanaRenderer;
import io.github.fallOut015.reptiliophile.entity.EntitiesReptiliophile;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class RenderingRegistryReptiliophile {
    public static void doClientStuff(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntitiesReptiliophile.BEARDED_DRAGON.get(), BeardedDragonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitiesReptiliophile.CHAMELEON.get(), ChameleonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitiesReptiliophile.IGUANA.get(), IguanaRenderer::new);
    }
}
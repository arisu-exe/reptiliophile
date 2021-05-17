package io.github.fallOut015.reptiliophile.client.renderer.color;

import io.github.fallOut015.reptiliophile.item.SpawnEggItemReptiliophile;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ItemColorsReptiliophile {
    public static void doClientStuff(final FMLClientSetupEvent event) {
        for(SpawnEggItemReptiliophile spawneggitemreptiliophile : SpawnEggItemReptiliophile.getEggs()) {
            Minecraft.getInstance().getItemColors().register((stack, color) -> spawneggitemreptiliophile.getColor(color), spawneggitemreptiliophile);
        }
    }
}
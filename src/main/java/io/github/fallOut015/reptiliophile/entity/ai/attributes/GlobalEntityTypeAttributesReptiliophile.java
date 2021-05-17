package io.github.fallOut015.reptiliophile.entity.ai.attributes;

import io.github.fallOut015.reptiliophile.entity.EntitiesReptiliophile;
import io.github.fallOut015.reptiliophile.entity.passive.BeardedDragonEntity;
import io.github.fallOut015.reptiliophile.entity.passive.ChameleonEntity;
import io.github.fallOut015.reptiliophile.entity.passive.IguanaEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class GlobalEntityTypeAttributesReptiliophile {
    public static void setup(final FMLCommonSetupEvent event) {
        GlobalEntityTypeAttributes.put(EntitiesReptiliophile.BEARDED_DRAGON.get(), BeardedDragonEntity.applyAttributes().build());
        GlobalEntityTypeAttributes.put(EntitiesReptiliophile.CHAMELEON.get(), ChameleonEntity.applyAttributes().build());
        GlobalEntityTypeAttributes.put(EntitiesReptiliophile.IGUANA.get(), IguanaEntity.applyAttributes().build());
    }
}
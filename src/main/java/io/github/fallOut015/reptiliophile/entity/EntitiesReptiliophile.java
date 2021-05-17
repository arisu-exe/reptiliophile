package io.github.fallOut015.reptiliophile.entity;

import io.github.fallOut015.reptiliophile.MainReptiliophile;
import io.github.fallOut015.reptiliophile.entity.passive.BeardedDragonEntity;
import io.github.fallOut015.reptiliophile.entity.passive.ChameleonEntity;
import io.github.fallOut015.reptiliophile.entity.passive.IguanaEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntitiesReptiliophile {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MainReptiliophile.MODID);



    public static final RegistryObject<EntityType<BeardedDragonEntity>> BEARDED_DRAGON = ENTITIES.register("bearded_dragon", () -> EntityType.Builder.of(BeardedDragonEntity::new, EntityClassification.CREATURE).sized(0.2f, 0.2f).build("bearded_dragon"));
    public static final RegistryObject<EntityType<ChameleonEntity>> CHAMELEON = ENTITIES.register("chameleon", () -> EntityType.Builder.of(ChameleonEntity::new, EntityClassification.CREATURE).sized(0.25f, 0.25f).build("chameleon"));
    public static final RegistryObject<EntityType<IguanaEntity>> IGUANA = ENTITIES.register("iguana", () -> EntityType.Builder.of(IguanaEntity::new, EntityClassification.CREATURE).sized(0.5f, 0.3f).build("iguana"));



    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
package io.github.fallOut015.reptiliophile;

import io.github.fallOut015.reptiliophile.client.registry.RenderingRegistryReptiliophile;
import io.github.fallOut015.reptiliophile.client.renderer.color.ItemColorsReptiliophile;
import io.github.fallOut015.reptiliophile.client.renderer.entity.layer.BeardedDragonLayer;
import io.github.fallOut015.reptiliophile.client.renderer.entity.layer.ChameleonCloakLayer;
import io.github.fallOut015.reptiliophile.client.renderer.entity.layer.ChameleonLayer;
import io.github.fallOut015.reptiliophile.entity.EntitiesReptiliophile;
import io.github.fallOut015.reptiliophile.entity.ai.attributes.GlobalEntityTypeAttributesReptiliophile;
import io.github.fallOut015.reptiliophile.entity.passive.BeardedDragonEntity;
import io.github.fallOut015.reptiliophile.entity.passive.ChameleonEntity;
import io.github.fallOut015.reptiliophile.item.ItemsReptiliophile;
import io.github.fallOut015.reptiliophile.item.SpawnEggItemReptiliophile;
import io.github.fallOut015.reptiliophile.util.DamageSourceReptiliophile;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MainReptiliophile.MODID)
public class MainReptiliophile {
    public static final String MODID = "reptiliophile";

    public MainReptiliophile() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);

        ItemsReptiliophile.register(FMLJavaModLoadingContext.get().getModEventBus());
        EntitiesReptiliophile.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final FMLCommonSetupEvent event) {
        GlobalEntityTypeAttributesReptiliophile.setup(event);
    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        // TODO move to own file
        // Chameleon Shoulder
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default").addLayer(new ChameleonLayer<>(Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default")));
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim").addLayer(new ChameleonLayer<>(Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim")));

        // Bearded Dragon Shoulder
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default").addLayer(new BeardedDragonLayer<>(Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default")));
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim").addLayer(new BeardedDragonLayer<>(Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim")));

        // Chameleon Cloak
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default").addLayer(new ChameleonCloakLayer<>(Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default")));
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim").addLayer(new ChameleonCloakLayer<>(Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("slim")));

        RenderingRegistryReptiliophile.doClientStuff(event);
        ItemColorsReptiliophile.doClientStuff(event);
    }
    private void enqueueIMC(final InterModEnqueueEvent event) {
    }
    private void processIMC(final InterModProcessEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber
    public static class Events {
    	@SubscribeEvent
    	public static void onBiomeLoad(final BiomeLoadingEvent biomeLoadingEvent) {
            if(biomeLoadingEvent.getCategory() == Biome.Category.DESERT) {
                biomeLoadingEvent.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(EntitiesReptiliophile.CHAMELEON.get(), 10, 1, 2));
            } else if(biomeLoadingEvent.getCategory() == Biome.Category.JUNGLE) {
                biomeLoadingEvent.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(EntitiesReptiliophile.CHAMELEON.get(), 12, 2, 4));
                biomeLoadingEvent.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(EntitiesReptiliophile.IGUANA.get(), 24, 2, 4));
            } else if(biomeLoadingEvent.getCategory() == Biome.Category.SAVANNA) {
                biomeLoadingEvent.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(EntitiesReptiliophile.BEARDED_DRAGON.get(), 12, 2, 4));
            }
        }

        @SubscribeEvent
        public static void onEntityJoinWorld(final EntityJoinWorldEvent entityJoinWorldEvent) {
            if(entityJoinWorldEvent.getEntity().getType() == EntityType.SPIDER) {
                ((SpiderEntity) entityJoinWorldEvent.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((CreatureEntity) entityJoinWorldEvent.getEntity(), ChameleonEntity.class, 6.0f, 1.2D, 1.4D));
                ((SpiderEntity) entityJoinWorldEvent.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((CreatureEntity) entityJoinWorldEvent.getEntity(), BeardedDragonEntity.class, 6.0f, 1.2D, 1.4D));
            } else if(entityJoinWorldEvent.getEntity().getType() == EntityType.CAVE_SPIDER) {
                ((CaveSpiderEntity) entityJoinWorldEvent.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((CreatureEntity) entityJoinWorldEvent.getEntity(), ChameleonEntity.class, 6.0f, 1.2D, 1.4D));
                ((CaveSpiderEntity) entityJoinWorldEvent.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((CreatureEntity) entityJoinWorldEvent.getEntity(), BeardedDragonEntity.class, 6.0f, 1.2D, 1.4D));
            } else if(entityJoinWorldEvent.getEntity().getType() == EntityType.SILVERFISH) {
                ((SilverfishEntity) entityJoinWorldEvent.getEntity()).goalSelector.addGoal(1, new AvoidEntityGoal<>((CreatureEntity) entityJoinWorldEvent.getEntity(), ChameleonEntity.class, 6.0f, 0.5D, 0.7D));
                ((SilverfishEntity) entityJoinWorldEvent.getEntity()).goalSelector.addGoal(1, new AvoidEntityGoal<>((CreatureEntity) entityJoinWorldEvent.getEntity(), BeardedDragonEntity.class, 6.0f, 0.5D, 0.7D));
            } else if(entityJoinWorldEvent.getEntity().getType() == EntityType.ENDERMITE) {
                ((EndermiteEntity) entityJoinWorldEvent.getEntity()).goalSelector.addGoal(1, new AvoidEntityGoal<>((CreatureEntity) entityJoinWorldEvent.getEntity(), ChameleonEntity.class, 6.0f, 0.5D, 0.7D));
                ((EndermiteEntity) entityJoinWorldEvent.getEntity()).goalSelector.addGoal(1, new AvoidEntityGoal<>((CreatureEntity) entityJoinWorldEvent.getEntity(), BeardedDragonEntity.class, 6.0f, 0.5D, 0.7D));
            } else if(entityJoinWorldEvent.getEntity().getType() == EntityType.BEE) {
                ((BeeEntity) entityJoinWorldEvent.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((CreatureEntity) entityJoinWorldEvent.getEntity(), ChameleonEntity.class, 6.0f, 0.6D, 0.8D));
                ((BeeEntity) entityJoinWorldEvent.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((CreatureEntity) entityJoinWorldEvent.getEntity(), BeardedDragonEntity.class, 6.0f, 0.6D, 0.8D));
            }
        }

        @SubscribeEvent
        public static void onLivingEventLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
    	    if(event.getEntityLiving().getTags().contains("hasVenom")) {
                event.getEntityLiving().hurt(DamageSourceReptiliophile.VENOM, 1.0f);
            }
    	    // render green hearts
            // render green vignette
            // slow down player
            // prevent healing while having venom
        }
    }
}
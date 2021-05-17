package io.github.fallOut015.reptiliophile.item;

import io.github.fallOut015.reptiliophile.MainReptiliophile;
import io.github.fallOut015.reptiliophile.entity.EntitiesReptiliophile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsReptiliophile {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainReptiliophile.MODID);



    // agamid
    // anole
    public static final RegistryObject<Item> BEARDED_DRAGON_SPAWN_EGG = ITEMS.register("bearded_dragon_spawn_egg", () -> new SpawnEggItemReptiliophile(EntitiesReptiliophile.BEARDED_DRAGON::get, 14397817, 15255450, new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> CHAMELEON_SPAWN_EGG = ITEMS.register("chameleon_spawn_egg", () -> new SpawnEggItemReptiliophile(EntitiesReptiliophile.CHAMELEON::get, 2162500, 14463743, new Item.Properties().tab(ItemGroup.TAB_MISC)));
    // frilled dragon
    // gecko
    public static final RegistryObject<Item> IGUANA_SPAWN_EGG = ITEMS.register("iguana_spawn_egg", () -> new SpawnEggItemReptiliophile(EntitiesReptiliophile.IGUANA::get, 9625141, 6069813, new Item.Properties().tab(ItemGroup.TAB_MISC)));
    // komodo dragon
    // perentie
    // salamander
    // water basilisk

    public static final RegistryObject<Item> CHAMELEON_EYE = ITEMS.register("chameleon_eye", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> CHAMELEON_SKIN = ITEMS.register("chameleon_skin", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> CHAMELEON_CLOAK = ITEMS.register("chameleon_cloak", () -> new ChameleonCloakItem(new Item.Properties().tab(ItemGroup.TAB_MISC).durability(432).rarity(Rarity.UNCOMMON)));
    // frills
    public static final RegistryObject<Item> FANG = ITEMS.register("fang", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)) {
        @Override
        public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
            if(!playerIn.removeTag("hasVenom")) {
                playerIn.addTag("hasVenom");
            }
            return super.use(worldIn, playerIn, handIn);
        }
    });
    public static final RegistryObject<Item> ANTIDOTE = ITEMS.register("antidote", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    // salamander
    // basilisk feet



    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
package io.github.fallOut015.reptiliophile.item;

import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class ChameleonCloakItem extends Item implements IVanishable {
    public ChameleonCloakItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getItemInHand(handIn);
        EquipmentSlotType equipmentSlotType = MobEntity.getEquipmentSlotForItem(itemStack);
        ItemStack stack = playerIn.getItemBySlot(equipmentSlotType);
        if (stack.isEmpty()) {
            playerIn.setItemSlot(equipmentSlotType, itemStack.copy());
            itemStack.setCount(0);
            return ActionResult.success(itemStack);
        } else {
            return ActionResult.fail(itemStack);
        }
    }
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.CHEST;
    }
    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == ItemsReptiliophile.CHAMELEON_SKIN.get();
    }
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if(player.isShiftKeyDown() && !player.abilities.flying) {
            player.addEffect(new EffectInstance(Effects.INVISIBILITY, 10, 0, false, false));
        }

        super.onArmorTick(stack, world, player);
    }
}
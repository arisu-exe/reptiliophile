package io.github.fallOut015.reptiliophile.entity.passive;

import io.github.fallOut015.reptiliophile.entity.EntitiesReptiliophile;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public class BeardedDragonEntity extends ShoulderRidingEntity {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.SPIDER_EYE);

    public BeardedDragonEntity(EntityType<? extends BeardedDragonEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTame(false);
    }

    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity ageable) {
        BeardedDragonEntity beardeddragonentity = EntitiesReptiliophile.BEARDED_DRAGON.get().create(serverWorld);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            beardeddragonentity.setOwnerUUID(uuid);
            beardeddragonentity.setTame(true);
        }

        return beardeddragonentity;
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1.0D, 1.0F, 9.0F, false));
        this.goalSelector.addGoal(2, new LandOnOwnersShoulderGoal(this));
        this.goalSelector.addGoal(3, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 0.8D, 1.0000001E-5F));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 10.0F));
        this.goalSelector.addGoal(6, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(7, new TemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
    }

    public static AttributeModifierMap.MutableAttribute applyAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3d).add(Attributes.MAX_HEALTH, 5.0D);
    }

    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || item == Items.SPIDER_EYE && !this.isTame();
            return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
        } else {
            if (this.isTame()) {
                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!player.abilities.instabuild) {
                        itemstack.shrink(1);
                    }

                    this.heal((float)item.getFoodProperties().getNutrition());
                    return ActionResultType.SUCCESS;
                }

                if (!(item instanceof DyeItem)) {
                    ActionResultType actionresulttype = super.mobInteract(player, hand);
                    if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                        this.jumping = false;
                        this.navigation.stop();
                        this.setTarget((LivingEntity)null);
                        return ActionResultType.SUCCESS;
                    }

                    return actionresulttype;
                }

            } else if (item == Items.SPIDER_EYE) {
                if (!player.abilities.instabuild) {
                    itemstack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget((LivingEntity)null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }

                return ActionResultType.SUCCESS;
            }

            return super.mobInteract(player, hand);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.SPIDER_EYE;
    }
    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0D);
            this.setHealth(10.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(5.0D);
        }
    }
}
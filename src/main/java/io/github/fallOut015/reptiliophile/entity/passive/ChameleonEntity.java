package io.github.fallOut015.reptiliophile.entity.passive;

import io.github.fallOut015.reptiliophile.entity.EntitiesReptiliophile;
import io.github.fallOut015.reptiliophile.item.ItemsReptiliophile;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ChameleonEntity extends ShoulderRidingEntity {
    private static final DataParameter<Integer> CAMOUFLAGE = EntityDataManager.defineId(ChameleonEntity.class, DataSerializers.INT);
    private static final DataParameter<Optional<BlockState>> ON = EntityDataManager.defineId(ChameleonEntity.class, DataSerializers.BLOCK_STATE);
    private static final DataParameter<Integer> COLOR = EntityDataManager.defineId(ChameleonEntity.class, DataSerializers.INT);
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.defineId(ChameleonEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Boolean> RESTING = EntityDataManager.defineId(ChameleonEntity.class, DataSerializers.BOOLEAN);
    public int timeUntilNextShed = this.random.nextInt(12000) + 6000;

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE);

    public ChameleonEntity(EntityType<? extends ChameleonEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTame(false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1.5D, 1.0F, 9.0F, false));
        this.goalSelector.addGoal(2, new LandOnOwnersShoulderGoal(this));
        this.goalSelector.addGoal(3, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new ChameleonEntity.RestOnBlockGoal(this, (blockState) -> blockState.getBlock() == Blocks.SAND)); // add a light value to the predicate
        this.goalSelector.addGoal(5, new ChameleonEntity.RestOnBlockGoal(this, (blockState) -> blockState.getBlock() == Blocks.JUNGLE_LOG));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 10.0F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 0.8D, 1.0000001E-5F));
    }

    public static AttributeModifierMap.MutableAttribute applyAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double)0.15F).add(Attributes.MAX_HEALTH, 5.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(CAMOUFLAGE, 0); // 0 is for blending (default), 1 is for default chameleon texture, 2 is for remembering
        this.entityData.define(ON, Optional.empty());
        this.entityData.define(COLOR, -1); // -1 for no color overlay
        this.entityData.define(CLIMBING, (byte)0);
        this.entityData.define(RESTING, false);
    }
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity ageable) {
        ChameleonEntity chameleonentity = EntitiesReptiliophile.CHAMELEON.get().create(serverWorld);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            chameleonentity.setOwnerUUID(uuid);
            chameleonentity.setTame(true);
        }

        return chameleonentity;
    }

    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || item == Items.SPIDER_EYE && !this.isTame();
            return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
        } else {
            if (this.isTame()) {
                if(this.isSpecialItem(itemstack)) {
                    if(this.entityData.get(CAMOUFLAGE).intValue() == 2) {
                        this.entityData.set(CAMOUFLAGE, 0);
                    } else {
                        this.entityData.set(CAMOUFLAGE, this.entityData.get(CAMOUFLAGE).intValue() + 1);
                        if(this.entityData.get(CAMOUFLAGE).intValue() == 2) {
                            this.updateOn();
                        }
                    }
                    if(this.level.isClientSide) {
                        for(int i = 0; i < 7; ++i) {
                            double d0 = this.random.nextGaussian() * 0.02D;
                            double d1 = this.random.nextGaussian() * 0.02D;
                            double d2 = this.random.nextGaussian() * 0.02D;
                            this.level.addParticle(ParticleTypes.CLOUD, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
                        }
                    }
                }
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
    public boolean isSpecialItem(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.FERMENTED_SPIDER_EYE;
    }
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);

        this.entityData.set(CAMOUFLAGE, compound.getInt("camouflage"));
        this.entityData.set(ON, Optional.of(NBTUtil.readBlockState((CompoundNBT) compound.get("on"))));
        this.entityData.set(COLOR, compound.getInt("color"));

        this.entityData.set(RESTING, compound.getBoolean("resting"));
    }
    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);

        compound.putInt("camouflage", this.entityData.get(CAMOUFLAGE).intValue());
        compound.put("on", NBTUtil.writeBlockState(this.entityData.get(ON).orElse(Blocks.AIR.defaultBlockState())));
        compound.putInt("color", this.entityData.get(COLOR).intValue());

        compound.putBoolean("resting", this.entityData.get(RESTING).booleanValue());
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
    @Override
    public boolean onClimbable() {
        return this.isBesideClimbableBlock();
    }
    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            this.setBesideClimbableBlock(this.horizontalCollision);
        }
    }
    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new ClimberPathNavigator(this, worldIn);
    }
    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && --this.timeUntilNextShed <= 0) {
//			this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(ItemsReptiliophile.CHAMELEON_SKIN.get());
            this.timeUntilNextShed = this.random.nextInt(12000) + 6000;
        }
    }

    public boolean isBesideClimbableBlock() {
        return (this.entityData.get(CLIMBING) & 1) != 0;
    }
    public void setBesideClimbableBlock(boolean climbing) {
        byte b0 = this.entityData.get(CLIMBING);
        if (climbing) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }
        this.entityData.set(CLIMBING, b0);
    }
    public boolean blend() {
        return this.entityData.get(CAMOUFLAGE).intValue() == 0;
    }
    public boolean blendOff() {
        return this.entityData.get(CAMOUFLAGE).intValue() == 1;
    }
    public BlockState getOn() {
        return this.entityData.get(ON).orElse(Blocks.AIR.defaultBlockState());
    }
    public int getColor() {
        return this.entityData.get(COLOR).intValue();
    }
    public void updateOn() {
        this.entityData.set(ON, Optional.of(this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement())));
        this.entityData.set(COLOR, Minecraft.getInstance().getBlockColors().getColor(this.entityData.get(ON).get(), null, new BlockPos(this.getBlockPosBelowThatAffectsMyMovement()), 0));
    }
    // TODO
    /*
     * baby
     * particles when cycling camouflage
     * tongue animations
     * tree climbing
     * data fixer
     * fix camouflage when hanging on the edge of a block
     * camouflage when submerged in a block
     * add sound
     * chameleons scare arthropods on shoulders
     * chameleons retain texture on shoulder
     * resting
     */

    public class RestOnBlockGoal extends Goal {
        private final AnimalEntity animal;
        private final Predicate<BlockState> predicate$BlockState;
        private boolean foundBlock;
        private final PathNavigator navigator;
        private BlockPos destination;

        public RestOnBlockGoal(AnimalEntity animal, Predicate<BlockState> predicate$BlockState) {
            this.animal = animal;
            this.predicate$BlockState = predicate$BlockState;
            this.foundBlock = false;
            this.navigator = animal.getNavigation();
            this.destination = BlockPos.ZERO;

            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return true;
        }
        @Override
        public void tick() {
            super.tick();

            if(!this.foundBlock) {
                BlockPos from = new BlockPos(this.animal.position().x() - 5, this.animal.position().y() - 5, this.animal.position().z() - 5);
                BlockPos to = new BlockPos(this.animal.position().x() + 5, this.animal.position().y() + 5, this.animal.position().z() + 5);
                Stream<BlockPos> blocks = BlockPos.betweenClosedStream(from, to);
                blocks.forEach(pos -> {
                    if(this.predicate$BlockState.test(this.animal.getCommandSenderWorld().getBlockState(pos))) {
                        this.foundBlock = true;
                        this.destination = pos;
                    }
                });
            } else {
                this.navigator.moveTo(this.destination.getX(), this.destination.getY(), this.destination.getZ(), this.animal.getSpeed());
            }
        }
    }
}
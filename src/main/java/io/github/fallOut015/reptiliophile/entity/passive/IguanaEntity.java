package io.github.fallOut015.reptiliophile.entity.passive;

import io.github.fallOut015.reptiliophile.entity.EntitiesReptiliophile;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class IguanaEntity extends AnimalEntity {
    public IguanaEntity(EntityType<? extends IguanaEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity ageable) {
        IguanaEntity iguanaentity = EntitiesReptiliophile.IGUANA.get().create(serverWorld);

        return iguanaentity;
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 0.8D, 1.0000001E-5F));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 10.0F));
    }

    public static AttributeModifierMap.MutableAttribute applyAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.2d).add(Attributes.MAX_HEALTH, 10.0D);
    }
}
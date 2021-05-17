package io.github.fallOut015.reptiliophile.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.AbstractSpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class SpawnEggItemReptiliophile extends Item {
    private static final Map<LazyValue<EntityType<?>>, SpawnEggItemReptiliophile> EGGS = Maps.newIdentityHashMap();
    private final int primaryColor;
    private final int secondaryColor;
    private final LazyValue<EntityType<?>> typeIn;

    public SpawnEggItemReptiliophile(Supplier<EntityType<?>> typeIn, int primaryColorIn, int secondaryColorIn, Item.Properties builder) {
        super(builder);
        this.typeIn = new LazyValue<>(typeIn);
        this.primaryColor = primaryColorIn;
        this.secondaryColor = secondaryColorIn;
        EGGS.put(this.typeIn, this);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        if (!(world instanceof ServerWorld)) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.is(Blocks.SPAWNER)) {
                TileEntity tileentity = world.getBlockEntity(blockpos);
                if (tileentity instanceof MobSpawnerTileEntity) {
                    AbstractSpawner abstractspawner = ((MobSpawnerTileEntity)tileentity).getSpawner();
                    EntityType<?> entitytype1 = this.getType(itemstack.getTag());
                    abstractspawner.setEntityId(entitytype1);
                    tileentity.setChanged();
                    world.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
                    itemstack.shrink(1);
                    return ActionResultType.CONSUME;
                }
            }

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            EntityType<?> entitytype = this.getType(itemstack.getTag());
            if (entitytype.spawn((ServerWorld)world, itemstack, context.getPlayer(), blockpos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
                itemstack.shrink(1);
            }

            return ActionResultType.CONSUME;
        }
    }
    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.pass(itemstack);
        } else if (!(worldIn instanceof ServerWorld)) {
            return ActionResult.success(itemstack);
        } else {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getBlockPos();
            if (!(worldIn.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
                return ActionResult.pass(itemstack);
            } else if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, blockraytraceresult.getDirection(), itemstack)) {
                EntityType<?> entitytype = this.getType(itemstack.getTag());
                if (entitytype.spawn((ServerWorld)worldIn, itemstack, playerIn, blockpos, SpawnReason.SPAWN_EGG, false, false) == null) {
                    return ActionResult.pass(itemstack);
                } else {
                    if (!playerIn.abilities.instabuild) {
                        itemstack.shrink(1);
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.consume(itemstack);
                }
            } else {
                return ActionResult.fail(itemstack);
            }
        }
    }

    public boolean hasType(@Nullable CompoundNBT nbt, EntityType<?> type) {
        return Objects.equals(this.getType(nbt), type);
    }
    @OnlyIn(Dist.CLIENT)
    public int getColor(int tintIndex) {
        return tintIndex == 0 ? this.primaryColor : this.secondaryColor;
    }
    @Nullable
    @OnlyIn(Dist.CLIENT)
    public static SpawnEggItemReptiliophile getEgg(@Nullable EntityType<?> type) {
        for(LazyValue<EntityType<?>> key : EGGS.keySet()) {
            if(key.get() == type) {
                return EGGS.get(key);
            }
        }
        return null;
    }
    public static Iterable<SpawnEggItemReptiliophile> getEggs() {
        return Iterables.unmodifiableIterable(EGGS.values());
    }
    public EntityType<?> getType(@Nullable CompoundNBT nbt) {
        if (nbt != null && nbt.contains("EntityTag", 10)) {
            CompoundNBT compoundnbt = nbt.getCompound("EntityTag");
            if (compoundnbt.contains("id", 8)) {
                return EntityType.byString(compoundnbt.getString("id")).orElse(this.typeIn.get());
            }
        }

        return this.typeIn.get();
    }
    public Optional<MobEntity> getChildToSpawn(PlayerEntity player, MobEntity mob, EntityType<? extends MobEntity> entityType, ServerWorld world, Vector3d pos, ItemStack stack) {
        if (!this.hasType(stack.getTag(), entityType)) {
            return Optional.empty();
        } else {
            MobEntity mobentity;
            if (mob instanceof AgeableEntity) {
                mobentity = ((AgeableEntity)mob).getBreedOffspring(world, (AgeableEntity)mob);
            } else {
                mobentity = entityType.create(world);
            }

            if (mobentity == null) {
                return Optional.empty();
            } else {
                mobentity.setBaby(true);
                if (!mobentity.isBaby()) {
                    return Optional.empty();
                } else {
                    mobentity.moveTo(pos.x(), pos.y(), pos.z(), 0.0F, 0.0F);
                    world.addFreshEntityWithPassengers(mobentity);
                    if (stack.hasCustomHoverName()) {
                        mobentity.setCustomName(stack.getHoverName());
                    }

                    if (!player.abilities.instabuild) {
                        stack.shrink(1);
                    }

                    return Optional.of(mobentity);
                }
            }
        }
    }
}
package com.example.examplemod.entity;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.network.ServerNetwork;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TempleEntity extends LivingEntity implements Ownable {
    private static final int DESPAWN_AGE = 200;
    //        private UUID ownerUuid;
    private Entity owner;

    public TempleEntity(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (this.getWorld() instanceof ServerWorld) {
            if (entity instanceof ServerPlayerEntity) {
                return;
            }
            ServerNetwork.sendActionCallback((ServerPlayerEntity) owner);
            this.discard();
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getWorld() instanceof ServerWorld && !(ExampleMod.config.player_attack_collect && source.getAttacker() instanceof ServerPlayerEntity)) {
            ServerNetwork.sendActionCallback((ServerPlayerEntity) owner);
            this.discard();
        }
        return false;
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return null;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public Arm getMainArm() {
        return null;
    }

    @Override
    public void tick() {
        ++this.age;
        if (age > DESPAWN_AGE) {
            this.discard();
        }
    }

//    @Override
//    protected void initDataTracker() {
//
//    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.discard();
    }
//    @Override
//    public void readCustomDataFromNbt(NbtCompound nbt) {
//        if (nbt.containsUuid("Owner")) {
//            this.ownerUuid = nbt.getUuid("Owner");
//        }
//    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        this.discard();
    }
//    @Override
//    public void writeCustomDataToNbt(NbtCompound nbt) {
//        if (this.ownerUuid != null) {
//            nbt.putUuid("Owner", this.ownerUuid);
//        }
//    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    @Override
    @Nullable
    public Entity getOwner() {
//        World world;
        if (this.owner != null && !this.owner.isRemoved()) {
            return this.owner;
        }
//        if (this.ownerUuid != null && (world = this.getWorld()) instanceof ServerWorld) {
//            ServerWorld serverWorld = (ServerWorld) world;
//            this.owner = serverWorld.getEntity(this.ownerUuid);
//            return this.owner;
//        }
        return null;
    }
}

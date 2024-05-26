package com.jez.mha.item;

import com.jez.mha.client.ModClient;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ModSwordItem extends Item implements MhaSword {
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int damage = stack.getDamage() + this.getStackDamage(target);
        if (damage > stack.getMaxDamage()) {
            stack.setDamage(stack.getDamage());
        } else {
            stack.setDamage(damage);
        }
        return true;
    }

    @Override
    public void equip(PlayerEntity player) {
        if (isEquipped(player)) {
            PlayerInventory inventory = player.getInventory();
            inventory.offHand.set(0, ItemStack.EMPTY);
            ItemStack itemStack = new ItemStack(getSwordInSheathItem());
            itemStack.getOrCreateNbt().putInt("sheath_damage", inventory.main.get(inventory.selectedSlot).getDamage());
            inventory.main.set(inventory.selectedSlot, itemStack);
            if (player.getWorld() instanceof ClientWorld) {
                ModClient.processor.stopSubAnim();
                ModClient.processor.setEquipped(false);
            }
        } else {
            PlayerInventory inventory = player.getInventory();
            NbtCompound nbt = inventory.getMainHandStack().getNbt();
            ItemStack offHandItem = inventory.offHand.get(0);
            if (player.getWorld() instanceof ServerWorld world) {
                if (!offHandItem.isEmpty()) {
                    world.spawnEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), offHandItem));
                }
            } else {
                ModClient.processor.playSubAnim(getSubAnim());
                ModClient.processor.setEquipped(true);
                if (player.getMainHandStack().getItem() instanceof MhaSword item) {
                    ModClient.processor.setProcessorActions(item.getWeaponActions());
                }
            }
            inventory.offHand.set(0, new ItemStack(getSheathItem()));
            ItemStack itemStack = new ItemStack(getSwordItem());
            itemStack.getOrCreateNbt().putInt(ItemStack.DAMAGE_KEY, nbt.getInt("sheath_damage"));
            inventory.main.set(inventory.selectedSlot, itemStack);
        }
    }

    @Override
    public boolean isEquipped(PlayerEntity player) {
        PlayerInventory inventory = player.getInventory();
        NbtCompound nbt = inventory.getMainHandStack().getNbt();
        if (nbt == null) {
            return inventory.offHand.get(0).getItem() instanceof MhaSword;
        } else {
            return nbt.get("sheath_damage") == null;
        }
    }

    @Override
    public void attack(ServerPlayerEntity player, Entity target) {
        //isAttackable()在hitbox里
        if (target.handleAttack(player)) {
            return;
        }
        if (target instanceof EnderDragonPart) {
            target = ((EnderDragonPart) target).owner;
        }
        ItemStack itemStack = player.getMainHandStack();
        if (target instanceof LivingEntity && itemStack.getItem() instanceof ModSwordItem item) {
            itemStack.postHit((LivingEntity) target, player);
            target.damage(player.getDamageSources().playerAttack(player), item.getAttackDamage());
            player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), item.getAttackSound(), SoundCategory.PLAYERS);
        }
    }

    public ModSwordItem(Settings settings) {
        super(settings);
    }
}

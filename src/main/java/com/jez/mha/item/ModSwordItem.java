package com.jez.mha.item;

import com.jez.mha.client.ModClient;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ModSwordItem extends Item implements IMhaSword {
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
            if (player.getWorld() instanceof ClientWorld) {
                ModClient.processor.stopSubAnim();
                ModClient.processor.setEquipped(false);
            }
        } else {
                ModClient.processor.setEquipped(true);
                if (player.getMainHandStack().getItem() instanceof IMhaSword item) {
                    ModClient.processor.setProcessorActions(item.getWeaponActions());
            }
        }
    }

    @Override
    public boolean isEquipped(PlayerEntity player) {
        PlayerInventory inventory = player.getInventory();
        NbtCompound nbt = inventory.getMainHandStack().getNbt();
        if (nbt == null) {
            return inventory.offHand.get(0).getItem() instanceof IMhaSword;
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

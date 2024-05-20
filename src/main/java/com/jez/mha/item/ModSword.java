package com.jez.mha.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public interface ModSword {
    static void attack(ServerPlayerEntity player, Entity target) {
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

    void drawSword();

    int getAttackDamage();

    int getStackDamage(LivingEntity target);

    SoundEvent getAttackSound();
}

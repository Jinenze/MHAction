package com.jez.mha.item;

import com.jez.mha.action.WeaponActions;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;

public interface IMhaSword {
    void attack(ServerPlayerEntity player, Entity target);

    void equip(PlayerEntity player);

    boolean isEquipped(PlayerEntity player);

    @Environment(EnvType.CLIENT)
    void drawSwordTick(ClientPlayerEntity player);

    int getAttackDamage();

    int getStackDamage(LivingEntity target);

    @Environment(EnvType.CLIENT)
    SoundEvent getAttackSound();

    @Environment(EnvType.CLIENT)
    KeyframeAnimation getSubAnim();

    @Environment(EnvType.CLIENT)
    WeaponActions getWeaponActions();
}

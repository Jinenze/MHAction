package com.example.examplemod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;

public interface ModSword {
    int getAttackDamage();

    int getStackDamage(LivingEntity target);

    SoundEvent getAttackSound();
}

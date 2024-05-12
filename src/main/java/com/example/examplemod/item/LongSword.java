package com.example.examplemod.item;

import com.example.examplemod.init.ModSound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;

public class LongSword extends ModSwordItem {
    @Override
    public int getAttackDamage() {
        return 1000;
    }

    @Override
    public int getStackDamage(LivingEntity target) {
        return 10;
    }

    @Override
    public SoundEvent getAttackSound() {
        return ModSound.DODGE.START.soundEvent;
    }

    public LongSword(Settings settings) {
        super(settings);
    }
}

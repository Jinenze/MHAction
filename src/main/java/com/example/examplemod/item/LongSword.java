package com.example.examplemod.item;

import com.example.examplemod.init.ModItems;
import com.example.examplemod.init.ModSound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
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

    @Override
    public Item getSwordItem() {
        return ModItems.katana;
    }

    @Override
    public Item getSheathItem() {
        return ModItems.katanaa;
    }

    public LongSword(Settings settings) {
        super(settings);
    }
}

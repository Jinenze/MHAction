package com.jez.mha.item;

import com.jez.mha.client.ModClient;
import com.jez.mha.init.ModActions;
import com.jez.mha.init.ModItems;
import com.jez.mha.init.ModKeyBinds;
import com.jez.mha.init.ModSound;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;

public class LongSword extends ModSwordItem {
    @Override
    public void drawSword() {
        if (ModKeyBinds.ATTACK.wasPressed()) {
            if (MinecraftClient.getInstance().player.isSprinting()) {
                ModClient.processor.runAction(ModActions.STEP_SLASH);
            } else {
                ModClient.processor.runAction(ModActions.DRAW_SWORD);
            }
        }
    }

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

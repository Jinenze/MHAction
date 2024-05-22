package com.jez.mha.item;

import com.jez.mha.client.ModClient;
import com.jez.mha.init.ModActions;
import com.jez.mha.init.ModItems;
import com.jez.mha.init.ModKeyBinds;
import com.jez.mha.init.ModSound;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec2f;

public class LongSword extends ModSwordItem {
    @Override
    public void drawSwordTick(ClientPlayerEntity player) {
        if (ModKeyBinds.ATTACK.isPressed() && player.isOnGround()) {
            if (player.input.getMovementInput().equals(Vec2f.ZERO)) {
                ModClient.processor.runAction(ModActions.DRAW_SWORD);
            } else {
                ModClient.processor.runAction(ModActions.STEP_SLASH);
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
    public KeyframeAnimation getSubAnim() {
        return super.getSubAnim();
    }

    @Override
    public Item getSwordInSheathItem(){
        return ModItems.longSword;
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

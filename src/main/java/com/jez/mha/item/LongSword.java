package com.jez.mha.item;

import com.jez.mha.action.WeaponActions;
import com.jez.mha.client.ModClient;
import com.jez.mha.init.*;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec2f;

public class LongSword extends ModSwordItem {
    @Environment(EnvType.CLIENT)
    @Override
    public void drawSwordTick(ClientPlayerEntity player) {
        if (ModClient.processor.isReady()) {
            if (ModKeyBinds.UNLOAD.isPressed()) {
                ModClient.processor.runAction(ModActions.UNLOAD);
                ModClient.processor.stopSubAnim();
                ModClient.processor.setReady(false);
            }
        } else {
            if (ModKeyBinds.ATTACK.isPressed() && player.isOnGround()) {
                if (player.input.getMovementInput().equals(Vec2f.ZERO)) {
                    ModClient.processor.runAction(ModActions.DRAW_SWORD);
//                ModClient.processor.playSubAnim(getSubAnim());
                    ModClient.processor.setReady(true);
                } else {
                    ModClient.processor.runAction(ModActions.STEP_SLASH);
//                ModClient.processor.playSubAnim(getSubAnim());
                    ModClient.processor.setReady(true);
                }
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
        return PlayerAnimationRegistry.getAnimation(ModAnimations.DODGE);
    }

    @Override
    public Item getSwordInSheathItem() {
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

    @Override
    public WeaponActions getWeaponActions() {
        return ModActions.LongSword.INSTANCE;
    }

    public LongSword(Settings settings) {
        super(settings);
    }
}

package com.jez.mha.action.longsword;

import com.jez.mha.action.Action;
import com.jez.mha.init.ModAnimations;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.network.ClientPlayerEntity;

public class DrawSword extends Action {
    public DrawSword(String ID, int length) {
        super(ID, length);
    }

    @Override
    public void clientInit(ClientPlayerEntity player) {
        var animationPlayer = new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(ID)).setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL).setFirstPersonConfiguration(new FirstPersonConfiguration());
        ModAnimations.mainAnim.setAnimation(animationPlayer);
    }

    @Override
    public KeyframeAnimation getActionAnim() {
        return PlayerAnimationRegistry.getAnimation(ModAnimations.EMPTY);
    }
}

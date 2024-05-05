package com.example.examplemod.action;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public abstract class AbstractAction {
    private final int length;
    private KeyBinding[] actionKey;
    private Identifier actionAnim;
    private final AbstractAction[] availableAction;

    public AbstractAction(int length, AbstractAction... availableAction) {
        this.length = length;
        this.availableAction = availableAction;
    }

    @Environment(EnvType.CLIENT)
    public void clientInit(ClientPlayerEntity player) {
    }

    @Environment(EnvType.CLIENT)
    public void onClientTick(int tick) {
    }

    public void attacked(PlayerEntity player) {
    }

    @Environment(EnvType.CLIENT)
    public boolean isAvailable() {
        return true;
    }

    @Environment(EnvType.CLIENT)
    public boolean isAvailable(AbstractAction action) {
        for (AbstractAction a : availableAction) {
            if (a == action) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return length;
    }

    @Environment(EnvType.CLIENT)
    public KeyBinding[] getActionKey() {
        return actionKey;
    }

    @Environment(EnvType.CLIENT)
    public void setActionKey(KeyBinding[] key) {
        actionKey = key;
    }

    @Environment(EnvType.CLIENT)
    public KeyframeAnimation getActionAnim() {
        return PlayerAnimationRegistry.getAnimation(actionAnim);
    }

    @Environment(EnvType.CLIENT)
    public void setActionAnim(Identifier actionAnim) {
        this.actionAnim = actionAnim;
    }
}

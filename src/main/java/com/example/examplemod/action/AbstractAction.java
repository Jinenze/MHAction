package com.example.examplemod.action;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;

public abstract class AbstractAction {
    private final int stage0;
    private final int stage1;
    private final int stage2;
    private KeyBinding[] actionKey;
    private final Identifier actionAnim;
    private AbstractAction[] availableAction;

    public AbstractAction(int stage1, int stage2, int stage3, Identifier actionAnim) {
        this.stage0 = stage1;
        this.stage1 = stage2;
        this.stage2 = stage3;
        this.actionAnim = actionAnim;
    }

    public void run() {
    }

    public void onTick() {
    }

    public void onServerTick() {
    }

    @Environment(EnvType.CLIENT)
    public void onClientTick() {
    }

    public void attacked() {
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

    public int getStage0() {
        return stage0;
    }

    public int getStage1() {
        return stage1;
    }

    public int getStage2() {
        return stage2;
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
    public void setAvailableAction(AbstractAction[] availableAction) {
        this.availableAction = availableAction;
    }
}

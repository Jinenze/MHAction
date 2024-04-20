package com.example.examplemod.client.action;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public abstract class AbstractAction {
    private final int stage1;
    private final int stage2;
    private final int stage3;
    private KeyBinding[] actionKey;
    private final Identifier actionAnim;
    private AbstractAction[] availableAction;

    public AbstractAction(int stage1, int stage2, int stage3, Identifier actionAnim) {
        this.stage1 = stage1;
        this.stage2 = stage2;
        this.stage3 = stage3;
        this.actionAnim = actionAnim;
    }

    public abstract void run();

    public boolean isAvailable(AbstractAction action) {
        for (AbstractAction a : availableAction) {
            if (a == action) {
                return true;
            }
        }
        return false;
    }

    public int getStage1() {
        return stage1;
    }

    public int getStage2() {
        return stage2;
    }

    public int getStage3() {
        return stage3;
    }

    public KeyBinding[] getActionKey() {
        return actionKey;
    }

    public void setActionKey(KeyBinding[] key) {
        actionKey = key;
    }

    public KeyframeAnimation getActionAnim() {
        return PlayerAnimationRegistry.getAnimation(actionAnim);
    }

    public void setAvailableAction(AbstractAction[] availableAction) {
        this.availableAction = availableAction;
    }
}

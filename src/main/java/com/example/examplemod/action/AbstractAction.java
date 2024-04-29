package com.example.examplemod.action;

import com.example.examplemod.config.ServerConfig;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;

public abstract class AbstractAction {
    private int stage1;
    private int stage2;
    private int stage3;
    private KeyBinding[] actionKey;
    private Identifier actionAnim;
    private AbstractAction[] availableAction;

    public AbstractAction(ServerConfig.ActionTimeConfig config, AbstractAction... availableAction) {
        this.stage1 = config.stage1;
        this.stage2 = config.stage2;
        this.stage3 = config.stage3;
        this.availableAction = availableAction;
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

    public void setStage1(int stage1) {
        this.stage1 = stage1;
    }

    public void setStage2(int stage2) {
        this.stage2 = stage2;
    }

    public void setStage3(int stage3) {
        this.stage3 = stage3;
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

    public void setAvailableAction(AbstractAction[] availableAction) {
        this.availableAction = availableAction;
    }
}

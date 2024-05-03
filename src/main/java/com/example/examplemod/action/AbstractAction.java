package com.example.examplemod.action;

import com.example.examplemod.config.ServerConfig;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;

public abstract class AbstractAction {
    private final int stage1;
    private final int stage2;
    private final int stage3;
    private KeyBinding[] actionKey;
    private Identifier actionAnim;
    private final AbstractAction[] availableAction;

    public AbstractAction(ServerConfig.ActionTimeConfig config, AbstractAction... availableAction) {
        this.stage1 = config.stage1;
        this.stage2 = config.stage2;
        this.stage3 = config.stage3;
        this.availableAction = availableAction;
    }

    @Environment(EnvType.CLIENT)
    public void run() {
    }

//    public void onTick() {
//    }
//
//    public void onServerTick() {
//    }

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
}

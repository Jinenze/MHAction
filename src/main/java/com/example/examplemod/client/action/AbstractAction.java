package com.example.examplemod.client.action;

import net.minecraft.client.option.KeyBinding;

public abstract class AbstractAction {
    private final int stage1;
    private final int stage2;
    private final int stage3;
    private final KeyBinding[] actionKey;
    private final AbstractAction[] availableAction;

    public AbstractAction(int stage1, int stage2, int stage3, AbstractAction[] availableAction, KeyBinding... key) {
        this.stage1 = stage1;
        this.stage2 = stage2;
        this.stage3 = stage3;
        this.actionKey = key;
        this.availableAction = availableAction;
    }

    abstract void run();

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

    public AbstractAction[] getAvailableAction() {
        return availableAction;
    }
}

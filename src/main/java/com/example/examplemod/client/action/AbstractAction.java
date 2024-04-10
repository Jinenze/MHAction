package com.example.examplemod.client.action;

abstract class AbstractAction {
    final int stage1;
    final int stage2;
    final int stage3;
    final AbstractAction[] availableAction;

    AbstractAction(int stage1, int stage2, int stage3, AbstractAction[] availableAction) {
        this.stage1 = stage1;
        this.stage2 = stage2;
        this.stage3 = stage3;
        this.availableAction = availableAction;
    }

    abstract void run();
}

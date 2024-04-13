package com.example.examplemod.client.action;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;

@Environment(EnvType.CLIENT)
public class Dodge extends AbstractAction {

    public Dodge(int stage1, int stage2, int stage3, KeyBinding key, AbstractAction... availableAction) {
        super(stage1, stage2, stage3, key, availableAction);
    }

    @Override
    void run() {

    }
}

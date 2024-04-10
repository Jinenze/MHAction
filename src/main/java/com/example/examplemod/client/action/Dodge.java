package com.example.examplemod.client.action;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Dodge extends AbstractAction {
    public static final Dodge INSTANCE = new Dodge(1,0,1);

     private Dodge(int stage1, int stage2, int stage3, AbstractAction... availableAction) {
        super(stage1, stage2, stage3, availableAction);
    }

    @Override
    void run() {

    }
}

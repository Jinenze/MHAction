package com.example.examplemod.client.action;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class Dodge extends AbstractAction {

    public Dodge(int stage1, int stage2, int stage3) {
        super(stage1, stage2, stage3);
    }

    @Override
    public void run() {
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.takeKnockback(1, 1, 0);
        }
    }
}

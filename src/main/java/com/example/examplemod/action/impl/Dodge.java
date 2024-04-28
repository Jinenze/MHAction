package com.example.examplemod.action.impl;

import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.client.action.ClientActionRunner;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3d;

public class Dodge extends AbstractAction {

    @Override
    public void run() {

    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onClientTick() {
        if (ClientActionRunner.getActionStage() != 3) {
            return;
        }
        ClientActionRunner.player.setVelocity(new Vec3d(-Math.sin(Math.toRadians(ClientActionRunner.actionHeadYaw)), 0, Math.cos(Math.toRadians(ClientActionRunner.actionHeadYaw))));
    }

    @Override
    public void attacked() {

    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isAvailable() {
        return ClientActionRunner.player.isOnGround();
    }

    public Dodge(int stage1, int stage2, int stage3, AbstractAction... availableAction) {
        super(stage1, stage2, stage3, availableAction);
    }

}
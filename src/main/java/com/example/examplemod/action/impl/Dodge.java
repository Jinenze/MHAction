package com.example.examplemod.action.impl;

import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.action.ActionRunner;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class Dodge extends AbstractAction {

    @Override
    public void run() {
    }

    @Override
    public void onClientTick() {
        if (ActionRunner.getActionStage() != 2){
            return;
        }
        ActionRunner.player.setVelocity(new Vec3d(-Math.sin(Math.toRadians(ActionRunner.actionHeadYaw)), 0, Math.cos(Math.toRadians(ActionRunner.actionHeadYaw))));
    }

    @Override
    public void attacked() {

    }
    @Override
    public boolean isAvailable() {
        return ActionRunner.player.isOnGround();
    }
    public Dodge(int stage1, int stage2, int stage3, Identifier actionAnim) {
        super(stage1, stage2, stage3, actionAnim);
    }
}
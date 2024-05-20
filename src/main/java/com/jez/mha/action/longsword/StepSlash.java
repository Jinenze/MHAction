package com.jez.mha.action.longsword;

import com.jez.mha.action.Action;
import com.jez.mha.action.AttackAction;
import com.jez.mha.client.ModClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class StepSlash extends Action implements AttackAction {
    @Environment(EnvType.CLIENT)
    @Override
    public void onClientTick(int tick) {
        switch (tick) {
            case 10:
                ModClient.processor.attack();
                break;
            case 15:
                ModClient.processor.setCanPlayerAction(true);
                break;
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Box getHitBox(ClientPlayerEntity player) {
        Vec3d pos = ModClient.processor.getActionYaw().getVec3d();
        Vec3d pos1 = new Vec3d(player.getPos().getX() - Math.ceil(pos.getX()) / 2, player.getY() + 0.5f, player.getPos().getY() - Math.ceil(pos.getY()) / 2);
        Vec3d pos2 = new Vec3d(player.getPos().getX() + Math.ceil(pos.getX()) / 2, player.getY() + 1.5f, player.getPos().getY() + Math.ceil(pos.getY()) / 2);
        return new Box(pos1, pos2);
    }

    @Override
    public void hit(ClientPlayerEntity player) {
        PlayerGauge playerGauge = (PlayerGauge) player;
        playerGauge.setSpiritGauge(playerGauge.getSpiritGauge() + 10);
    }

    public StepSlash(String ID, int length) {
        super(ID, length);
    }
}

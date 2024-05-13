package com.example.examplemod.action.longsword;

import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.action.AttackAction;
import com.example.examplemod.client.action.ClientActionRunner;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class OverHeadSlash extends AbstractAction implements AttackAction {
    @Environment(EnvType.CLIENT)
    @Override
    public void onClientTick(int tick) {
        super.onClientTick(tick);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Box getHitBox(ClientPlayerEntity player) {
        Vec3d pos = ClientActionRunner.actionYaw.getVec3d();
        Vec3d pos1 = new Vec3d(player.getPos().getX() - Math.ceil(pos.getX()) / 2, player.getY() + 0.5f, player.getPos().getY() - Math.ceil(pos.getY()) / 2);
        Vec3d pos2 = new Vec3d(player.getPos().getX() + Math.ceil(pos.getX()) / 2, player.getY() + 1.5f, player.getPos().getY() + Math.ceil(pos.getY()) / 2);
        return new Box(pos1, pos2);
    }

    @Override
    public void hit(ClientPlayerEntity player) {
        PlayerGauge playerGauge = (PlayerGauge) player;
        playerGauge.setSpiritGauge(playerGauge.getSpiritGauge() + 10);
    }

    public OverHeadSlash(String ID, int length) {
        super(ID, length);
    }
}

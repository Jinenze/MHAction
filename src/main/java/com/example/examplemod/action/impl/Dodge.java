package com.example.examplemod.action.impl;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.action.AttackAction;
import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.init.ModSound;
import com.example.examplemod.network.ClientNetwork;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Dodge extends AbstractAction implements AttackAction {
    @Override
    public void clientInit(ClientPlayerEntity player) {
        ClientActionRunner.actionYaw.playerInput(player);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onClientTick(int tick) {
        switch (tick) {
            case 1:
                ClientActionRunner.setTick((player) -> {
                    player.setVelocity(ClientActionRunner.actionYaw.getVec3d());
                });
                ClientActionRunner.run((ClientNetwork::sendSpawnRequest));
                break;
            case 6:
                ClientActionRunner.setTick(null);
                ClientActionRunner.run((player) -> {
                    ClientActionRunner.attack();
                });
                break;
            case 20:
//                ClientNetwork.sendDiscardRequest();
                break;
        }
    }

    @Override
    public void attacked(PlayerEntity player) {
        ExampleMod.LOGGER.info("OHHHHHHHHHHHHHHH");

    }

    public Dodge(String ID, int length, AbstractAction... availableAction) {
        super(ID, length, availableAction);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isAvailable() {
        return MinecraftClient.getInstance().player.isOnGround();
    }


    @Override
    public Box getHitBox(ClientPlayerEntity player) {
        Vec3d pos = ClientActionRunner.actionYaw.getVec3d();
        Vec3d pos1 = new Vec3d(player.getPos().getX() - Math.ceil(pos.getX()) / 2, player.getY() + 0.5f, player.getPos().getY() - Math.ceil(pos.getY()) / 2);
        Vec3d pos2 = new Vec3d(player.getPos().getX() + Math.ceil(pos.getX()) / 2, player.getY() + 1.5f, player.getPos().getY() + Math.ceil(pos.getY()) / 2);
        return new Box(pos1, pos2);
    }

    @Override
    public SoundEvent getStartSound() {
        return ModSound.DODGE.START.soundEvent;
    }
}
package com.example.examplemod.action.impl;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.config.ServerConfig;
import com.example.examplemod.network.Packets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class Dodge extends AbstractAction {
    @Override
    public void run() {
        Vec2f input = ClientActionRunner.player.input.getMovementInput();
        switch ((int) input.x) {
            case 1:
                ClientActionRunner.actionHeadYaw -= input.y == 1.0f ? 45f : 90f;
                break;
            case -1:
                ClientActionRunner.actionHeadYaw += input.y == 1.0f ? 45f : 90f;
                break;
        }
        ClientPlayNetworking.send(Packets.ActionSpawnRequest.ID, new Packets.ActionSpawnRequest(ClientActionRunner.player.getBlockPos()).write());
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onClientTick() {
        if (ClientActionRunner.getActionStage() == 1) {
            ClientActionRunner.player.setVelocity(new Vec3d(-Math.sin(Math.toRadians(ClientActionRunner.actionHeadYaw)), 0, Math.cos(Math.toRadians(ClientActionRunner.actionHeadYaw))));
        }
    }

    @Override
    public void attacked() {
        ExampleMod.LOGGER.info("OHHHHHHHHHHHHHHH");
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isAvailable() {
        return MinecraftClient.getInstance().player.isOnGround();
    }

    public Dodge(ServerConfig.ActionTimeConfig config, AbstractAction... availableAction) {
        super(config, availableAction);
    }
}
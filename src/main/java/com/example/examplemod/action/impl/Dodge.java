package com.example.examplemod.action.impl;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.action.AttackAction;
import com.example.examplemod.client.action.ActionHitBox;
import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.network.Packets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class Dodge extends AbstractAction implements AttackAction {
    @Override
    public void clientInit(ClientPlayerEntity player) {
        Vec2f input = player.input.getMovementInput();
        switch ((int) input.x) {
            case 1:
                ClientActionRunner.actionHeadYaw -= input.y == 1.0f ? 45f : 90f;
                break;
            case -1:
                ClientActionRunner.actionHeadYaw += input.y == 1.0f ? 45f : 90f;
                break;
        }
        ClientPlayNetworking.send(Packets.ActionSpawnRequest.ID, new Packets.ActionSpawnRequest(player.getBlockPos()).write());
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onClientTick(int tick) {
        switch (tick) {
            case 1:
                ClientActionRunner.setTick((player) -> {
                    player.setVelocity(new Vec3d(-Math.sin(Math.toRadians(ClientActionRunner.actionHeadYaw)), 0, Math.cos(Math.toRadians(ClientActionRunner.actionHeadYaw))));
                });
                break;
            case 6:
                ClientActionRunner.setTick((player) -> {
                    for (Entity entity : ActionHitBox.intersects(player, this.getHitBox(player))) {
                        int id = entity.getId();
                        ExampleMod.LOGGER.info(String.valueOf(id));
                    }
                    ClientPlayNetworking.send(Packets.ActionDiscardRequest.ID, new Packets.ActionDiscardRequest().write());
                });
                break;
            case 7:
                ClientActionRunner.setTick(null);
        }
    }

    @Override
    public void attacked(PlayerEntity player) {
        ExampleMod.LOGGER.info("OHHHHHHHHHHHHHHH");

    }

    public Dodge(int time, AbstractAction... availableAction) {
        super(time, availableAction);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isAvailable() {
        return MinecraftClient.getInstance().player.isOnGround();
    }


    @Override
    public Box getHitBox(ClientPlayerEntity player) {
        return player.getBoundingBox();
    }
}
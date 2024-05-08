package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.action.ServerActionRunner;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class ServerNetwork {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            sender.sendPacket(Packets.ServerConfigPacket.ID, new Packets.ServerConfigPacket(ExampleMod.config).write());
        });
        ServerPlayNetworking.registerGlobalReceiver(Packets.ActionStartRequest.ID, (server, player, handler, buf, responseSender) -> {
            ServerActionRunner.startAction(player, Packets.ActionStartRequest.read(buf));
        });
        ServerPlayNetworking.registerGlobalReceiver(Packets.ActionSpawnRequest.ID, (server, player, handler, buf, responseSender) -> {
            ServerActionRunner.spawnActionEntity(player, Packets.ActionSpawnRequest.read(buf));
        });
        ServerPlayNetworking.registerGlobalReceiver(Packets.ActionDiscardRequest.ID, (server, player, handler, buf, responseSender) -> {
            ServerActionRunner.discardActionEntity(player);
        });
    }

    public static void sendActionCallback(ServerPlayerEntity player) {
        ServerPlayNetworking.getSender(player).sendPacket(Packets.ActionCallback.ID, new Packets.ActionCallback().write());
    }
}

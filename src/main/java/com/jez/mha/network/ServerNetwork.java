package com.jez.mha.network;

import com.jez.mha.MHAction;
import com.jez.mha.action.ServerActionRunner;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class ServerNetwork {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            sender.sendPacket(Packets.ServerConfigPacket.ID, new Packets.ServerConfigPacket(MHAction.config).write());
        });
        ServerPlayNetworking.registerGlobalReceiver(Packets.AttackRequest.ID, (server, player, handler, buf, responseSender) -> {
            ServerActionRunner.attack(player, Packets.AttackRequest.read(buf));
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

package com.jez.mha.network;

import com.jez.mha.MHAction;
import com.jez.mha.action.Action;
import com.jez.mha.client.ModClient;
import com.jez.mha.init.ModActions;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;

import java.util.List;

public class ClientNetwork {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(Packets.ServerConfigPacket.ID, (client, handler, buf, responseSender) -> {
            MHAction.config = Packets.ServerConfigPacket.read(buf);
            ModActions.client();
        });
        ClientPlayNetworking.registerGlobalReceiver(Packets.ActionCallback.ID, (client, handler, buf, responseSender) -> {
            ModClient.processor.actionAttackCallBack();
        });
    }

    public static void sendAttackRequest(List<Entity> entities){
        ClientPlayNetworking.send(Packets.AttackRequest.ID, new Packets.AttackRequest(entities).write());
    }

    public static void sendStartRequest(Action action) {
        ClientPlayNetworking.send(Packets.ActionStartRequest.ID, new Packets.ActionStartRequest(action).write());
    }

    public static void sendSpawnRequest(ClientPlayerEntity player) {
        ClientPlayNetworking.send(Packets.ActionSpawnRequest.ID, new Packets.ActionSpawnRequest(player.getBlockPos()).write());
    }

    public static void sendDiscardRequest() {
        ClientPlayNetworking.send(Packets.ActionDiscardRequest.ID, new Packets.ActionDiscardRequest().write());
    }
}

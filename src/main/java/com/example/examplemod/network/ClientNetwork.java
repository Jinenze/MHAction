package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.init.ModActions;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;

public class ClientNetwork {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(Packets.ServerConfigPacket.ID, (client, handler, buf, responseSender) -> {
            ExampleMod.config = Packets.ServerConfigPacket.read(buf);
            ModActions.client();
        });
        ClientPlayNetworking.registerGlobalReceiver(Packets.ActionCallback.ID, (client, handler, buf, responseSender) -> {
            ClientActionRunner.actionAttackCallBack();
        });
    }
    public static void sendStartRequest(AbstractAction action){
        ClientPlayNetworking.send(Packets.ActionStartRequest.ID, new Packets.ActionStartRequest(action).write());
    }

    public static void sendSpawnRequest(ClientPlayerEntity player) {
        ClientPlayNetworking.send(Packets.ActionSpawnRequest.ID, new Packets.ActionSpawnRequest(player.getBlockPos()).write());
    }

    public static void sendDiscardRequest() {
        ClientPlayNetworking.send(Packets.ActionDiscardRequest.ID, new Packets.ActionDiscardRequest().write());
    }
}

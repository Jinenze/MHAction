package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.init.ModActions;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientNetwork {
    public static void register(){
        ClientPlayNetworking.registerGlobalReceiver(Packets.ServerConfigPacket.ID, (client, handler, buf, responseSender) -> {
            ExampleMod.config = Packets.ServerConfigPacket.read(buf);
            ModActions.client();
        });
        ClientPlayNetworking.registerGlobalReceiver(Packets.ActionCallback.ID, (client, handler, buf, responseSender) -> {
            ClientActionRunner.actionAttackCallBack();
        });
    }
}

package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class ServerNetwork {
    public static void register(){
        ServerPlayConnectionEvents.JOIN.register( (handler, sender, server) -> {
            sender.sendPacket(Packets.ServerConfigPacket.ID, new Packets.ServerConfigPacket(ExampleMod.config).write());
        });
    }
}

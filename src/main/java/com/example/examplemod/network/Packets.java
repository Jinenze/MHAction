package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.config.ServerConfig;
import com.google.gson.Gson;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class Packets {
    public static class ServerConfigPacket {
        public static final Identifier ID = new Identifier(ExampleMod.MODID, "server_config");
        private final String config;

        public ServerConfigPacket(ServerConfig config) {
            this.config = new Gson().toJson(config);
        }

        public PacketByteBuf write() {
            return PacketByteBufs.create().writeString(config);
        }

        public static ServerConfig read(PacketByteBuf buffer) {
            return new Gson().fromJson(buffer.readString(), ServerConfig.class);
        }
    }
}

package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.config.ServerConfig;
import com.example.examplemod.init.ModActions;
import com.google.gson.Gson;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class Packets {
    public record ServerConfigPacket(ServerConfig config) {
        public static final Identifier ID = new Identifier(ExampleMod.MODID, "server_config");

        public PacketByteBuf write() {
            return PacketByteBufs.create().writeString(new Gson().toJson(config));
        }

        public static ServerConfig read(PacketByteBuf buffer) {
            return new Gson().fromJson(buffer.readString(), ServerConfig.class);
        }
    }

    public record ActionStartRequest(AbstractAction action) {
        public static final Identifier ID = new Identifier(ExampleMod.MODID, "start_request");

        public PacketByteBuf write() {
            return PacketByteBufs.create().writeIdentifier(action.ID);
        }

        public static AbstractAction read(PacketByteBuf buf) {
            return ModActions.inList(buf.readIdentifier());
        }
    }

    public record ActionSpawnRequest(BlockPos pos) {
        public static final Identifier ID = new Identifier(ExampleMod.MODID, "spawn_request");

        public PacketByteBuf write() {
            return PacketByteBufs.create().writeBlockPos(pos);
        }

        public static BlockPos read(PacketByteBuf buf) {
            return buf.readBlockPos();
        }
    }

    public static class ActionCallback {
        public static final Identifier ID = new Identifier(ExampleMod.MODID, "action_callback");

        public PacketByteBuf write() {
            return PacketByteBufs.empty();
        }
    }

    public static class ActionDiscardRequest {
        public static final Identifier ID = new Identifier(ExampleMod.MODID, "discard_request");

        public PacketByteBuf write() {
            return PacketByteBufs.empty();
        }
    }
}

package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.action.Action;
import com.example.examplemod.config.ServerConfig;
import com.example.examplemod.init.ModActions;
import com.google.gson.Gson;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class Packets {
    public record AttackRequest(List<Entity> entities) {
        public static final Identifier ID = new Identifier(ExampleMod.MODID, "attack_request");

        public PacketByteBuf write() {
            int[] entityIds = new int[entities.size()];
            for (int i = 0; i < entities.size(); i++) {
                entityIds[i] = entities.get(i).getId();
            }
            return PacketByteBufs.create().writeIntArray(entityIds);
        }

        public static int[] read(PacketByteBuf buffer) {
            return buffer.readIntArray();
        }
    }

    public record ServerConfigPacket(ServerConfig config) {
        public static final Identifier ID = new Identifier(ExampleMod.MODID, "server_config");

        public PacketByteBuf write() {
            return PacketByteBufs.create().writeString(new Gson().toJson(config));
        }

        public static ServerConfig read(PacketByteBuf buffer) {
            return new Gson().fromJson(buffer.readString(), ServerConfig.class);
        }
    }

    public record ActionStartRequest(Action action) {
        public static final Identifier ID = new Identifier(ExampleMod.MODID, "start_request");

        public PacketByteBuf write() {
            return PacketByteBufs.create().writeIdentifier(action.ID);
        }

        public static Action read(PacketByteBuf buf) {
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

package com.example.examplemod.action;

import com.example.examplemod.init.ModEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.entity.SpawnReason.COMMAND;

public class ServerActionRunner {

    public static void spawnActionEntity(ServerPlayerEntity player, BlockPos pos) {
        ModEntity.TEMPLE.spawn(player.getServerWorld(), pos, COMMAND).setOwner(player);
    }
}

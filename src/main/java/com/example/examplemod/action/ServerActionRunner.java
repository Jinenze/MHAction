package com.example.examplemod.action;

import com.example.examplemod.entity.TempleEntity;
import com.example.examplemod.init.ModEntities;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

import static net.minecraft.entity.SpawnReason.COMMAND;

public class ServerActionRunner {
    private static final ArrayList<TempleEntity> entities = new ArrayList<>();

    public static void startAction(ServerPlayerEntity player, AbstractAction action){
        if(action != null){
            player.getServerWorld().playSound( null, player.getBlockPos(), action.getStartSound() , SoundCategory.PLAYERS);
        }
    }

    public static void spawnActionEntity(ServerPlayerEntity player, BlockPos pos) {
        entities.add(ModEntities.TEMPLE.spawn(player.getServerWorld(), pos, COMMAND).setOwner(player));
    }

    public static void discardActionEntity(ServerPlayerEntity player) {
        for (TempleEntity entity : entities) {
            if (entity.getOwner() == player) {
                entity.discard();
            }
        }
    }
}

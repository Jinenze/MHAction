package com.jez.mha.action;

import com.jez.mha.entity.TempleEntity;
import com.jez.mha.init.ModEntities;
import com.jez.mha.item.ModSword;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

import static net.minecraft.entity.SpawnReason.COMMAND;

public class ServerActionRunner {
    private static final ArrayList<TempleEntity> entities = new ArrayList<>();

    public static void startAction(ServerPlayerEntity player, Action action) {
        if (action != null) {
            player.getServerWorld().playSound(null, player.getX(), player.getY(), player.getZ(), action.getStartSound(), SoundCategory.PLAYERS);
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

    public static void attack(ServerPlayerEntity player, int[] entityIds) {
        Entity entity;
        for (int id : entityIds) {
            entity = player.getServerWorld().getEntityById(id);
            if (entity != null) {
                ModSword.attack(player, entity);
            }
        }
    }
}

package com.jez.mha.action;

import com.jez.mha.entity.TempleEntity;
import com.jez.mha.init.ModEntities;
import com.jez.mha.item.MhaSword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class ServerActionRunner {
    private static final ArrayList<TempleEntity> entities = new ArrayList<>();

    public static void startAction(ServerPlayerEntity player, Action action) {
        if (action != null) {
            action.serverStart(player);
        }
    }

    public static void spawnActionEntity(ServerPlayerEntity player, BlockPos pos) {
        entities.add(ModEntities.TEMPLE.spawn(player.getServerWorld(), pos, SpawnReason.COMMAND).setOwner(player));
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
            if (player.getMainHandStack().getItem() instanceof MhaSword item) {
                if (entity != null) {
                    item.attack(player, entity);
                }
            }
        }
    }

    public static void equipItem(ServerPlayerEntity player) {
        if (player.getMainHandStack().getItem() instanceof MhaSword item) {
            item.equip(player);
        }
    }
}

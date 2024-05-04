package com.example.examplemod.client.action;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

import java.util.List;

public class ActionHitBox {
    public static List<Entity> intersects(ClientPlayerEntity player, Box box) {
        List<Entity> entities = player
                .getWorld()
                .getOtherEntities(player, box, entity -> !entity.isSpectator() && entity.canHit())
                .stream()
                .filter(entity -> entity != player && entity.isAttackable())
                .toList();
        return entities;
    }
}

package com.jez.mha.client.action;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ActionHitBox {
    public static List<Entity> intersects(ClientPlayerEntity player, Box box) {
        return player
                .getWorld()
                .getOtherEntities(player, box, entity -> !entity.isSpectator() && entity.canHit())
                .stream()
                .filter(entity -> entity != player && entity.isAttackable())
                .toList();
    }
}

package com.jez.mha.action;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Box;

@Environment(EnvType.CLIENT)
public interface AttackAction {
    Box getHitBox(ClientPlayerEntity player);

    void hit(ClientPlayerEntity player);
}

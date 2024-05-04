package com.example.examplemod.action;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Box;

public interface AttackAction {
    Box getHitBox(ClientPlayerEntity player);
}

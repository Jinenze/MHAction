package com.jez.mha.action.impl;

import com.jez.mha.MHAction;
import com.jez.mha.action.Action;
import com.jez.mha.action.CounterAction;
import com.jez.mha.client.ModClient;
import com.jez.mha.init.ModSound;
import com.jez.mha.network.ClientNetwork;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;

public class Dodge extends Action implements CounterAction {
    @Environment(EnvType.CLIENT)
    @Override
    public void onClientTick(int tick) {
        switch (tick) {
            case 1:
                ModClient.processor.setTick((player) -> {
                    player.setVelocity(ModClient.processor.getActionYaw().getVec3d());
                });
                ModClient.processor.run((ClientNetwork::sendSpawnRequest));
                break;
            case 6:
                ModClient.processor.setTick(null);
                ModClient.processor.run((player) -> {
                    ModClient.processor.attack();
                });
                break;
            case 20:
//                ClientNetwork.sendDiscardRequest();
                break;
        }
    }

    @Override
    public void attacked(PlayerEntity player) {
        MHAction.LOGGER.info("OHHHHHHHHHHHHHHH");
    }

    @Override
    public SoundEvent getStartSound() {
        return ModSound.DODGE.START.soundEvent;
    }

    public Dodge(String ID, int length) {
        super(ID, length);
    }
}
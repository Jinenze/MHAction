package com.jez.mha.action;

import com.jez.mha.MHAction;
import com.jez.mha.client.ModClient;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public abstract class Action {
    public final Identifier ID;
    private final int length;
    @Environment(EnvType.CLIENT)
    private KeyBinding[] actionKey;
    @Environment(EnvType.CLIENT)
    private Action[] availableAction;

    public Action(String ID, int length) {
        this.ID = new Identifier(MHAction.MODID, ID);
        this.length = length;
    }

    public void serverStart(ServerPlayerEntity player) {
        if (this.getStartSound() != null) {
            player.getServerWorld().playSound(null, player.getX(), player.getY(), player.getZ(), this.getStartSound(), SoundCategory.PLAYERS);
        }
    }


    @Environment(EnvType.CLIENT)
    public void clientInit(ClientPlayerEntity player) {
        ModClient.processor.getActionYaw().playerInput(player);
    }

    @Environment(EnvType.CLIENT)
    public void onClientTick(int tick) {
    }

    @Environment(EnvType.CLIENT)
    public boolean isAvailable() {
        return MinecraftClient.getInstance().player.isOnGround();
    }

    @Environment(EnvType.CLIENT)
    public boolean isAvailable(Action action) {
        for (Action a : availableAction) {
            if (a == action) {
                return true;
            }
        }
        return false;
    }

    @Environment(EnvType.CLIENT)
    public void setAvailableAction(Action[] actions) {
        this.availableAction = actions;
    }

    @Environment(EnvType.CLIENT)
    public KeyBinding[] getActionKey() {
        return actionKey;
    }

    @Environment(EnvType.CLIENT)
    public void setActionKey(KeyBinding[] key) {
        actionKey = key;
    }

    @Environment(EnvType.CLIENT)
    public KeyframeAnimation getActionAnim() {
        return PlayerAnimationRegistry.getAnimation(ID);
    }

    public int getLength() {
        return length;
    }

    @Environment(EnvType.CLIENT)
    public SoundEvent getStartSound() {
        return null;
    }

    @Environment(EnvType.CLIENT)
    public SoundEvent getEndSound() {
        return null;
    }
}

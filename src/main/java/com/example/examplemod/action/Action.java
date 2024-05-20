package com.example.examplemod.action;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.client.action.ClientActionRunner;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public abstract class Action {
    public final Identifier ID;
    private final int length;
    private KeyBinding[] actionKey;
    private Identifier actionAnim;
    private Action[] availableAction;

    public Action(String ID, int length) {
        this.ID = new Identifier(ExampleMod.MODID, ID);
        this.length = length;
    }

    @Environment(EnvType.CLIENT)
    public void clientInit(ClientPlayerEntity player) {
        ClientActionRunner.actionYaw.playerInput(player);
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
    public void setAvailableAction(Action[] actions){
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
        return PlayerAnimationRegistry.getAnimation(actionAnim);
    }

    @Environment(EnvType.CLIENT)
    public void setActionAnim(Identifier actionAnim) {
        this.actionAnim = actionAnim;
    }

    public int getLength() {
        return length;
    }

    public SoundEvent getStartSound() {
        return null;
    }

    public SoundEvent getEndSound() {
        return null;
    }
}

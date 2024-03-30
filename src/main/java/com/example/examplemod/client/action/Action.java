package com.example.examplemod.client.action;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.SwordItem;

@Environment(EnvType.CLIENT)
public class Action {
    public static int cooldown;
    public static int inputTime;

    public static int stopTime;
    public static boolean running;
    public static boolean stopping;

    public static void register(KeyBinding key, int cooldown, int inputTime,int stopTime, Action action) {
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (client.player != null && key.wasPressed() && !Action.running && client.player.getMainHandStack().getItem() instanceof SwordItem) {
                Action.running = true;
                Action.stopping = true;
                action.run();
                Action.cooldown = cooldown;
                Action.inputTime = inputTime;
                Action.stopTime = stopTime;
            }
        });
    }

    public void run() {

    }
}

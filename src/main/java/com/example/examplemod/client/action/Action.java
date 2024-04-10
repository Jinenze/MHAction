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

    public static int actionStage;
    public static boolean actionRunning;
    private static AbstractAction runningAction;
    public static float movementForward;
    public static float movementSideways;

    public static void register(KeyBinding key, int cooldown, int inputTime, int stopTime, AbstractAction action) {
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (client.player != null && key.wasPressed() && Action.actionStage == 1 && client.player.getMainHandStack().getItem() instanceof SwordItem) {
                actionRunning = true;
                Action.actionStage = 0;
                action.run();
                Action.cooldown = cooldown;
                Action.inputTime = inputTime;
                Action.stopTime = stopTime;
            }
        });
    }

    public static void register(KeyBinding key, int cooldown, int inputTime, int stopTime, Action action, KeyBinding... keyList) {
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (client.player != null && key.wasPressed() && Action.actionStage == 1 && client.player.getMainHandStack().getItem() instanceof SwordItem) {
                Action.actionStage = 0;
                action.run(keyList);
                Action.cooldown = cooldown;
                Action.inputTime = inputTime;
                Action.stopTime = stopTime;
            }
        });
    }

    public static void actionTick() {
        switch (Action.actionStage) {
            case 0:
                if (!Action.actionRunning) {
                    break;
                }
                if (Action.cooldown > 0) {
                    --Action.cooldown;
                } else {
                    Action.actionStage = 1;
                }
            case 1:
                if (Action.inputTime > 0) {
                    --Action.inputTime;
                } else {
                    Action.actionStage = 2;
                }
            case 2:
                if (Action.stopTime > 0) {
                    --Action.stopTime;
                } else {
                    Action.actionStage = 0;
                    Action.actionRunning = false;
                }
        }
    }

    public void run(KeyBinding... keyList) {
        for (KeyBinding keyBind : keyList) {
            if (keyBind.isPressed()) {
                action(keyBind);
            }
        }
    }

    public void setAction(AbstractAction action) {
        if (!Action.actionRunning || Action.actionStage == 2) {
            runningAction = action;
        }
    }

    public void action(KeyBinding keyBind) {

    }
}

package com.example.examplemod.client.action;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class Action {
    public static float movementForward;
    public static float movementSideways;
    public static int cooldown;
    private static int inputTime;
    private static int stopTime;
    private static int actionStage;
    private static boolean actionRunning;
    private static AbstractAction runningAction;
    private static final ArrayList<AbstractAction> Actions = new ArrayList<>();

    public static void actionTick() {
        switch (actionStage) {
            case 0:
                if (!actionRunning) {
                    break;
                }
                if (cooldown > 0) {
                    --cooldown;
                } else {
                    actionStage = 1;
                }
            case 1:
                if (inputTime > 0) {
                    --inputTime;
                } else {
                    actionStage = 2;
                }
            case 2:
                if (stopTime > 0) {
                    --stopTime;
                } else {
                    actionStage = 0;
                    actionRunning = false;
                    runningAction = null;
                }
        }
    }

    public static void doAction(KeyBinding lastKey, KeyBinding key) {
        if ((lastKey == null && key != null) || lastKey == key) {
            lastKey = null;
        }
        for (AbstractAction a : Actions) {
            KeyBinding[] keys = a.getActionKey();
            if (keys[0] == lastKey && keys[1] == key) {
                setAction(a);
            }
        }
    }

    public static void setAction(AbstractAction action) {
        if ((!actionRunning || actionStage == 2)) {
            if (runningAction != null) {
                if (!runningAction.isAvailable(action)) {
                    return;
                }
            }
            runningAction = action;
            cooldown = action.getStage1();
            inputTime = action.getStage2();
            stopTime = action.getStage3();
            actionRunning = true;
            action.run();
        }
    }

    public static void register(AbstractAction action, KeyBinding key, AbstractAction... availableAction) {
        KeyBinding[] k = {null, key};
        action.setActionKey(k);
        action.setAvailableAction(availableAction);
        Actions.add(action);
    }

    public static void register(AbstractAction action, KeyBinding lastKey, KeyBinding key, AbstractAction... availableAction) {
        KeyBinding[] k = {lastKey, key};
        action.setActionKey(k);
        action.setAvailableAction(availableAction);
        Actions.add(action);
    }

    public static boolean isActionRunning() {
        return actionRunning;
    }
}

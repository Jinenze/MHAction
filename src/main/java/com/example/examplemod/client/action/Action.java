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

    public static void doAction(KeyBinding lastKey, KeyBinding key) {
        KeyBinding[] k;
        if ((lastKey == null && key != null) || lastKey == key) {
            k = new KeyBinding[]{null, key};
        } else {
            k = new KeyBinding[]{lastKey, key};
        }
        for (AbstractAction a : Actions) {
            if (a.getActionKey() == k) {
                setAction(a);
            }
        }
    }

    public static void setAction(AbstractAction action) {
        if ((!Action.actionRunning || Action.actionStage == 2) && runningAction.isAvailable(action)) {
            runningAction = action;
            cooldown = action.getStage1();
            inputTime = action.getStage2();
            stopTime = action.getStage3();
            Action.actionRunning = true;
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

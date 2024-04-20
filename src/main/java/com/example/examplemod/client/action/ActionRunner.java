package com.example.examplemod.client.action;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.client.init.ModAnimations;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class ActionRunner {
    public static float movementForward;
    public static float movementSideways;
    public static int cooldown;
    private static int inputTime;
    private static int stopTime;
    private static int actionStage;
    private static int actionTime;
    private static boolean actionRunning;
    @Nullable
    private static AbstractAction lastAction;
    @Nullable
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
        for (AbstractAction a : Actions) {
            KeyBinding[] keys = a.getActionKey();
            if ((keys[0] == lastKey && keys[1] == key) || (keys[1] == lastKey && keys[0] == key)) {
                setAction(a);
            }
        }
    }

    public static void setAction(AbstractAction action) {
        if (action == runningAction) {
            return;
        }
        if ((!actionRunning || actionStage == 2)) {
            if (runningAction != null) {
                if (!runningAction.isAvailable(action)) {
                    lastAction = runningAction;
                    return;
                }
            }
            runAction(action);
        } else if (lastAction != null) {
            if (!lastAction.isAvailable(action)) {
                return;
            }
            runAction(action);
        }
    }
    private static void runAction(AbstractAction action){
        runningAction = action;
        cooldown = action.getStage1();
        inputTime = action.getStage2();
        stopTime = action.getStage3();
        ((ModifierLayer<IAnimation>) ModAnimations.playerAssociatedAnimationData.get(new Identifier(ExampleMod.MODID, "main_anim"))).replaceAnimationWithFade(AbstractFadeModifier.functionalFadeIn(20, (modelName, type, value) -> value), new KeyframeAnimationPlayer(action.getActionAnim()), actionRunning);
        actionRunning = true;
        action.run();
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

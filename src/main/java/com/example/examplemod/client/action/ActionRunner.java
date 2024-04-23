package com.example.examplemod.client.action;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.client.init.ModAnimations;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class ActionRunner {
    private static int cooldown;
    private static int inputTime;
    private static int stopTime;
    private static int actionStage;
    private static boolean actionRunning;
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
                    break;
                } else {
                    actionStage = 1;
                    break;
                }
            case 1:
                if (inputTime > 0) {
                    --inputTime;
                    break;
                } else {
                    actionStage = 2;
                    break;
                }
            case 2:
                if (stopTime > 0) {
                    --stopTime;
                    break;
                } else {
                    actionStage = 0;
                    actionRunning = false;
                    runningAction = null;
                    break;
                }
        }
    }

    public static void searchAction(KeyBinding lastKey, KeyBinding key) {
        for (AbstractAction a : Actions) {
            KeyBinding[] keys = a.getActionKey();
            if ((keys[0] == lastKey && keys[1] == key) || (keys[1] == lastKey && keys[0] == key)) {
                isAvailableAction(a);
            }
        }
    }

    public static void isAvailableAction(AbstractAction action) {
        if (action == runningAction) {
            return;
        }
        if (!actionRunning || actionStage == 1) {
            if (runningAction != null) {
                if (!runningAction.isAvailable(action)) {
                    return;
                }
            }
            runAction(action);
        }
    }

    public static void runAction(AbstractAction action) {
        ExampleMod.LOGGER.info(String.valueOf(cooldown));
        runningAction = action;
        cooldown = action.getStage0();
        inputTime = action.getStage1();
        stopTime = action.getStage2();
        int length = (cooldown + inputTime + stopTime);
        ((ModifierLayer<IAnimation>) ModAnimations.playerAssociatedAnimationData.get(new Identifier(ExampleMod.MODID, "main_anim"))).replaceAnimationWithFade(AbstractFadeModifier.functionalFadeIn(length, (modelName, type, value) -> value), new KeyframeAnimationPlayer(action.getActionAnim()), actionRunning);
        actionRunning = true;
        actionStage = 0;
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

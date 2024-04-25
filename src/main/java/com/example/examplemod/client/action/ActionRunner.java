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
import net.minecraft.client.network.ClientPlayerEntity;
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
    public static float actionHeadYaw;
    public static float actionBodyYaw;
    @Nullable
    private static AbstractAction runningAction;
    private static final ArrayList<AbstractAction> Actions = new ArrayList<>();

    public static void actionTick() {
        switch (actionStage) {
            case 0:
                if (!actionRunning) {
                    break;
                }
                if (cooldown > 1) {
                    --cooldown;
                } else {
                    --cooldown;
                    actionStage = 1;
                }
                break;
            case 1:
                if (inputTime > 1) {
                    --inputTime;
                } else {
                    --inputTime;
                    actionStage = 2;
                }
                break;
            case 2:
                if (stopTime > 1) {
                    --stopTime;
                } else {
                    --stopTime;
                    actionStage = 0;
                    actionRunning = false;
                    runningAction = null;
                }
                break;
        }
        if (actionRunning) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            player.headYaw = actionHeadYaw;
            player.bodyYaw = actionBodyYaw;
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
                if (!runningAction.isAvailable(action) && !action.isAvailable()) {
                    return;
                }
            }
            runAction(action);
        }
    }

    public static void runAction(AbstractAction action) {
        runningAction = action;
        cooldown = action.getStage0();
        inputTime = action.getStage1();
        stopTime = action.getStage2();
        int length = (cooldown + inputTime + stopTime);
        ((ModifierLayer<IAnimation>) ModAnimations.playerAssociatedAnimationData.get(new Identifier(ExampleMod.MODID, "main_anim"))).replaceAnimationWithFade(AbstractFadeModifier.functionalFadeIn(length, (modelName, type, value) -> value), new KeyframeAnimationPlayer(action.getActionAnim()), actionRunning);
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        actionHeadYaw = player.getHeadYaw();
        actionBodyYaw = player.getBodyYaw();
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

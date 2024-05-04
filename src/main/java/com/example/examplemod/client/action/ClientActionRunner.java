package com.example.examplemod.client.action;

import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.init.ModAnimations;
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
public class ClientActionRunner {
    private static int cooldown;
    private static int inputTime;
    private static int stopTime;
    private static int actionAge;
    private static int actionStage = 1;
    private static boolean actionRunning;
    public static float actionHeadYaw;
    public static float actionBodyYaw;
    @Nullable
    private static AbstractAction runningAction;
    private static ClientPlayerEntity player;
    private static final ArrayList<AbstractAction> Actions = new ArrayList<>();

    public static void actionTick() {
        switch (actionStage) {
            case 1:
                if (!actionRunning) {
                    break;
                }
                if (cooldown > 0) {
                    --cooldown;
                    break;
                } else {
                    actionStage = 2;
                }
            case 2:
                if (inputTime > 0) {
                    --inputTime;
                    break;
                } else {
                    actionStage = 3;
                }
            case 3:
                if (stopTime > 0) {
                    --stopTime;
                } else {
                    actionAge = 0;
                    actionStage = 1;
                    actionRunning = false;
                    runningAction = null;
                }
                break;
        }
        if (actionRunning) {
            ++actionAge;
            runningAction.onClientTick(player);
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
        if (!actionRunning || actionStage == 2) {
            if (runningAction != null) {
                if (runningAction.isAvailable(action) && action.isAvailable()) {
                    runAction(action);
                }
            } else if (action.isAvailable()) {
                runAction(action);
            }
        }
    }

    public static void runAction(AbstractAction action) {
        runningAction = action;
        cooldown = action.getStage1();
        inputTime = action.getStage2();
        stopTime = action.getStage3();
        int length = (cooldown + inputTime + stopTime);
        ((ModifierLayer<IAnimation>) ModAnimations.playerAssociatedAnimationData.get(ModAnimations.mainAnim)).replaceAnimationWithFade(AbstractFadeModifier.functionalFadeIn(length, (modelName, type, value) -> value), new KeyframeAnimationPlayer(action.getActionAnim()), actionRunning);
        player = MinecraftClient.getInstance().player;
        actionHeadYaw = player.getHeadYaw();
        actionBodyYaw = player.getBodyYaw();
        actionRunning = true;
        actionStage = 1;
        action.clientInit(player);
    }

    public static void tickPlayerYaw() {
        if (actionRunning) {
            player.headYaw = ClientActionRunner.actionHeadYaw;
            player.bodyYaw = ClientActionRunner.actionBodyYaw;
        }
    }

    public static void register(AbstractAction action, KeyBinding key, Identifier actionAnim) {
        KeyBinding[] k = {null, key};
        action.setActionKey(k);
        action.setActionAnim(actionAnim);
        Actions.add(action);
    }

    public static void register(AbstractAction action, KeyBinding lastKey, KeyBinding key, Identifier actionAnim) {
        KeyBinding[] k = {lastKey, key};
        action.setActionKey(k);
        action.setActionAnim(actionAnim);
        Actions.add(action);
    }

    public static int getAge() {
        return actionAge;
    }

    public static int getStage() {
        return actionStage;
    }

    public static boolean isRunning() {
        return actionRunning;
    }

    public static void actionAttackCallBack() {
        if (runningAction != null) {
            runningAction.attacked(player);
        }
    }
}

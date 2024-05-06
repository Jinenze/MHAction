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
    private static int length;
    private static int actionAge;
    public static float actionHeadYaw;
    public static float actionBodyYaw;
    private static boolean canPlayerInput;
    @Nullable
    private static AbstractAction runningAction;
    private static ClientPlayerEntity player;
    @Nullable
    private static ClientActionTick tickRunnable;
    private static final ArrayList<AbstractAction> Actions = new ArrayList<>();

    public static void actionTick() {
        if (runningAction != null) {
            ++actionAge;
            runningAction.onClientTick(actionAge);
            if (tickRunnable != null) {
                tickRunnable.run(player);
            }
            if (actionAge == length) {
                runningAction = null;
                tickRunnable = null;
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
        if (runningAction == null || canPlayerInput) {
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
        length = action.getLength();
        ((ModifierLayer<IAnimation>) ModAnimations.playerAssociatedAnimationData.get(ModAnimations.mainAnim)).replaceAnimationWithFade(AbstractFadeModifier.functionalFadeIn(length, (modelName, type, value) -> value), new KeyframeAnimationPlayer(action.getActionAnim()), runningAction != null);
        player = MinecraftClient.getInstance().player;
        actionHeadYaw = player.getHeadYaw();
        actionBodyYaw = player.getBodyYaw();
        runningAction = action;
        actionAge = 0;
        tickRunnable = null;
        canPlayerInput = false;
        action.clientInit(player);
    }

    public static void tickPlayerYaw() {
        if (runningAction != null) {
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

    public static boolean isRunning() {
        return runningAction != null;
    }

    public static void actionAttackCallBack() {
        if (runningAction != null) {
            runningAction.attacked(player);
        }
    }

    public static void setTick(ClientActionTick tickRunnable) {
        ClientActionRunner.tickRunnable = tickRunnable;
    }

    public interface ClientActionTick {
        void run(ClientPlayerEntity player);
    }
}

package com.example.examplemod.client.action;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.action.Action;
import com.example.examplemod.action.AttackAction;
import com.example.examplemod.action.CounterAction;
import com.example.examplemod.init.ModAnimations;
import com.example.examplemod.item.ModSword;
import com.example.examplemod.network.ClientNetwork;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class ClientActionRunner {
    private static int length;
    private static int actionAge;
    public static ActionYaw actionYaw;
    private static boolean canPlayerAction = true;
    @Nullable
    private static Action runningAction;
    private static ClientPlayerEntity player;
    @Nullable
    private static Consumer<ClientPlayerEntity> tickRunnable;
    private static KeyframeAnimation subAnimation;
    private static final ArrayList<Action> Actions = new ArrayList<>();
    private static final ArrayList<Action> defaultActions = new ArrayList<>();

    public static void actionTick() {
        if (runningAction != null) {
            ++actionAge;
            runningAction.onClientTick(actionAge);
            if (tickRunnable != null) {
                tickRunnable.accept(player);
            }
            if (actionAge == length) {
                reset();
            }
        }
    }

    public static void searchAction(KeyBinding lastKey, KeyBinding key) {
        for (Action a : Actions) {
            KeyBinding[] keys = a.getActionKey();
            if ((keys[0] == lastKey && keys[1] == key) || (keys[1] == lastKey && keys[0] == key)) {
                isAvailableAction(a);
            }
        }
    }

    public static void isAvailableAction(Action action) {
        if (action == runningAction) {
            return;
        }
        if (canPlayerAction && action.isAvailable()) {
            if (runningAction != null) {
                if (runningAction.isAvailable(action)) {
                    runAction(action);
                }
            } else if (isInDefaultAction(action)) {
                runAction(action);
            }
        }
    }

    public static void runAction(Action action) {
        length = action.getLength();
        var animationPlayer = new KeyframeAnimationPlayer(action.getActionAnim()).setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL).setFirstPersonConfiguration(new FirstPersonConfiguration());
        ModifierLayer<IAnimation> layer = ((ModifierLayer<IAnimation>) ModAnimations.playerAssociatedAnimationData.get(ModAnimations.mainAnim));
        layer.replaceAnimationWithFade(AbstractFadeModifier.functionalFadeIn(length, (modelName, type, value) -> value), animationPlayer, runningAction != null);
        if (subAnimation != null){
            var animationPlayer1 = new KeyframeAnimationPlayer(subAnimation).setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL).setFirstPersonConfiguration(new FirstPersonConfiguration());
            ModifierLayer<IAnimation> subLayer = ((ModifierLayer<IAnimation>) ModAnimations.playerAssociatedAnimationData.get(ModAnimations.subAnim));
            subLayer.replaceAnimationWithFade(AbstractFadeModifier.functionalFadeIn(length, (modelName, type, value) -> value), animationPlayer1, true);
        }
        player = MinecraftClient.getInstance().player;
        actionYaw = new ActionYaw(player.getHeadYaw(), player.getBodyYaw());
        runningAction = action;
        actionAge = 0;
        tickRunnable = null;
        canPlayerAction = false;
        action.clientInit(player);
        ClientNetwork.sendStartRequest(action);
        ExampleMod.LOGGER.info(runningAction.ID.toString());
    }

    public static KeyframeAnimation getSubAnimation() {
        return subAnimation;
    }

    public static void setSubAnimation(KeyframeAnimation subAnimation) {
        ClientActionRunner.subAnimation = subAnimation;
    }

    public static void tickPlayerYaw() {
        if (runningAction != null) {
            player.headYaw = actionYaw.getHead();
            player.bodyYaw = actionYaw.getBody();
        }
    }

    public static void register(Action action, KeyBinding key, Identifier actionAnim, Action... availableAction) {
        KeyBinding[] k = {null, key};
        action.setActionKey(k);
        action.setActionAnim(actionAnim);
        action.setAvailableAction(availableAction);
        Actions.add(action);
    }

    public static void register(Action action, KeyBinding lastKey, KeyBinding key, Identifier actionAnim, Action... availableAction) {
        KeyBinding[] k = {lastKey, key};
        action.setActionKey(k);
        action.setActionAnim(actionAnim);
        action.setAvailableAction(availableAction);
        Actions.add(action);
    }

    public static boolean isMainHandSword() {
        if (MinecraftClient.getInstance().player != null) {
//            return MinecraftClient.getInstance().player.getMainHandStack().isIn(ItemTags.SWORDS);
            return MinecraftClient.getInstance().player.getMainHandStack().getItem() instanceof ModSword;
        }
        return false;
    }

    public static void attack() {
        if (runningAction instanceof AttackAction action) {
            List<Entity> entities = ActionHitBox.intersects(player, action.getHitBox(player));
            for (Entity entity : entities) {
                action.hit(player);
            }
            ClientNetwork.sendAttackRequest(entities);
        }
    }

    public static boolean isRunning() {
        return runningAction != null;
    }

    public static void actionAttackCallBack() {
        if (runningAction instanceof CounterAction action) {
            action.attacked(player);
        }
    }

    public static void setTick(Consumer<ClientPlayerEntity> tickRunnable) {
        ClientActionRunner.tickRunnable = tickRunnable;
    }

    //比setTick里的先运行
    public static void run(Consumer<ClientPlayerEntity> tickRunnable) {
        tickRunnable.accept(player);
    }

    public static void addDefaultAction(Action action){
        defaultActions.add(action);
    }

    private static boolean isInDefaultAction(Action action){
        for (Action a : defaultActions) {
            if (a == action) {
                return true;
            }
        }
        return false;
    }

    public static void setCanPlayerAction(boolean canPlayerAction) {
        ClientActionRunner.canPlayerAction = canPlayerAction;
    }

    public static void reset() {
        canPlayerAction = true;
        runningAction = null;
        tickRunnable = null;
    }

    public static class ActionYaw {
        private float head;
        private float body;

        public ActionYaw(float head, float body) {
            this.head = head;
            this.body = body;
        }

        public void playerInput(ClientPlayerEntity player) {
            Vec2f input = player.input.getMovementInput();
            switch ((int) input.x) {
                case 1:
                    head -= input.y == 1.0f ? 45f : 90f;
                    break;
                case -1:
                    head += input.y == 1.0f ? 45f : 90f;
                    break;
            }
            body = head;
        }

        public Vec3d getVec3d() {
            return new Vec3d(-Math.sin(Math.toRadians(head)), 0, Math.cos(Math.toRadians(head)));
        }

        public float getHead() {
            return head;
        }

        public float getBody() {
            return body;
        }
    }
}

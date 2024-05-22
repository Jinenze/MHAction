package com.jez.mha.client.action;

import com.jez.mha.MHAction;
import com.jez.mha.action.Action;
import com.jez.mha.action.AttackAction;
import com.jez.mha.action.CounterAction;
import com.jez.mha.init.ModAnimations;
import com.jez.mha.item.ModSword;
import com.jez.mha.network.ClientNetwork;
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
public class ClientActionProcessor {
    private int length;
    private int actionAge;
    private ActionYaw actionYaw;
    private boolean canPlayerAction = true;
    private boolean subActionRunning;
    @Nullable
    private Action runningAction;
    private ClientPlayerEntity player;
    @Nullable
    private Consumer<ClientPlayerEntity> tickRunnable;
    private final ArrayList<Action> Actions = new ArrayList<>();
    private final ArrayList<Action> defaultActions = new ArrayList<>();

    public void actionTick() {
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

    public void searchAction(KeyBinding lastKey, KeyBinding key) {
        for (Action a : Actions) {
            KeyBinding[] keys = a.getActionKey();
            if ((keys[0] == lastKey && keys[1] == key) || (keys[1] == lastKey && keys[0] == key)) {
                isAvailableAction(a);
            }
        }
    }

    public void isAvailableAction(Action action) {
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

    public void runAction(Action action) {
        player = MinecraftClient.getInstance().player;
        length = action.getLength();
        playMainAnim(action.getActionAnim(), length);
        actionYaw = new ActionYaw(player.getHeadYaw(), player.getBodyYaw());
        runningAction = action;
        actionAge = 0;
        tickRunnable = null;
        canPlayerAction = false;
        action.clientInit(player);
        ClientNetwork.sendStartRequest(action);
        MHAction.LOGGER.info(runningAction.ID.toString());
    }

    public void register(Action action, KeyBinding key, Identifier actionAnim, Action... availableAction) {
        KeyBinding[] k = {null, key};
        action.setActionKey(k);
        action.setActionAnim(actionAnim);
        action.setAvailableAction(availableAction);
        Actions.add(action);
    }

    public void register(Action action, KeyBinding lastKey, KeyBinding key, Identifier actionAnim, Action... availableAction) {
        KeyBinding[] k = {lastKey, key};
        action.setActionKey(k);
        action.setActionAnim(actionAnim);
        action.setAvailableAction(availableAction);
        Actions.add(action);
    }

    public boolean isMainHandSword() {
        if (MinecraftClient.getInstance().player != null) {
//            return MinecraftClient.getInstance().player.getMainHandStack().isIn(ItemTags.SWORDS);
            return MinecraftClient.getInstance().player.getMainHandStack().getItem() instanceof ModSword;
        }
        return false;
    }

    public void attack() {
        if (runningAction instanceof AttackAction action) {
            List<Entity> entities = ActionHitBox.intersects(player, action.getHitBox(player));
            for (Entity ignored : entities) {
                action.hit(player);
            }
            ClientNetwork.sendAttackRequest(entities);
        }
    }

    public void playMainAnim(KeyframeAnimation animation, int length) {
        var animationPlayer = new KeyframeAnimationPlayer(animation).setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL).setFirstPersonConfiguration(new FirstPersonConfiguration());
        var mainAnim = ((ModifierLayer<IAnimation>) ModAnimations.playerAssociatedAnimationData.get(ModAnimations.mainAnim));
        mainAnim.replaceAnimationWithFade(AbstractFadeModifier.functionalFadeIn(length, (modelName, type, value) -> value), animationPlayer, runningAction != null);
    }

    public boolean isRunning() {
        return runningAction != null || subActionRunning;
    }

    public void actionAttackCallBack() {
        if (runningAction instanceof CounterAction action) {
            action.attacked(player);
        }
    }

    public void setTick(Consumer<ClientPlayerEntity> tickRunnable) {
        this.tickRunnable = tickRunnable;
    }

    //比setTick里的先运行
    public void run(Consumer<ClientPlayerEntity> tickRunnable) {
        tickRunnable.accept(player);
    }

    public void addDefaultAction(Action action) {
        defaultActions.add(action);
    }

    private boolean isInDefaultAction(Action action) {
        for (Action a : defaultActions) {
            if (a == action) {
                return true;
            }
        }
        return false;
    }

    public void setCanPlayerAction(boolean canPlayerAction) {
        this.canPlayerAction = canPlayerAction;
    }

    public void playSubAnim(KeyframeAnimation animation) {
        var animationPlayer = new KeyframeAnimationPlayer(animation).setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL).setFirstPersonConfiguration(new FirstPersonConfiguration());
        var subAnim = ((ModifierLayer<IAnimation>) ModAnimations.playerAssociatedAnimationData.get(ModAnimations.subAnim));
        subAnim.setAnimation(animationPlayer);
        subActionRunning = true;
    }

    public void stopSubAnim() {
        var subAnim = ((ModifierLayer<IAnimation>) ModAnimations.playerAssociatedAnimationData.get(ModAnimations.subAnim));
        subAnim.setAnimation(null);
        subActionRunning = false;
    }

    public boolean isSubActionRunning() {
        return subActionRunning;
    }

    public void reset() {
        canPlayerAction = true;
        runningAction = null;
        tickRunnable = null;
    }

    public void tickPlayerYaw() {
        if (runningAction != null) {
            player.headYaw = actionYaw.getHead();
            player.bodyYaw = actionYaw.getBody();
        }
    }

    public ActionYaw getActionYaw() {
        return actionYaw;
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

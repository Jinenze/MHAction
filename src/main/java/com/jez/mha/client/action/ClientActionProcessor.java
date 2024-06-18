package com.jez.mha.client.action;

import com.jez.mha.MHAction;
import com.jez.mha.action.Action;
import com.jez.mha.action.AttackAction;
import com.jez.mha.action.CounterAction;
import com.jez.mha.action.WeaponActions;
import com.jez.mha.client.action.impl.ClientProcessor;
import com.jez.mha.client.input.KeyBind;
import com.jez.mha.init.ModAnimations;
import com.jez.mha.item.IMhaSword;
import com.jez.mha.network.ClientNetwork;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class ClientActionProcessor implements ClientProcessor {
    private int length;
    private int actionAge;
    private ActionYaw actionYaw;
    private boolean canPlayerAction = true;
    @Nullable
    private Action runningAction;
    @Nullable
    private Consumer<ClientPlayerEntity> tickRunnable;
    private boolean equipped;
    private boolean ready;
    private List<Action> actions;
    private List<Action> defaultActions;
    private final ClientPlayerEntity player;
    private final KeyBind keyBind;

    public ClientActionProcessor(ClientPlayerEntity player) {
        this.player = player;
        keyBind = new KeyBind(this);
    }

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
        if (isMainHandSword()) {
            keyBind.keyBindTick();
        }
    }

    public void searchAction(KeyBinding lastKey, KeyBinding key) {
        if (runningAction == null) {
            for (Action action : defaultActions) {
                KeyBinding[] keys = action.getActionKey();
                if ((keys[0] == lastKey && keys[1] == key) || (keys[1] == lastKey && keys[0] == key)) {
                    isAvailableAction(action);
                }
            }
        } else {
            for (Action action : actions) {
                KeyBinding[] keys = action.getActionKey();
                if ((keys[0] == lastKey && keys[1] == key) || (keys[1] == lastKey && keys[0] == key)) {
                    isAvailableAction(action);
                }
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
            } else {
                runAction(action);
            }
        }
    }

    public void runAction(Action action) {
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
        ModAnimations.mainAnim.replaceAnimationWithFade(AbstractFadeModifier.functionalFadeIn(length, (modelName, type, value) -> value), animationPlayer);
    }

    public boolean isMainActionRunning() {
        return runningAction != null;
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

    public void setCanPlayerAction(boolean canPlayerAction) {
        this.canPlayerAction = canPlayerAction;
    }

    public void playSubAnim(KeyframeAnimation animation) {
        var animationPlayer = new KeyframeAnimationPlayer(animation).setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL).setFirstPersonConfiguration(new FirstPersonConfiguration());
        ModAnimations.subAnim.setAnimation(animationPlayer);
    }

    public void stopSubAnim() {
        ModAnimations.subAnim.setAnimation(null);
    }

    public void setProcessorActions(WeaponActions weaponActions) {
        defaultActions = weaponActions.getDefaultActions();
        actions = weaponActions.getActions();
    }

    public ClientPlayerEntity getPlayer() {
        return player;
    }

    public KeyBind getKeyBind() {
        return keyBind;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
    }

    public ActionYaw getActionYaw() {
        return actionYaw;
    }

    public void tickPlayerYaw() {
        if (runningAction != null) {
            player.headYaw = actionYaw.getHead();
            player.bodyYaw = actionYaw.getBody();
        }
    }

    public void reset() {
        canPlayerAction = true;
        runningAction = null;
        tickRunnable = null;
    }

    public boolean isMainHandSword() {
        if (MinecraftClient.getInstance().player != null) {
//            return MinecraftClient.getInstance().player.getMainHandStack().isIn(ItemTags.SWORDS);
            return MinecraftClient.getInstance().player.getMainHandStack().getItem() instanceof IMhaSword;
        }
        return false;
    }
}

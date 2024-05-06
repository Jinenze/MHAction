package com.example.examplemod.client.action;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.action.AttackAction;
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
import net.minecraft.entity.Entity;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ClientActionRunner {
    private static int length;
    private static int actionAge;
    public static ActionYaw actionYaw;
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
                reset();
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
        actionYaw = new ActionYaw(player.getHeadYaw(), player.getBodyYaw());
        runningAction = action;
        actionAge = 0;
        tickRunnable = null;
        canPlayerInput = false;
        action.clientInit(player);
    }

    public static void tickPlayerYaw() {
        if (runningAction != null) {
            player.headYaw = actionYaw.getHead();
            player.bodyYaw = actionYaw.getBody();
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

    public static boolean isMainHandSword() {
        if (MinecraftClient.getInstance().player != null) {
            return MinecraftClient.getInstance().player.getMainHandStack().isIn(ItemTags.SWORDS);
        }
        return false;
    }

    public static void attack() {
        if (runningAction instanceof AttackAction) {
            for (Entity entity : ActionHitBox.intersects(player, ((AttackAction) runningAction).getHitBox(player))) {
                entity.damage(player.getDamageSources().playerAttack(player), ((SwordItem) player.getMainHandStack().getItem()).getAttackDamage());
            }
        }
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

    //比setTick里的先运行
    public static void run(ClientActionTick tickRunnable) {
        tickRunnable.run(player);
    }

    public static void reset() {
        canPlayerInput = false;
        runningAction = null;
        tickRunnable = null;
    }

    public interface ClientActionTick {
        void run(ClientPlayerEntity player);
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

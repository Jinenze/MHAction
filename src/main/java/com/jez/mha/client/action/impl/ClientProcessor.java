package com.jez.mha.client.action.impl;

import com.jez.mha.action.Action;
import com.jez.mha.action.WeaponActions;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public interface ClientProcessor {
    void actionTick();

    void searchAction(KeyBinding lastKey, KeyBinding key);

    void isAvailableAction(Action action);

    void runAction(Action action);

    void attack();

    void playMainAnim(KeyframeAnimation animation, int length);

    boolean isMainActionRunning();

    void actionAttackCallBack();

    void setTick(Consumer<ClientPlayerEntity> tickRunnable);

    //比setTick里的先运行
    void run(Consumer<ClientPlayerEntity> tickRunnable);

    void setCanPlayerAction(boolean canPlayerAction);

    void playSubAnim(KeyframeAnimation animation);

    void stopSubAnim();

    void tickPlayerYaw();

    void setProcessorActions(WeaponActions weaponActions);

    ActionYaw getActionYaw();

    ClientPlayerEntity getPlayer();

    boolean isEquipped();

    void setEquipped(boolean equipped);

    void setReady(boolean ready);

    boolean isReady();

    void reset();

    boolean isMainHandSword();

    class ActionYaw {
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

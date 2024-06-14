package com.jez.mha.client.action.impl;

import com.jez.mha.action.Action;
import com.jez.mha.action.WeaponActions;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class DummyClientProcessor implements ClientProcessor {

    private static final DummyClientProcessor INSTANCE = new DummyClientProcessor();

    public static DummyClientProcessor getInstance(){
        return INSTANCE;
    }

    private DummyClientProcessor(){
    }

    @Override
    public void actionTick() {

    }

    @Override
    public void searchAction(KeyBinding lastKey, KeyBinding key) {

    }

    @Override
    public void isAvailableAction(Action action) {

    }

    @Override
    public void runAction(Action action) {

    }

    @Override
    public void attack() {

    }

    @Override
    public void playMainAnim(KeyframeAnimation animation, int length) {

    }

    @Override
    public boolean isMainActionRunning() {
        return false;
    }

    @Override
    public void actionAttackCallBack() {

    }

    @Override
    public void setTick(Consumer<ClientPlayerEntity> tickRunnable) {

    }

    @Override
    public void run(Consumer<ClientPlayerEntity> tickRunnable) {

    }

    @Override
    public void setCanPlayerAction(boolean canPlayerAction) {

    }

    @Override
    public void playSubAnim(KeyframeAnimation animation) {

    }

    @Override
    public void stopSubAnim() {

    }

    @Override
    public void tickPlayerYaw() {

    }

    @Override
    public void setProcessorActions(WeaponActions weaponActions) {

    }

    @Override
    public ActionYaw getActionYaw() {
        return null;
    }

    @Override
    public ClientPlayerEntity getPlayer() {
        return null;
    }

    @Override
    public boolean isEquipped() {
        return false;
    }

    @Override
    public void setEquipped(boolean equipped) {

    }

    @Override
    public void setReady(boolean ready) {

    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean isMainHandSword() {
        return false;
    }
}

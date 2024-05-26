package com.jez.mha.init;

import com.jez.mha.action.Action;
import com.jez.mha.action.WeaponActions;
import com.jez.mha.action.impl.Dodge;
import com.jez.mha.action.longsword.DrawSword;
import com.jez.mha.action.longsword.OverHeadSlash;
import com.jez.mha.action.longsword.StepSlash;
import com.jez.mha.action.longsword.Unload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModActions {
    public static Action DODGE = new Dodge("dodge", 20);
    public static Action UNLOAD = new Unload("unload", 20);
    public static Action DRAW_SWORD = new DrawSword("draw_sword", 20);
    public static Action STEP_SLASH = new StepSlash("step_slash", 20);
    public static Action OVER_HEAD_SLASH = new OverHeadSlash("over_head_slash", 20);

    public static final ArrayList<Action> DEFAULT = new ArrayList<>();
    public static final ArrayList<Action> ACTIONS = new ArrayList<>();

    @Environment(EnvType.CLIENT)
    public static void client() {
        ModKeyBinds.init();
        register(null, DODGE, ModKeyBinds.DODGE_KEY, ModAnimations.DODGE);
        DEFAULT.add(DODGE);
        LongSword.DEFAULT.addAll(DEFAULT);
        register(LongSword.DEFAULT, STEP_SLASH, ModKeyBinds.ATTACK, STEP_SLASH.ID, OVER_HEAD_SLASH);
        register(LongSword.ACTIONS, OVER_HEAD_SLASH, ModKeyBinds.ATTACK, STEP_SLASH.ID);
        register(LongSword.ACTIONS, DRAW_SWORD, null, STEP_SLASH.ID);
        register(LongSword.ACTIONS, UNLOAD, null, STEP_SLASH.ID);
    }

    public static void register() {

    }

    @Nullable
    public static Action inList(Identifier ID) {
        for (Action action : ACTIONS) {
            if (action.ID.equals(ID)) return action;
        }
        return null;
    }

    public static void register(@Nullable List<Action> actionList, Action action, @Nullable KeyBinding key, @Nullable Identifier actionAnim, Action... availableAction) {
        KeyBinding[] k = {null, key};
        if (key == null) {
            k = new KeyBinding[]{ModKeyBinds.ATTACK,ModKeyBinds.ATTACK};
        }
        action.setActionKey(k);
        action.setActionAnim(Objects.requireNonNullElseGet(actionAnim, () -> action.ID));
        action.setAvailableAction(availableAction);
        ACTIONS.add(action);
        if (actionList != null) {
            actionList.add(action);
        }
    }

    public static void register(@Nullable List<Action> actionList,Action action, KeyBinding lastKey, KeyBinding key, @Nullable Identifier actionAnim, Action... availableAction) {
        KeyBinding[] k = {lastKey, key};
        action.setActionKey(k);
        action.setActionAnim(Objects.requireNonNullElseGet(actionAnim, () -> action.ID));
        action.setAvailableAction(availableAction);
        ACTIONS.add(action);
        if (actionList != null) {
            actionList.add(action);
        }
    }

    public static class LongSword implements WeaponActions {
        public static final LongSword INSTANCE = new LongSword();
        private static final List<Action> DEFAULT = new ArrayList<>();
        private static final List<Action> ACTIONS = new ArrayList<>();

        @Override
        public List<Action> getDefaultActions() {
            return DEFAULT;
        }

        @Override
        public List<Action> getActions() {
            return ACTIONS;
        }
    }
}

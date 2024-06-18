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

public class ModActions {
    public static final Action DODGE = new Dodge("dodge", 20);
    public static final Action UNLOAD = new Unload("unload", 20);
    public static final Action DRAW_SWORD = new DrawSword("katana_draw", 20);
    public static final Action STEP_SLASH = new StepSlash("step_slash", 20);
    public static final Action OVER_HEAD_SLASH = new OverHeadSlash("over_head_slash", 20);

    public static final ArrayList<Action> DEFAULT = new ArrayList<>();
    public static final ArrayList<Action> ACTIONS = new ArrayList<>();

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        register(DEFAULT, DODGE, ModKeyBinds.DODGE_KEY);
        LongSword.getInstance().getDefaultActions().addAll(DEFAULT);
        register(LongSword.getInstance().getDefaultActions(), STEP_SLASH, ModKeyBinds.ATTACK);
        register(LongSword.getInstance().getActions(), OVER_HEAD_SLASH, ModKeyBinds.ATTACK);
        register(LongSword.getInstance().getActions(), DRAW_SWORD, null);
        register(LongSword.getInstance().getActions(), UNLOAD, null);
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

    public static void register(List<Action> actionList, Action action, @Nullable KeyBinding key, Action... availableAction) {
        KeyBinding[] k = {null, key};
        if (key == null) {
            k = new KeyBinding[]{ModKeyBinds.EQUIP, ModKeyBinds.EQUIP};
        }
        action.setActionKey(k);
        action.setAvailableAction(availableAction);
        ACTIONS.add(action);
        actionList.add(action);
    }

    public static void register(List<Action> actionList, Action action, KeyBinding lastKey, KeyBinding key, Action... availableAction) {
        KeyBinding[] k = {lastKey, key};
        action.setActionKey(k);
        action.setAvailableAction(availableAction);
        ACTIONS.add(action);
        actionList.add(action);
    }

    public static class LongSword implements WeaponActions {
        private static final LongSword INSTANCE = new LongSword();
        private final List<Action> DEFAULT = new ArrayList<>();
        private final List<Action> ACTIONS = new ArrayList<>();

        public static LongSword getInstance() {
            return INSTANCE;
        }

        @Override
        public List<Action> getDefaultActions() {
            return DEFAULT;
        }

        @Override
        public List<Action> getActions() {
            return ACTIONS;
        }

        private LongSword() {
        }
    }
}

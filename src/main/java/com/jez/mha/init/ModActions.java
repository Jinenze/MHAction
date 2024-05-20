package com.jez.mha.init;

import com.jez.mha.action.Action;
import com.jez.mha.action.impl.Dodge;
import com.jez.mha.action.longsword.DrawSword;
import com.jez.mha.action.longsword.OverHeadSlash;
import com.jez.mha.action.longsword.StepSlash;
import com.jez.mha.client.ModClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ModActions {
    public static Action DODGE = new Dodge("dodge", 20);
    public static Action DRAW_SWORD = new DrawSword("draw_sword",20);
    public static Action STEP_SLASH = new StepSlash("step_slash",20);
    public static Action OVER_HEAD_SLASH = new OverHeadSlash("over_head_slash", 20);

    public static final ArrayList<Action> ACTIONS = new ArrayList<>();

    @Environment(EnvType.CLIENT)
    public static void client() {
        ModKeyBinds.init();
        ModClient.processor.register(DODGE, ModKeyBinds.DODGE_KEY, ModAnimations.DODGE);
        ModClient.processor.addDefaultAction(DODGE);
        ModClient.processor.register(STEP_SLASH, ModKeyBinds.ATTACK, STEP_SLASH.ID, OVER_HEAD_SLASH);
        ModClient.processor.addDefaultAction(STEP_SLASH);
        ModClient.processor.register(OVER_HEAD_SLASH, ModKeyBinds.ATTACK, STEP_SLASH.ID);
    }

    public static void register() {
        ACTIONS.add(DODGE);
    }

    @Nullable
    public static Action inList(Identifier ID) {
        for (Action action : ACTIONS) {
            if (action.ID.equals(ID)) return action;
        }
        return null;
    }
//    public static final ActionList LongSword = new ActionList(DODGE);
//    public record ActionList(AbstractAction... actions) {
//        @Nullable
//        public AbstractAction inList(Identifier ID) {
//            for (AbstractAction action : actions) {
//                if (action.ID.equals(ID)) return action;
//            }
//            return null;
//        }
//    }
}

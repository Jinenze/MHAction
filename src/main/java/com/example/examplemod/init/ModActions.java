package com.example.examplemod.init;

import com.example.examplemod.action.Action;
import com.example.examplemod.action.longsword.OverHeadSlash;
import com.example.examplemod.action.longsword.StepSlash;
import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.action.impl.Dodge;
import com.example.examplemod.client.input.KeyBind;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ModActions {
    public static Action DODGE = new Dodge("dodge", 20);
    public static Action STEP_SLASH = new StepSlash("step_slash",20);
    public static Action OVER_HEAD_SLASH = new OverHeadSlash("over_head_slash", 20);
    public static final ArrayList<Action> ACTIONS = new ArrayList<>();

    @Environment(EnvType.CLIENT)
    public static void client() {
        ClientActionRunner.register(DODGE, ModKeyBinds.DODGE_KEY, ModAnimations.DODGE);
        ClientActionRunner.addDefaultAction(DODGE);
        ClientActionRunner.register(STEP_SLASH, KeyBind.register(MinecraftClient.getInstance().options.attackKey), STEP_SLASH.ID, OVER_HEAD_SLASH);
        ClientActionRunner.addDefaultAction(STEP_SLASH);
        ClientActionRunner.register(OVER_HEAD_SLASH, KeyBind.register(MinecraftClient.getInstance().options.attackKey), STEP_SLASH.ID);
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

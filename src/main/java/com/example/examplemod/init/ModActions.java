package com.example.examplemod.init;

import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.action.longsword.OverHeadSlash;
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
    public static AbstractAction DODGE = new Dodge("dodge", 20);
    public static AbstractAction STEP_SLASH = new OverHeadSlash("step_slash",20);
    public static AbstractAction OVER_HEAD_SLASH = new OverHeadSlash("over_head_slash", 20);
    public static final ArrayList<AbstractAction> ACTIONS = new ArrayList<>();

    @Environment(EnvType.CLIENT)
    public static void client() {
        ClientActionRunner.register(DODGE, ModKeyBinds.DODGE_KEY, ModAnimations.DODGE);
        ClientActionRunner.addDefaultAction(DODGE);
        ClientActionRunner.register(STEP_SLASH, KeyBind.register(MinecraftClient.getInstance().options.attackKey), ModAnimations.DODGE, OVER_HEAD_SLASH);
        ClientActionRunner.addDefaultAction(STEP_SLASH);
        ClientActionRunner.register(OVER_HEAD_SLASH, KeyBind.register(MinecraftClient.getInstance().options.attackKey), ModAnimations.DODGE);
    }

    public static void register() {
        ACTIONS.add(DODGE);
    }

    @Nullable
    public static AbstractAction inList(Identifier ID) {
        for (AbstractAction action : ACTIONS) {
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

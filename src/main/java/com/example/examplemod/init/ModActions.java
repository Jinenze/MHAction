package com.example.examplemod.init;

import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.action.impl.Dodge;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ModActions {
    public static final ArrayList<AbstractAction> Actions = new ArrayList<>();
    public static AbstractAction DODGE = new Dodge("dodge", 20);

    @Environment(EnvType.CLIENT)
    public static void client() {
        ClientActionRunner.register(DODGE, ModKeyBinds.DODGE_KEY, ModAnimations.DODGE);
    }

    @Nullable
    public static AbstractAction inList(Identifier ID) {
        for (AbstractAction action : Actions) {
            if (action.ID.equals(ID)) return action;
        }
        return null;
    }

    public static void register() {
        Actions.add(DODGE);
    }
}

package com.example.examplemod.init;

import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.action.impl.Dodge;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ModActions {
    public static AbstractAction DODGE = new Dodge(20);

    @Environment(EnvType.CLIENT)
    public static void client() {
        ClientActionRunner.register(DODGE, ModKeyBinds.DODGE_KEY, ModAnimations.DODGE);
    }

    public static void register() {

    }
}

package com.example.examplemod.init;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.action.impl.Dodge;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ModActions {
    public static AbstractAction DODGE;

    @Environment(EnvType.CLIENT)
    public static void client() {
        ClientActionRunner.register(DODGE, ModKeyBinds.DODGE_KEY, ModAnimations.DODGE);
        DODGE = new Dodge(ExampleMod.config.dodge);
    }

    public static void register() {
        DODGE = new Dodge(ExampleMod.config.dodge);
    }
}

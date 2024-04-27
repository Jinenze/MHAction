package com.example.examplemod.init;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.action.impl.Dodge;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

public class ModActions {
    public static final AbstractAction DODGE = new Dodge(20, 0, 5, new Identifier(ExampleMod.MODID, "nmlgb"));

    @Environment(EnvType.CLIENT)
    public static void client() {
        ClientActionRunner.register(DODGE, ModKeyBinds.DODGE_KEY);
    }

    public static void server() {

    }
}

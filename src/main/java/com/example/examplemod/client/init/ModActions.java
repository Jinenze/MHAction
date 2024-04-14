package com.example.examplemod.client.init;

import com.example.examplemod.client.action.Action;
import com.example.examplemod.client.action.Dodge;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModActions {
    public static final Dodge DODGE = new Dodge(1, 1, 1);

    public static void register() {
        Action.register(DODGE, ModKeyBinds.DODGE_KEY);
    }
}

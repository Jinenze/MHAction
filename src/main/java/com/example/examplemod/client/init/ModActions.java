package com.example.examplemod.client.init;

import com.example.examplemod.client.action.Action;
import com.example.examplemod.client.action.Dodge;

public class ModActions {
    public static final Dodge DODGE = new Dodge(1, 1, 1);

    public static void register() {
        Action.register(DODGE, ModKeyBinds.DODGE_KEY);
    }
}

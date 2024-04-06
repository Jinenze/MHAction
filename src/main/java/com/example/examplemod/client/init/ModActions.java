package com.example.examplemod.client.init;

import com.example.examplemod.client.action.Action;
import com.example.examplemod.client.action.Dodge;

public class ModActions {
    public static void register() {
        Action.actionTick();
        Action.register(ModKeyBinds.DODGE_KEY, 1, 1, 1, new Dodge());
    }
}

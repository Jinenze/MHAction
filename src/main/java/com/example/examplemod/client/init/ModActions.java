package com.example.examplemod.client.init;

import com.example.examplemod.client.action.Action;
import com.example.examplemod.client.action.Dodge;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ModActions {
    public static void register() {
        ClientTickEvents.START_CLIENT_TICK.register((client) -> Action.actionTick());
        Action.register(ModKeyBinds.DODGE_KEY, 1, 1, 1, new Action());
    }
}

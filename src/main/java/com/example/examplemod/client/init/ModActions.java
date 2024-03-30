package com.example.examplemod.client.init;

import com.example.examplemod.client.action.Action;
import com.example.examplemod.client.action.Dodge;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ModActions {
    public static void register() {
        ClientTickEvents.START_CLIENT_TICK.register((client) -> {
            if (Action.running) {
                if (Action.cooldown > 0) {
                    --Action.cooldown;
                } else {
                    Action.running = false;
                }
            }
        });
        Action.register(ModKeyBinds.DODGE_KEY, 1, 1, 1, new Dodge());
    }
}

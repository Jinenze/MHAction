package com.example.examplemod.client;

import com.example.examplemod.client.action.Action;
import com.example.examplemod.client.init.ModActions;
import com.example.examplemod.client.init.ModKeyBinds;
import com.example.examplemod.client.input.KeyBind;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

@Environment(EnvType.CLIENT)
public class ExampleModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register((client) -> {
            Action.actionTick();
            KeyBind.keyBindTick();
        });
        ModKeyBinds.register();
        ModActions.register();
    }
}

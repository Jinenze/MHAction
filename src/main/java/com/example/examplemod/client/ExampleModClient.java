package com.example.examplemod.client;

import com.example.examplemod.client.init.ModActions;
import com.example.examplemod.client.init.ModKeyBinds;
import net.fabricmc.api.ClientModInitializer;

public class ExampleModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModKeyBinds.register();
        ModActions.register();
    }
}

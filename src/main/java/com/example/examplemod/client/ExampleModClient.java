package com.example.examplemod.client;

import com.example.examplemod.client.init.ModActions;
import com.example.examplemod.client.init.ModKeyBinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ExampleModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModKeyBinds.register();
        ModActions.register();

    }
}

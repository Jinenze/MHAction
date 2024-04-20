package com.example.examplemod.client;

import com.example.examplemod.client.action.ActionRunner;
import com.example.examplemod.client.init.ModActions;
import com.example.examplemod.client.init.ModAnimations;
import com.example.examplemod.client.init.ModKeyBinds;
import com.example.examplemod.client.input.KeyBind;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

@Environment(EnvType.CLIENT)
public class ExampleModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register((client) -> {
            ActionRunner.actionTick();
            KeyBind.keyBindTick();
        });
        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(ModAnimations::register);
        ModKeyBinds.register();
        ModActions.register();
    }
}

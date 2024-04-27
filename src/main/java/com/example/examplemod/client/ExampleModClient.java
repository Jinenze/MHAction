package com.example.examplemod.client;

import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.init.ModActions;
import com.example.examplemod.init.ModAnimations;
import com.example.examplemod.init.ModKeyBinds;
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
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (ClientActionRunner.isActionRunning()) {
                ClientActionRunner.player.headYaw = ClientActionRunner.actionHeadYaw;
                ClientActionRunner.player.bodyYaw = ClientActionRunner.actionBodyYaw;
            }
        });
        ClientTickEvents.START_WORLD_TICK.register((client) -> {
            KeyBind.keyBindTick();
            ClientActionRunner.actionTick();
        });
        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(ModAnimations::register);
        ModKeyBinds.register();
        ModActions.client();
    }
}

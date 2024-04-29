package com.example.examplemod.client;

import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.config.ClientConfig;
import com.example.examplemod.config.ClientConfigWrapper;
import com.example.examplemod.init.ModAnimations;
import com.example.examplemod.init.ModKeyBinds;
import com.example.examplemod.client.input.KeyBind;
import com.example.examplemod.network.ClientNetwork;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

@Environment(EnvType.CLIENT)
public class ExampleModClient implements ClientModInitializer {
    public static ClientConfig config;

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
        AutoConfig.register(ClientConfigWrapper.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(ClientConfigWrapper.class).getConfig().client;
        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(ModAnimations::register);
        ClientNetwork.register();
        ModKeyBinds.register();
    }
}

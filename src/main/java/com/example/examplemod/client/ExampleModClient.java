package com.example.examplemod.client;

import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.config.ClientConfig;
import com.example.examplemod.config.ClientConfigWrapper;
import com.example.examplemod.entity.renderer.TempleEntityRenderer;
import com.example.examplemod.init.ModAnimations;
import com.example.examplemod.init.ModEntities;
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
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ExampleModClient implements ClientModInitializer {
    public static ClientConfig config;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            ClientActionRunner.tickPlayerYaw();
        });
        ClientTickEvents.START_WORLD_TICK.register((client) -> {
            KeyBind.tickSwitch();
            if (KeyBind.isEnabled()) {
                KeyBind.keyBindTick();
                ClientActionRunner.actionTick();
            }
        });
        EntityRendererRegistry.register(ModEntities.TEMPLE, TempleEntityRenderer::new);
        AutoConfig.register(ClientConfigWrapper.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(ClientConfigWrapper.class).getConfig().client;
        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(ModAnimations::register);
        ClientNetwork.register();
        ModKeyBinds.register();
    }
}

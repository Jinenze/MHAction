package com.example.examplemod.client;

import com.example.examplemod.client.action.ClientActionRunner;
import com.example.examplemod.client.render.ModelSwapper;
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
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class ExampleModClient implements ClientModInitializer {
    public static final ModelSwapper MODEL_SWAPPER = new ModelSwapper();

    public static ClientConfig config;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register((client) -> {
            if (MinecraftClient.getInstance().getOverlay() == null && MinecraftClient.getInstance().currentScreen == null) {
                KeyBind.tickSwitch();
                ClientActionRunner.actionTick();
                if (KeyBind.isEnabled() && ClientActionRunner.isMainHandSword()) {
                    KeyBind.keyBindTick();
                }
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            ClientActionRunner.tickPlayerYaw();
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ClientActionRunner.reset();
        });
        AutoConfig.register(ClientConfigWrapper.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(ClientConfigWrapper.class).getConfig().client;

        MODEL_SWAPPER.registerListeners();

        EntityRendererRegistry.register(ModEntities.TEMPLE, TempleEntityRenderer::new);

        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(ModAnimations::register);
        ClientNetwork.register();
        ModKeyBinds.register();
    }
}

package com.jez.mha.client;

import com.jez.mha.client.action.ClientActionProcessor;
import com.jez.mha.client.input.KeyBind;
import com.jez.mha.client.render.ModelSwapper;
import com.jez.mha.config.ClientConfig;
import com.jez.mha.config.ClientConfigWrapper;
import com.jez.mha.entity.renderer.TempleEntityRenderer;
import com.jez.mha.init.ModAnimations;
import com.jez.mha.init.ModEntities;
import com.jez.mha.init.ModKeyBinds;
import com.jez.mha.network.ClientNetwork;
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
public class ModClient implements ClientModInitializer {
    public static final ModelSwapper MODEL_SWAPPER = new ModelSwapper();
    public static ClientActionProcessor processor = new ClientActionProcessor();

    public static ClientConfig config;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register((client) -> {
            if (MinecraftClient.getInstance().getOverlay() == null && MinecraftClient.getInstance().currentScreen == null) {
                KeyBind.tickSwitch();
                processor.actionTick();
                if (KeyBind.isEnabled() && processor.isMainHandSword()) {
                    KeyBind.keyBindTick();
                }
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            processor.tickPlayerYaw();
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            processor.reset();
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

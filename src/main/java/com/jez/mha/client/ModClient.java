package com.jez.mha.client;

import com.jez.mha.client.action.ClientActionProcessor;
import com.jez.mha.client.action.impl.ClientProcessor;
import com.jez.mha.client.action.impl.DummyClientProcessor;
import com.jez.mha.client.render.ModelSwapper;
import com.jez.mha.config.ClientConfig;
import com.jez.mha.config.ClientConfigWrapper;
import com.jez.mha.init.ModAnimations;
import com.jez.mha.init.ModEntities;
import com.jez.mha.init.ModKeyBinds;
import com.jez.mha.init.ModUi;
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

@Environment(EnvType.CLIENT)
public class ModClient implements ClientModInitializer {
    public static final ModelSwapper MODEL_SWAPPER = new ModelSwapper();
    public static ClientProcessor processor = DummyClientProcessor.getInstance();

    public static ClientConfig config;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register((client) -> {
            if (client.getOverlay() == null && client.currentScreen == null) {
                processor.actionTick();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            processor.tickPlayerYaw();
        });
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            processor = new ClientActionProcessor(client.player);
        });
        AutoConfig.register(ClientConfigWrapper.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(ClientConfigWrapper.class).getConfig().client;

        MODEL_SWAPPER.registerListeners();

        ModEntities.client();

        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(ModAnimations::register);
        ClientNetwork.register();
        ModKeyBinds.register();
        ModUi.init();
    }
}

package com.example.examplemod;

import com.example.examplemod.config.ServerConfig;
import com.example.examplemod.config.ServerConfigWrapper;
import com.example.examplemod.init.ModActions;
import com.example.examplemod.init.ModEntities;
import com.example.examplemod.init.ModItems;
import com.example.examplemod.init.ModSound;
import com.example.examplemod.network.ServerNetwork;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
    public static final String MODID = "examplemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static ServerConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(ServerConfigWrapper.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(ServerConfigWrapper.class).getConfig().server;
        ServerNetwork.register();
        ModActions.register();
        ModSound.register();
        ModEntities.register();
        ModItems.register();
    }
}

package com.example.examplemod;

import com.example.examplemod.config.ServerConfig;
import com.example.examplemod.init.ModActions;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
    public static final String MODID = "examplemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        ModActions.register();
        AutoConfig.register(ServerConfig.class, GsonConfigSerializer::new);
    }
}

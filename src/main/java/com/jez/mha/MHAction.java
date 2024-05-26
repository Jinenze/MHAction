package com.jez.mha;

import com.jez.mha.config.ServerConfig;
import com.jez.mha.config.ServerConfigWrapper;
import com.jez.mha.init.ModActions;
import com.jez.mha.init.ModEntities;
import com.jez.mha.init.ModItems;
import com.jez.mha.init.ModSound;
import com.jez.mha.item.MhaSword;
import com.jez.mha.network.ServerNetwork;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MHAction implements ModInitializer {
    public static final String MODID = "mh_action";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static ServerConfig config;

    @Override
    public void onInitialize() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            ItemStack itemStack = player.getMainHandStack();
            if (itemStack.getItem() instanceof MhaSword item) {
                if (item.isEquipped(player)) {
                    item.equip(player);
                }
            }
        });
        AutoConfig.register(ServerConfigWrapper.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(ServerConfigWrapper.class).getConfig().server;
        ServerNetwork.register();
        ModActions.register();
        ModSound.register();
        ModEntities.register();
        ModItems.register();
    }
}

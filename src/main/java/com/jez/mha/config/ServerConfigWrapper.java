package com.jez.mha.config;

import com.jez.mha.MHAction;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = MHAction.MODID)
public class ServerConfigWrapper extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("server")
    public ServerConfig server = new ServerConfig();
}

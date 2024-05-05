package com.example.examplemod.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "server")
public class ServerConfig implements ConfigData {
    public boolean player_attack_collect = false;
}
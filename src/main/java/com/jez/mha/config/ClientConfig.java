package com.jez.mha.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "client")
public class ClientConfig implements ConfigData {
    public float third_x = 0;
    public float third_y = 0;
    public float third_z = 0;
    public float first_x = 0;
    public float first_y = 0;
    public float first_z = 0;
}

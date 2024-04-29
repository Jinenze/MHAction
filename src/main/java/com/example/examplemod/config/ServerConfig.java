package com.example.examplemod.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "server")
public class ServerConfig implements ConfigData {
    public ActionTimeConfig dodge = new ActionTimeConfig(20, 0, 5);

    public static class ActionTimeConfig {
        private ActionTimeConfig(int a, int b, int c) {
            stage1 = a;
            stage2 = b;
            stage3 = c;
        }

        public int stage1;
        public int stage2;
        public int stage3;
    }
}
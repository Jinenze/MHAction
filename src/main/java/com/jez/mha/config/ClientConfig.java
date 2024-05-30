package com.jez.mha.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "client")
public class ClientConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public TextureSetting itemRender = new TextureSetting();

    public static class TextureSetting {
        public float third_multiply_x = 0;
        public float third_multiply_y = 0;
        public float third_multiply_z = 0;
        public float third_translate_x = 0;
        public float third_translate_y = 0;
        public float third_translate_z = 0;
        public float third_scale_x = 1;
        public float third_scale_y = 1;
        public float third_scale_z = 1;
        public float first_multiply_x = 0;
        public float first_multiply_y = 0;
        public float first_multiply_z = 0;
        public float first_translate_x = 0;
        public float first_translate_y = 0;
        public float first_translate_z = 0;
        public float first_scale_x = 1;
        public float first_scale_y = 1;
        public float first_scale_z = 1;
    }
}

package com.jez.mha.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "client")
public class ClientConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public ModelSetting itemRender = new ModelSetting();

    @ConfigEntry.Gui.CollapsibleObject
    public HudSetting spiritGauge = new HudSetting(250, 100, true, 250, true, 100, 1f);

    @ConfigEntry.Gui.CollapsibleObject
    public HudSetting sharpness = new HudSetting(250, 100, true, 250, true, 100, 1f);

    @ConfigEntry.Gui.CollapsibleObject
    public HudSetting itemHudBackground = new ItemHudBackgroundSetting(500, 300, true, 500, true, 300, 1f);

    @ConfigEntry.Gui.Tooltip
    public boolean is_hud_based_background = true;

    @ConfigEntry.Gui.CollapsibleObject
    public HudSetting itemHud = new HudSetting(0, 0, true, 500, true, 300, 1f);

    public static class ModelSetting {
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

    public static class HudSetting {
        public int width;
        public int height;
        public boolean is_x_reversal;
        public int x;
        public boolean is_y_reversal;
        public int y;
        public float scale;

        private HudSetting(int width, int height, boolean xr, int x, boolean yr, int y, float scale) {
            this.width = width;
            this.height = height;
            is_x_reversal = xr;
            this.x = x;
            is_y_reversal = yr;
            this.y = y;
            this.scale = scale;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    public class ItemHudBackgroundSetting extends HudSetting {
        private ItemHudBackgroundSetting(int width, int height, boolean xr, int x, boolean yr, int y, float scale) {
            super(width, height, xr, x, yr, y, scale);
        }

        @Override
        public void setX(int x) {
            if (is_hud_based_background) {
                itemHud.setX(x + 50);
            } else {
                this.x = x;
            }
        }

        @Override
        public void setY(int y) {
            if (is_hud_based_background) {
                itemHud.setY(y - 50);
            } else {
                this.y = y;
            }
        }
    }
}

package com.jez.mha.util;

import com.jez.mha.config.ClientConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Pair;

public class RenderUtil {
    public static Pair<Integer, Integer> applyHudSetting(DrawContext drawContext, ClientConfig.HudSetting setting) {
        //似乎getScaledWindowWidth()会返回分辨率的1/4
        int x = setting.is_x_reversal ? (int) (drawContext.getScaledWindowWidth() * 4 / setting.scale - setting.x) : setting.x;
        int y = setting.is_y_reversal ? (int) (drawContext.getScaledWindowHeight() * 4 / setting.scale - setting.y) : setting.y;

        float i = 0.25f * setting.scale;
        //记得push
        drawContext.getMatrices().scale(i, i, i);

        return new Pair<>(x, y);
    }
}

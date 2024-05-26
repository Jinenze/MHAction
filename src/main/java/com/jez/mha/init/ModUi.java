package com.jez.mha.init;

import com.jez.mha.client.ui.hud.ItemRotaryMenuHud;

public class ModUi {
    public static ItemRotaryMenuHud itemRotaryMenuHud;

    public static void init() {
        itemRotaryMenuHud = new ItemRotaryMenuHud();
    }
}

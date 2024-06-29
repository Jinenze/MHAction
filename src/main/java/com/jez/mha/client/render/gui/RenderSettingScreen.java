package com.jez.mha.client.render.gui;

import com.jez.mha.client.ModClient;
import com.jez.mha.config.ClientConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class RenderSettingScreen extends Screen {
    public RenderSettingScreen() {
        super(Text.translatable("screen.mh_action.render_setting.title"));
        lastConfig = ModClient.config;
    }

    private final ClientConfig lastConfig;

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        ModClient.config = lastConfig;
        return true;
    }
}

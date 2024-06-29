package com.jez.mha.init;

import com.jez.mha.client.ModClient;
import com.jez.mha.state.MHAPlayerGetter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

@Environment(EnvType.CLIENT)
public class ModUi {
    public static void register() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (ModClient.processor.isEquipped()) {
                renderAllHud(drawContext, tickDelta, MinecraftClient.getInstance().player);
            }
        });
    }

    public static void renderAllHud(DrawContext drawContext, float tickDelta, ClientPlayerEntity player) {
        MHAPlayerGetter playerGetter = (MHAPlayerGetter) player;
        playerGetter.getSpiritGauge().getHudRender().onHudRender(drawContext, tickDelta);
//        SharpnessState.SharpnessHud.onHudRender(drawContext, tickDelta);
        playerGetter.getMHAPlayerItemList().getHudRender().onHudRender(drawContext, tickDelta);
    }
}

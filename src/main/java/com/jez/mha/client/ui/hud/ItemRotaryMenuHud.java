package com.jez.mha.client.ui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class ItemRotaryMenuHud {
    private final Identifier ICON = new Identifier("mh_action", "hud/icon.png");
    private final ItemRotaryMenu menu = new ItemRotaryMenu();

    public void register() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            Matrix4f positionMatrix = drawContext.getMatrices().peek().getPositionMatrix();
            int scaledWidth = drawContext.getScaledWindowWidth();
            int scaledHeight = drawContext.getScaledWindowHeight();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
            buffer.vertex(positionMatrix, scaledWidth - 60, scaledHeight - 60, 0).texture(0f, 0f).next();
            buffer.vertex(positionMatrix, scaledWidth - 60, scaledHeight - 20, 0).texture(0f, 1f).next();
            buffer.vertex(positionMatrix, scaledWidth - 20, scaledHeight - 20, 0).texture(1f, 1f).next();
            buffer.vertex(positionMatrix, scaledWidth - 20, scaledHeight - 60, 0).texture(1f, 0f).next();
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderTexture(0, new Identifier("mh_action", "icon.png"));
//            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            tessellator.draw();
        });
    }
}

package com.jez.mha.init;

public class ModUi {
//    private static final Identifier ROTARY_MENU_BACKGROUND = new Identifier("mh_action", "hud/icon.png");
//    private static final Identifier ITEM_DAMAGE_BAR_BACKGROUND = new Identifier("mh_action", "hud/icon.png");

    public static void init() {
//        itemRotaryMenuRender();
//        itemDamageBarRender();
    }

//    private static void itemRotaryMenuRender() {
//        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
//            MatrixStack stack = drawContext.getMatrices();
//            Matrix4f positionMatrix = stack.peek().getPositionMatrix();
//            int scaledWidth = drawContext.getScaledWindowWidth();
//            int scaledHeight = drawContext.getScaledWindowHeight();
//            Tessellator tessellator = Tessellator.getInstance();
//            BufferBuilder buffer = tessellator.getBuffer();
//            buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
//            buffer.vertex(positionMatrix, scaledWidth - 60, scaledHeight - 60, 0).texture(0f, 0f).next();
//            buffer.vertex(positionMatrix, scaledWidth - 60, scaledHeight - 20, 0).texture(0f, 1f).next();
//            buffer.vertex(positionMatrix, scaledWidth - 20, scaledHeight - 20, 0).texture(1f, 1f).next();
//            buffer.vertex(positionMatrix, scaledWidth - 20, scaledHeight - 60, 0).texture(1f, 0f).next();
//            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
//            RenderSystem.setShaderTexture(0, ModUi.ROTARY_MENU_BACKGROUND);
////            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
//            tessellator.draw();
//        });
//    }
//
//    private static void itemDamageBarRender() {
//        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
//            MatrixStack stack = drawContext.getMatrices();
//            Matrix4f positionMatrix = stack.peek().getPositionMatrix();
//            int scaledWidth = drawContext.getScaledWindowWidth();
//            int scaledHeight = drawContext.getScaledWindowHeight();
//            Tessellator tessellator = Tessellator.getInstance();
//            BufferBuilder buffer = tessellator.getBuffer();
//            buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
//            buffer.vertex(positionMatrix, scaledWidth - 60, scaledHeight - 60, 0).texture(0f, 0f).next();
//            buffer.vertex(positionMatrix, scaledWidth - 60, scaledHeight - 20, 0).texture(0f, 1f).next();
//            buffer.vertex(positionMatrix, scaledWidth - 20, scaledHeight - 20, 0).texture(1f, 1f).next();
//            buffer.vertex(positionMatrix, scaledWidth - 20, scaledHeight - 60, 0).texture(1f, 0f).next();
//            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
//            RenderSystem.setShaderTexture(0, ModUi.ITEM_DAMAGE_BAR_BACKGROUND);
////            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
//            tessellator.draw();
//        });
//    }
}

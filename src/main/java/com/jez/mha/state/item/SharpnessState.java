package com.jez.mha.state.item;

import com.jez.mha.MHAction;
import com.jez.mha.client.ModClient;
import com.jez.mha.config.ClientConfig;
import com.jez.mha.util.RenderUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public class SharpnessState {
    private static final int MAX = 1000;
    private static final String ID = "mha_sharpness";

    public static ItemStack initStack(ItemStack itemStack) {
        itemStack.getOrCreateNbt().putInt(ID, MAX);
        return itemStack;
    }

    @Environment(EnvType.CLIENT)
    public static class SharpnessHud {
        private static final Identifier BACKGROUND = new Identifier(MHAction.MODID, "hud/sharpness_background");
        private static final Identifier PROGRESS = new Identifier(MHAction.MODID, "hud/sharpness_progress");

        private static final ClientConfig.HudSetting setting = ModClient.config.sharpness;

        public static void onHudRender(DrawContext drawContext, float tickDelta) {
            if (ModClient.processor.isEquipped()) {
                drawContext.getMatrices().push();
                Pair<Integer, Integer> xy = RenderUtil.applyHudSetting(drawContext, setting);

                drawContext.drawGuiTexture(BACKGROUND, xy.getLeft(), xy.getRight(), setting.width, setting.height);
                NbtCompound nbt = MinecraftClient.getInstance().player.getMainHandStack().getNbt();
                if (nbt != null && nbt.get(ID) != null) {
                    int count = nbt.getInt(ID);
                    if (count > 0) {
                        int newWidth = count / MAX * setting.width;
                        drawContext.drawGuiTexture(PROGRESS, setting.width, setting.height, 0, 0, xy.getLeft(), xy.getRight(), newWidth, setting.height);
                    }
                }

                drawContext.getMatrices().pop();
            }
        }
    }
}

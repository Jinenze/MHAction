package com.jez.mha.state;

import com.jez.mha.MHAction;
import com.jez.mha.client.ModClient;
import com.jez.mha.config.ClientConfig;
import com.jez.mha.util.RenderUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public class SpiritGauge {
    private static final int MAX = 1000;
    private int count = 500;
    private short stage = 1;

    @Environment(EnvType.CLIENT)
    private final SpiritGaugeHud hudRender = new SpiritGaugeHud();

    public SpiritGauge() {
    }

    public void tick() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount(int count) {
        this.count += count;
        if (this.count > MAX) {
            this.count = MAX;
        }
    }

    public void minusCount(int count) {
        this.count -= count;
        if (this.count < 0) {
            this.count = 0;
        }
    }

    public void stageUpgrade() {
        if (stage == 3) {
            return;
        }
        ++stage;
    }

    public void stageDown() {
        if (stage == 1) {
            return;
        }
        --stage;
    }

    public int getStage() {
        return stage;
    }

    @Environment(EnvType.CLIENT)
    public SpiritGaugeHud getHudRender() {
        return hudRender;
    }

    @Environment(EnvType.CLIENT)
    public class SpiritGaugeHud {
        private static final Identifier BACKGROUND = new Identifier(MHAction.MODID, "hud/spirit_gauge_background");
        private static final Identifier WHITE = new Identifier(MHAction.MODID, "hud/spirit_gauge_white");
        private static final Identifier YELLOW = new Identifier(MHAction.MODID, "hud/spirit_gauge_yellow");
        private static final Identifier RED = new Identifier(MHAction.MODID, "hud/spirit_gauge_red");

        private final ClientConfig.HudSetting setting = ModClient.config.spiritGauge;

        public void onHudRender(DrawContext drawContext, float tickDelta) {
            drawContext.getMatrices().push();
            Pair<Integer, Integer> xy = RenderUtil.applyHudSetting(drawContext, setting);

            drawContext.drawGuiTexture(BACKGROUND, xy.getLeft(), xy.getRight(), setting.width, setting.height);

            int newWidth = (int) Math.ceil((double) count / MAX * setting.width);
            if (newWidth > 0) {
                switch (stage) {
                    case 1:
                        drawContext.drawGuiTexture(WHITE, setting.width, setting.height, 0, 0, xy.getLeft(), xy.getRight(), newWidth, setting.height);
                        break;
                    case 2:
                        drawContext.drawGuiTexture(YELLOW, setting.width, setting.height, 0, 0, xy.getLeft(), xy.getRight(), newWidth, setting.height);
                        break;
                    case 3:
                        drawContext.drawGuiTexture(RED, setting.width, setting.height, 0, 0, xy.getLeft(), xy.getRight(), newWidth, setting.height);
                        break;
                }
            }
            drawContext.getMatrices().pop();
        }
    }
}

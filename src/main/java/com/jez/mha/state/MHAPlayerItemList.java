package com.jez.mha.state;

import com.jez.mha.MHAction;
import com.jez.mha.client.ModClient;
import com.jez.mha.config.ClientConfig;
import com.jez.mha.util.RenderUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class MHAPlayerItemList {
    private final ClientPlayerEntity player;

    private final List<ItemStack> itemList = new ArrayList<>();
    //todo 替换
    private static final List<Item> availableItems = new ArrayList<>();

    private final ItemListHud hudRender = new ItemListHud();
    private short index = 0;

//    private static final int MAX_TICK = 100;
//    private int tickCount;

    //救命
    public void tick() {
//        ++tickCount;
//        if (tickCount >= MAX_TICK) {
//            tickCount = 0;
//        }
        updateItemList();
    }

    public void updateItemList() {
        itemList.clear();
        for (int i = 0; i < player.getInventory().size(); ++i) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (isInAvailable(itemStack)) {
                itemList.add(itemStack);
            }
        }
    }

    private boolean isInAvailable(ItemStack itemStack) {
        return availableItems.contains(itemStack.getItem());
    }

    public static void addAvailableItem(Item item) {
        availableItems.add(item);
    }

    public void addIndex() {
        if (index + 2 > itemList.size()) {
            index = 0;
            return;
        }
        ++index;
    }

    public void minusIndex() {
        if (index == 0) {
            index = (short) (itemList.size() - 1);
            return;
        }
        --index;
    }

    public ItemStack getCurrentItem() {
        return itemList.get(index);
    }

    public ItemListHud getHudRender() {
        return hudRender;
    }

    public MHAPlayerItemList(ClientPlayerEntity player) {
        this.player = player;
    }

    @Environment(EnvType.CLIENT)
    public class ItemListHud {
        private static final Identifier BACKGROUND = new Identifier(MHAction.MODID, "hud/item_list_background");

        private final ClientConfig.HudSetting setting = ModClient.config.itemHudBackground;
        private final ClientConfig.HudSetting itemSetting = ModClient.config.itemHud;
        private short renderingIndex = 1;
        private boolean inAnimation = false;
        private float time;
        private EmptyState emptyState;
        private ItemStack stack1;
        private ItemStack stack2;
        private ItemStack stack3;

        public void onHudRender(DrawContext drawContext, float tickDelta) {
            drawContext.getMatrices().push();
            Pair<Integer, Integer> xy = RenderUtil.applyHudSetting(drawContext, setting);
            drawContext.drawGuiTexture(BACKGROUND, xy.getLeft(), xy.getRight(), setting.width, setting.height);
            drawContext.getMatrices().pop();

            if (itemList.isEmpty()) {
                return;
            }

            if (renderingIndex == 0) {
                emptyState = EmptyState.LEFT;
            } else if (itemList.size() == 1) {
                emptyState = EmptyState.BOTH;
            } else if (renderingIndex + 1 == itemList.size()) {
                emptyState = EmptyState.RIGHT;
            } else {
                emptyState = EmptyState.NULL;
            }

            drawContext.getMatrices().push();
            int x = itemSetting.is_x_reversal ? (int) (drawContext.getScaledWindowWidth() / itemSetting.scale - itemSetting.x) : itemSetting.x;
            int y = itemSetting.is_y_reversal ? (int) (drawContext.getScaledWindowHeight() / itemSetting.scale - itemSetting.y) : itemSetting.y;

            drawContext.getMatrices().scale(itemSetting.scale, itemSetting.scale, itemSetting.scale);

            int leftX = x;
            int midX = leftX + 16;
            int rightX = midX + 16;
            int newY = y;

            switch (emptyState) {
                case LEFT:
                    drawContext.drawItem(itemList.get(renderingIndex), midX, newY);
                    drawContext.drawItem(itemList.get(renderingIndex + 1), rightX, newY);
                    break;
                case BOTH:
                    drawContext.drawItem(itemList.get(renderingIndex), midX, newY);
                    break;
                case RIGHT:
                    drawContext.drawItem(itemList.get(renderingIndex - 1), leftX, newY);
                    drawContext.drawItem(itemList.get(renderingIndex), midX, newY);
                    break;
                case NULL:
                    drawContext.drawItem(itemList.get(renderingIndex - 1), leftX, newY);
                    drawContext.drawItem(itemList.get(renderingIndex), midX, newY);
                    drawContext.drawItem(itemList.get(renderingIndex + 1), rightX, newY);
                    break;
            }
            drawContext.getMatrices().pop();
        }

        private enum EmptyState {
            LEFT,
            NULL,
            RIGHT,
            BOTH
        }

//            if (inAnimation) {
//                time -= tickDelta;
//                if (time <= 0f) {
//                    return;
//                }
//            } else {
//                if (renderingIndex == index) {
//                    renderItem(drawContext, tickDelta, x, y);
//                    return;
//                } else if (renderingIndex < index) {
//                    drawContext.drawItem(itemList.get(renderingIndex), x / 2 - 8, y / 2 + 8);
//
//                } else {
//                    drawContext.drawItem(itemList.get(renderingIndex), x / 2 - 8, y / 2 + 8);
//
//                }
//                time = 5f;
//                inAnimation = true;
//            }
    }
}

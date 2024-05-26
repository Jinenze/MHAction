package com.jez.mha.client.input;

import com.jez.mha.client.action.impl.ClientProcessor;
import com.jez.mha.init.ModKeyBinds;
import com.jez.mha.item.MhaSword;
import com.jez.mha.network.ClientNetwork;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class KeyBind {
    private static final ArrayList<KeyBinding> keyList = new ArrayList<>();
    @Nullable
    private KeyBinding lastKey;
    @Nullable
    private KeyBinding key;
    private int tickCount;
    private final ClientProcessor processor;
    private final ClientPlayerEntity player;

    public static KeyBinding register(KeyBinding key) {
        keyList.add(key);
        return key;
    }

    public void keyBindTick() {
        if (processor.isEquipped()) {
            if (processor.isReady()) {
                for (KeyBinding k : keyList) {
                    if (k.isPressed()) {
                        if (k != lastKey && tickCount <= 0) {
                            tickCount = 4;
                        }
                        if (lastKey == null) {
                            lastKey = k;
                        } else if (k != lastKey) {
                            key = k;
                        }
                    }
                }
                if (tickCount > 0) {
                    --tickCount;
                } else {
                    processor.searchAction(lastKey, key);
                    lastKey = null;
                    key = null;
                    return;
                }
                if (player.getMainHandStack().getItem() instanceof MhaSword item) {
                    item.drawSwordTick(player);
                }
            } else {
                if (player.getMainHandStack().getItem() instanceof MhaSword item) {
                    item.drawSwordTick(player);
                }
                equipTick(player);
            }
        } else {
            if (player != null) {
                equipTick(player);
            }
        }
    }

    private static void equipTick(ClientPlayerEntity player) {
        if (ModKeyBinds.EQUIP.wasPressed()) {
            if (player.getMainHandStack().getItem() instanceof MhaSword item) {
                item.equip(player);
                ClientNetwork.sendC2SEquipRequest();
            }
        }
    }

    public void setTickCount(int c) {
        tickCount = c;
    }

    public KeyBind(ClientProcessor processor) {
        this.processor = processor;
        player = processor.getPlayer();
    }
}
package com.jez.mha.client.input;

import com.jez.mha.client.ModClient;
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
    private static KeyBinding lastKey;
    @Nullable
    private static KeyBinding key;
    private static int tickCount;

    public static KeyBinding register(KeyBinding key) {
        keyList.add(key);
        return key;
    }

    public static void keyBindTick() {
        if (ModClient.processor.isEquipped()) {
            if (ModClient.processor.isReady()) {
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
                    ModClient.processor.searchAction(lastKey, key);
                    lastKey = null;
                    key = null;
                    return;
                }
                ClientPlayerEntity player = ModClient.processor.getPlayer();
                if (player.getMainHandStack().getItem() instanceof MhaSword item) {
                    item.drawSwordTick(player);
                }
            } else {
                ClientPlayerEntity player = ModClient.processor.getPlayer();
                if (player.getMainHandStack().getItem() instanceof MhaSword item) {
                    item.drawSwordTick(player);
                }
                equipTick(player);
            }
        } else {
            ClientPlayerEntity player = ModClient.processor.getPlayer();
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

    public static void setTickCount(int c) {
        tickCount = c;
    }
}
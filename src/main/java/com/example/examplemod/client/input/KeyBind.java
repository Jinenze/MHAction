package com.example.examplemod.client.input;

import com.example.examplemod.client.action.Action;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class KeyBind {
    private static final ArrayList<KeyBinding> keyList = new ArrayList<>();
    private static int tickCountKey;
    @Nullable
    private static KeyBinding lastKey;
    private static KeyBinding key;

    public static KeyBinding register(KeyBinding key) {
        keyList.add(key);
        return key;
    }

    public static void keyBindTick() {
        if (tickCountKey == 0) {
            lastKey = null;
            key = null;
        } else {
            --tickCountKey;
        }
        for (KeyBinding k : keyList) {
            if (k.isPressed()) {
                if (tickCountKey != 3) {
                    if (tickCountKey == 0 && lastKey == k) {
                        k = null;
                    }
                    tickCountKey = 3;
                    lastKey = k;
                }
                key = k;
            }
        }
        Action.doAction(lastKey, key);
    }
}

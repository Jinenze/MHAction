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
    @Nullable
    private static KeyBinding key;

    public static KeyBinding register(KeyBinding key) {
        keyList.add(key);
        return key;
    }

    public static void keyBindTick() {
        key = null;
        if (tickCountKey == 0) {
            lastKey = null;
        } else {
            --tickCountKey;
        }
        for (KeyBinding k : keyList) {
            if (k.isPressed()) {
                if (lastKey == null) {
                    lastKey = key;
                    tickCountKey = 5;
                }
                key = k;
            }
        }
        Action.doAction(lastKey, key);
    }

    public static int getTickCountKey() {
        return tickCountKey;
    }
}

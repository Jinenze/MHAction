package com.example.examplemod.client.input;

import com.example.examplemod.client.action.ClientActionRunner;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
            ClientActionRunner.searchAction(lastKey, key);
            lastKey = null;
            key = null;
        }
    }

    public static void setTickCount(int c) {
        tickCount = c;
    }
//    public static void keyBindTick() {
//        key = null;
//        lastKey = null;
////        if (tickCount == 0) {
////            lastKey = null;
////        } else {
////            --tickCount;
////        }
//        for (KeyBinding k : keyList) {
//            if (k.isPressed()) {
//                if (lastKey == null) {
//                    lastKey = k;
////                    tickCount = 5;
//                }
////                if (lastKey == k && key != null) {
////                    continue;
////                }
//                key = k;
//            }
//        }
//        Action.doAction(lastKey, key);
//    }
}
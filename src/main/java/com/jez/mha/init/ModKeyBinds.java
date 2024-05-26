package com.jez.mha.init;

import com.jez.mha.client.input.KeyBind;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ModKeyBinds {
    public static KeyBinding ATTACK;
    public static KeyBinding SUB_ATTACK;
    public static KeyBinding UNLOAD = KeyBind.register(KeyBindingHelper.registerKeyBinding(new KeyBinding("key.mh_action.switch", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.mh_action.category")));
    public static final KeyBinding EQUIP = KeyBind.register(KeyBindingHelper.registerKeyBinding(new KeyBinding("key.mh_action.equip", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, "category.mh_action.category")));
    public static final KeyBinding SLASH_KEY = KeyBind.register(KeyBindingHelper.registerKeyBinding(new KeyBinding("key.mh_action.slash", InputUtil.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_5, "category.mh_action.category")));
    public static final KeyBinding DODGE_KEY = KeyBind.register(KeyBindingHelper.registerKeyBinding(new KeyBinding("key.mh_action.dodge", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_SPACE, "category.mh_action.category")));

    public static void register() {
    }

    public static void init() {
        ATTACK = KeyBind.register(MinecraftClient.getInstance().options.attackKey);
        SUB_ATTACK = KeyBind.register(MinecraftClient.getInstance().options.useKey);
    }
}

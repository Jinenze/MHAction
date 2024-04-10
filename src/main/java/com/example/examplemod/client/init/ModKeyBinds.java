package com.example.examplemod.client.init;

import com.example.examplemod.client.action.Action;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.SwordItem;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ModKeyBinds {
    public static final KeyBinding SWITCH_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.examplemod.switch", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.examplemod.examplemod"));
    public static final KeyBinding LEFT_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.examplemod.left", InputUtil.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_1, "category.examplemod.examplemod"));
    public static final KeyBinding RIGHT_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.examplemod.right", InputUtil.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_2, "category.examplemod.examplemod"));
    public static final KeyBinding SLASH_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.examplemod.slash", InputUtil.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_5, "category.examplemod.examplemod"));
    public static final KeyBinding DODGE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.examplemod.dodge", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_SPACE, "category.examplemod.examplemod"));

    public static void register(){

    }
}

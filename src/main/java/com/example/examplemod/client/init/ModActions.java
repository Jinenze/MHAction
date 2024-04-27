package com.example.examplemod.client.init;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.action.AbstractAction;
import com.example.examplemod.action.ActionRunner;
import com.example.examplemod.action.impl.Dodge;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModActions {
    public static final AbstractAction DODGE = new Dodge(20, 0, 5, new Identifier(ExampleMod.MODID, "nmlgb"));

    public static void register() {
        ActionRunner.register(DODGE, ModKeyBinds.DODGE_KEY);
    }
}

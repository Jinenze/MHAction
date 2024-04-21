package com.example.examplemod.client.init;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.client.action.AbstractAction;
import com.example.examplemod.client.action.ActionRunner;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModActions {
    public static final AbstractAction DODGE = new AbstractAction(15, 5, 0, new Identifier(ExampleMod.MODID, "temple_animation")) {
        @Override
        public boolean isAvailable(AbstractAction action) {
            return true;
        }

        @Override
        public void run() {
            if (MinecraftClient.getInstance().player == null) {
                return;
            }
            MinecraftClient.getInstance().player.takeKnockback(1, 1, 0);
            ExampleMod.LOGGER.info(String.valueOf(DODGE.getActionAnim().hashCode()));
        }
    };

    public static void register() {
        ActionRunner.register(DODGE, ModKeyBinds.DODGE_KEY);
    }
}

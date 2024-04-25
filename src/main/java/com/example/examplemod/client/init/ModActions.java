package com.example.examplemod.client.init;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.client.action.AbstractAction;
import com.example.examplemod.client.action.ActionRunner;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class ModActions {
    public static final AbstractAction DODGE = new AbstractAction(40, 5, 0, new Identifier(ExampleMod.MODID, "temple_animation")) {
        @Override
        public void run() {
            if (MinecraftClient.getInstance().player == null) {
                return;
            }
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            Vec2f vec2f = player.input.getMovementInput();
            player.travel(new Vec3d(vec2f.x, 0, vec2f.y));
        }
    };

    public static void register() {
        ActionRunner.register(DODGE, ModKeyBinds.DODGE_KEY, DODGE);
    }
}

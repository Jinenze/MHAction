package com.example.examplemod.client.init;

import com.example.examplemod.ExampleMod;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;

public class ModAnimations {
    public static PlayerAnimationAccess.PlayerAssociatedAnimationData playerAssociatedAnimationData;

    public static void register(AbstractClientPlayerEntity player, AnimationStack animationStack) {
        ModifierLayer<IAnimation> layer = new ModifierLayer<>();
        animationStack.addAnimLayer(10, layer);
        playerAssociatedAnimationData = PlayerAnimationAccess.getPlayerAssociatedData(player);
        playerAssociatedAnimationData.set(new Identifier(ExampleMod.MODID, "main_anim"), layer);
    }
}

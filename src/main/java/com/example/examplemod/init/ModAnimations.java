package com.example.examplemod.init;

import com.example.examplemod.ExampleMod;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModAnimations {
    public static PlayerAnimationAccess.PlayerAssociatedAnimationData playerAssociatedAnimationData;
    public static final Identifier mainAnim = new Identifier(ExampleMod.MODID, "main_anim");
    public static final Identifier DODGE = new Identifier(ExampleMod.MODID, "nmlgb");

    public static void register(AbstractClientPlayerEntity player, AnimationStack animationStack) {
        ModifierLayer<IAnimation> layer = new ModifierLayer<>();
        animationStack.addAnimLayer(10, layer);
        playerAssociatedAnimationData = PlayerAnimationAccess.getPlayerAssociatedData(player);
        playerAssociatedAnimationData.set(mainAnim, layer);
    }
}

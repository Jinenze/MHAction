package com.jez.mha.init;

import com.jez.mha.MHAction;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModAnimations {
    public static final ModifierLayer<IAnimation> mainAnim = new ModifierLayer<>();
    public static final ModifierLayer<IAnimation> subAnim = new ModifierLayer<>();
    public static final Identifier katanaSub = new Identifier(MHAction.MODID, "katana_sub");
    public static final Identifier EMPTY = new Identifier(MHAction.MODID, "mha_empty");

    public static void register(AbstractClientPlayerEntity player, AnimationStack animationStack) {
        animationStack.addAnimLayer(1, mainAnim);
        animationStack.addAnimLayer(2, subAnim);
    }
}

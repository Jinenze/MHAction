package com.jez.mha.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(KeyBinding.class)
public interface KeyBindingInvoker {
    @Invoker("reset")
    void mha$reset();
}

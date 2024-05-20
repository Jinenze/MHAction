package com.example.examplemod.mixin;

import com.example.examplemod.client.action.ClientActionRunner;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Final
    @Shadow
    public GameOptions options;
    @Shadow
    public ClientPlayerEntity player;

    @Inject(method = "handleInputEvents", at = @At("HEAD"))
    private void handleInputEventsInject(CallbackInfo ci) {
        if (ClientActionRunner.isRunning()) {
            ((KeyBindingInvoker) this.options.dropKey).mha$reset();
            ((KeyBindingInvoker) this.options.inventoryKey).mha$reset();
            ((KeyBindingInvoker) this.options.swapHandsKey).mha$reset();
            for (int i = 0; i < 9; ++i) {
                ((KeyBindingInvoker) this.options.hotbarKeys[i]).mha$reset();
            }
        }
    }
}

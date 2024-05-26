package com.jez.mha.mixin;

import com.jez.mha.client.ModClient;
import com.jez.mha.init.ModActions;
import com.jez.mha.init.ModKeyBinds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Final
    @Shadow
    public GameOptions options;
    @Shadow
    public ClientPlayerEntity player;

    @Inject(method = "handleInputEvents", at = @At("HEAD"))
    private void handleInputEventsInject(CallbackInfo ci) {
        if (ModClient.processor.isEquipped()) {
            ((KeyBindingInvoker) this.options.dropKey).mha$reset();
            ((KeyBindingInvoker) this.options.inventoryKey).mha$reset();
            ((KeyBindingInvoker) this.options.swapHandsKey).mha$reset();
            for (int i = 0; i < 9; ++i) {
                ((KeyBindingInvoker) this.options.hotbarKeys[i]).mha$reset();
            }
        }
    }

    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void doAttackInject(CallbackInfoReturnable<Boolean> cir) {
        if (ModClient.processor.isEquipped()) cir.setReturnValue(false);
    }

    @Inject(method = "handleBlockBreaking", at = @At("HEAD"), cancellable = true)
    private void pre_handleBlockBreaking(boolean bl, CallbackInfo ci) {
        if (ModClient.processor.isEquipped()) ci.cancel();
    }

    @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
    private void pre_doItemUse(CallbackInfo ci) {
        if (ModClient.processor.isEquipped()) ci.cancel();
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderGlintAlpha(D)V"))
    private void mha$actionsInit(CallbackInfo ci) {
        ModKeyBinds.init();
        ModActions.clientInit();
    }
}

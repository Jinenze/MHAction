package com.example.examplemod.mixin;

import com.example.examplemod.client.action.ClientActionRunner;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    @Inject(method = "scrollInHotbar", at = @At("HEAD"), cancellable = true)
    private void scrollInHotbarInject(double scrollAmount, CallbackInfo ci){
        if (ClientActionRunner.isRunning()) ci.cancel();
    }
}

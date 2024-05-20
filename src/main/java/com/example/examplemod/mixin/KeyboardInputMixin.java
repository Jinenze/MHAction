package com.example.examplemod.mixin;

import com.example.examplemod.client.action.ClientActionRunner;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {
    @Inject(method = "tick", at = @At("RETURN"))
    private void tickInject(boolean slowDown, float slowDownFactor, CallbackInfo ci) {
        if (ClientActionRunner.isRunning()) {
//            this.pressingForward = false;
//            this.pressingBack = false;
//            this.pressingLeft = false;
//            this.pressingRight = false;
            this.movementForward = 0.0f;
            this.movementSideways = 0.0f;
            this.jumping = false;
            this.sneaking = false;
        }
    }
}

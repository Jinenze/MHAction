package com.example.examplemod.mixin;

import com.example.examplemod.action.longsword.PlayerGauge;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerMixin implements PlayerGauge {
    @Unique
    private int spiritGauge;

    @Unique
    private int gaugeTick;

    @Override
    public int getSpiritGauge() {
        return spiritGauge;
    }

    @Override
    public void setSpiritGauge(int spiritGauge) {
        gaugeTick = 600;
        this.spiritGauge = spiritGauge;
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo ci) {
        if (spiritGauge > 0) {
            if (gaugeTick > 0) {
                --gaugeTick;
            } else {
                --spiritGauge;
            }
        }
    }
}

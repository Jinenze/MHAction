package com.jez.mha.mixin;

import com.jez.mha.state.MHAPlayerGetter;
import com.jez.mha.state.MHAPlayerItemList;
import com.jez.mha.state.SpiritGauge;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerMixin implements MHAPlayerGetter {
    @Unique
    private final SpiritGauge spiritGauge = new SpiritGauge();

    @Unique
    private final MHAPlayerItemList mhaPlayerItemList = new MHAPlayerItemList((ClientPlayerEntity) (Object) this);

    @Override
    public SpiritGauge getSpiritGauge() {
        return spiritGauge;
    }

    @Override
    public MHAPlayerItemList getMHAPlayerItemList() {
        return mhaPlayerItemList;
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo ci) {
        spiritGauge.tick();
        mhaPlayerItemList.tick();
    }
}

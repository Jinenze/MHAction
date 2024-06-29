package com.jez.mha.client.render.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.minecraft.client.render.model.BakedModel;

@Environment(EnvType.CLIENT)
public class ModBakedModel extends ForwardingBakedModel {
    public ModBakedModel(BakedModel model) {
        this.wrapped = model;
    }

    @Override
    public boolean isBuiltin() {
        return true;
    }
}

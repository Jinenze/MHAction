package com.jez.mha.client.render.impl;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.minecraft.client.render.model.BakedModel;

public class ModBakedModel extends ForwardingBakedModel {
    public ModBakedModel(BakedModel model) {
        this.wrapped = model;
    }

    @Override
    public boolean isBuiltin() {
        return true;
    }
}

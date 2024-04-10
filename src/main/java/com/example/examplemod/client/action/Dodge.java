package com.example.examplemod.client.action;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class Dodge extends GeoModel {

    @Override
    public Identifier getModelResource(GeoAnimatable animatable) {
        return null;
    }

    @Override
    public Identifier getTextureResource(GeoAnimatable animatable) {
        return null;
    }

    @Override
    public Identifier getAnimationResource(GeoAnimatable animatable) {
        return null;
    }
}

package com.jez.mha.entity.renderer;

import com.jez.mha.entity.TempleEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class TempleEntityRenderer extends EntityRenderer<TempleEntity> implements EntityRendererFactory<TempleEntity>{

    public TempleEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(TempleEntity entity) {
        return null;
    }

    @Override
    public EntityRenderer<TempleEntity> create(Context ctx) {
        return null;
    }
}

package com.jez.mha.client.render;

import com.jez.mha.client.render.impl.ModBakedModel;
import com.jez.mha.init.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class ModelSwapper implements ModelModifier.AfterBake {
    private ArrayList<ModelIdentifier> identifiers;

    @Override
    public @Nullable BakedModel modifyModelAfterBake(@Nullable BakedModel model, Context context) {
        if (identifiers == null) collectSwaps();
        for (ModelIdentifier id : identifiers) {
            if (context.id().equals(id)) {
                return new ModBakedModel(model);
            }
        }
        return model;
    }

    private void collectSwaps() {
        this.identifiers = new ArrayList<>();
        ModItems.forEach(id -> identifiers.add(getItemModelLocation(id)));
    }

    private ModelIdentifier getItemModelLocation(Identifier identifier) {
        return new ModelIdentifier(identifier, "inventory");
    }

    public void registerListeners() {
        ModelLoadingPlugin.register(ctx -> ctx.modifyModelAfterBake().register(this));
    }
}

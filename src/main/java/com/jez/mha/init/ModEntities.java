package com.jez.mha.init;

import com.jez.mha.MHAction;
import com.jez.mha.entity.TempleEntity;
import com.jez.mha.entity.renderer.TempleEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<TempleEntity> TEMPLE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MHAction.MODID, "temple"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TempleEntity::new).dimensions(EntityDimensions.fixed(1.0f, 1.0f)).build());

    public static void register() {
        FabricDefaultAttributeRegistry.register(TEMPLE, TempleEntity.createLivingAttributes());
    }

    public static void client(){
        EntityRendererRegistry.register(ModEntities.TEMPLE, TempleEntityRenderer::new);
    }
}

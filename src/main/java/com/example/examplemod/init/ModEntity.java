package com.example.examplemod.init;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.entity.SlimeOverrideEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntity {
    public static final EntityType<SlimeOverrideEntity> TEMPLE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ExampleMod.MODID, "temple"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SlimeOverrideEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());
}

package com.example.examplemod.init;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.item.LongSword;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static Item katana = Registry.register(Registries.ITEM, new Identifier(ExampleMod.MODID, "katana"), new LongSword(new Item.Settings().fireproof().maxDamage(1000).rarity(Rarity.RARE)));

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
            content.add(katana);
        });
    }
}

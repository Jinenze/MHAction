package com.example.examplemod.init;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.client.render.LongSwordRender;
import com.example.examplemod.item.LongSword;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ModItems {
    public static final ArrayList<Identifier> ITEM_ID = new ArrayList<>();

    public static Item longSword = register(new Identifier(ExampleMod.MODID, "long_sword"), new LongSword(new Item.Settings().fireproof().rarity(Rarity.RARE)));
    public static Item katana = Registry.register(Registries.ITEM, new Identifier(ExampleMod.MODID, "katana"), new LongSword(new Item.Settings().fireproof().maxDamage(1000).rarity(Rarity.RARE)));
    public static Item katanaa = Registry.register(Registries.ITEM, new Identifier(ExampleMod.MODID, "katanaa"), new LongSword(new Item.Settings().fireproof().rarity(Rarity.RARE)));

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
            content.add(katana);
        });
        BuiltinItemRendererRegistry.INSTANCE.register(longSword, new LongSwordRender());
    }

    public static void forEach(Consumer<Identifier> consumer) {
        ITEM_ID.forEach(consumer);
    }

    private static Item register(Identifier identifier, Item item) {
        Registry.register(Registries.ITEM, identifier, item);
        ITEM_ID.add(identifier);
        return item;
    }
}

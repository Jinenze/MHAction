package com.jez.mha.init;

import com.jez.mha.MHAction;
import com.jez.mha.client.render.item.LongSwordRender;
import com.jez.mha.item.LongSword;
import com.jez.mha.state.MHAPlayerItemList;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ModItems {
    public static final ArrayList<Identifier> ITEM_ID = new ArrayList<>();

    public static Item longSword = register(new Identifier(MHAction.MODID, "long_sword"), new LongSword(new Item.Settings().fireproof().rarity(Rarity.RARE)));

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
            content.add(longSword);
        });
        BuiltinItemRendererRegistry.INSTANCE.register(longSword, new LongSwordRender());
        MHAPlayerItemList.addAvailableItem(Items.POTION);
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

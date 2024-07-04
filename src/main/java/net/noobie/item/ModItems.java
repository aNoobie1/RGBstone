package net.noobie.item;

import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.noobie.block.ModBlocks;

public class ModItems {
    public static Item BLUESTONE;

    public static void RegisterItems(){
        BLUESTONE = Registry.register(
                Registries.ITEM,
                Identifier.tryParse("rgbstone:bluestone"),
                new AliasedBlockItem(
                        ModBlocks.BLUESTONE_WIRE,
                        new Item.Settings()
                )
        );
    }
}

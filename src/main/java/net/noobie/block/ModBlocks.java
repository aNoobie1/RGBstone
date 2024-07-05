package net.noobie.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.noobie.block.custom.BluestoneWireBlock;

import java.util.List;

public class ModBlocks {
    public static Block BLUESTONE_WIRE;

    public static List<Block> BLUESTONE_BLOCKS;


    public static void RegisterBlocks(){
        BLUESTONE_WIRE = Registry.register(
                Registries.BLOCK,
                Identifier.tryParse(
                        "rgbstone:bluestone_wire"
                ),
                new BluestoneWireBlock(
                        AbstractBlock.Settings.create()
                                .noCollision()
                                .breakInstantly()
                                .pistonBehavior(PistonBehavior.DESTROY)
                )
        );

        BLUESTONE_BLOCKS = List.of(BLUESTONE_WIRE);
    }
}

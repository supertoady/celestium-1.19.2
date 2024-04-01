package supertoady.celestium.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.MagmaBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import supertoady.celestium.Celestium;
import supertoady.celestium.item.ModItemGroups;

public class ModBlocks {
    public static Block METEORITE_CHUNK = registerBlock("meteorite_chunk",
            new MeteoriteChunkBlock(FabricBlockSettings.copyOf(Blocks.ANCIENT_DEBRIS)));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registry.BLOCK, new Identifier(Celestium.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registry.ITEM, new Identifier(Celestium.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(ModItemGroups.CELESTIUM)));
    }

    public static void registerModBlocks(){
        Celestium.LOGGER.info("Registering mod blocks for" + Celestium.MOD_ID);
    }
}

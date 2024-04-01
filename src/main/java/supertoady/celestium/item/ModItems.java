package supertoady.celestium.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import supertoady.celestium.Celestium;
import supertoady.celestium.item.ModItemGroups;
import supertoady.celestium.item.custom.MeteorMagnetItem;
import supertoady.celestium.item.custom.SpaceGunItem;

public class ModItems {

    public static final Item ROUGH_METEORITE = registerItem("rough_meteorite",
            new Item(new FabricItemSettings().group(ModItemGroups.CELESTIUM).rarity(Rarity.UNCOMMON)));

    public static final Item REFINED_METEORITE = registerItem("refined_meteorite",
            new Item(new FabricItemSettings().group(ModItemGroups.CELESTIUM).rarity(Rarity.UNCOMMON)));

    public static final Item METEOR_MAGNET = registerItem("meteor_magnet",
            new Item(new FabricItemSettings().group(ModItemGroups.CELESTIUM).rarity(Rarity.UNCOMMON).maxCount(1)));

    public static final Item SPACE_GUN = registerItem("space_gun",
            new SpaceGunItem(ToolMaterials.DIAMOND, new FabricItemSettings().group(ModItemGroups.CELESTIUM).rarity(Rarity.UNCOMMON).maxCount(1)));

    public static final Item STARDUST = registerItem("stardust",
            new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(Celestium.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Celestium.LOGGER.info("Registering mod items for " + Celestium.MOD_ID);
    }
}

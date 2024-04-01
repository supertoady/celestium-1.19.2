package supertoady.celestium.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import supertoady.celestium.Celestium;

public class ModItemGroups {
    public static ItemGroup CELESTIUM = FabricItemGroupBuilder.build(
            new Identifier(Celestium.MOD_ID, "celestium"), () -> new ItemStack(ModItems.ROUGH_METEORITE));
}

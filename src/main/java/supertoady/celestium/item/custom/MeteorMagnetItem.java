package supertoady.celestium.item.custom;

import com.ibm.icu.text.MessagePattern;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import net.minecraft.command.argument.RegistryPredicateArgumentType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.StructureTags;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSplitter;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import supertoady.celestium.Celestium;

public class MeteorMagnetItem extends Item {
    public MeteorMagnetItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(world.isClient) {
            return TypedActionResult.pass(itemStack);
        }

        BlockPos blockPos = null;
        if (!world.isClient()){
            blockPos = ((ServerWorld)world).locateStructure(StructureTags.VILLAGE, user.getBlockPos(), 2000, true);
        }

        if (blockPos != null) {
            user.sendMessage(Text.literal("§6§l" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ()), true);

            user.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1);
            user.setStackInHand(hand, new ItemStack(Items.AIR));
        }
        else {
            user.sendMessage(Text.literal("§6§lN0 STRUCTUR3 F0UND"), true);
        }

        return TypedActionResult.consume(itemStack);
    }
}

package supertoady.celestium.mixin;

import net.minecraft.block.*;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supertoady.celestium.Celestium;
import supertoady.celestium.item.ModItems;

import java.util.Random;


@Mixin(Item.class)
public class PickaxeItemMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"))
    private void useOnBlockInjection(ItemUsageContext context, CallbackInfoReturnable info) {
        // Your custom logic for useOnBlock
        if (context.getPlayer() != null && context.getPlayer().getMainHandStack().isOf(Items.NETHERITE_PICKAXE)) {
            scrapeEndPortalFrame(context);
        }
    }

    private void scrapeEndPortalFrame(ItemUsageContext context) {

        BlockState targetedBlockStates = context.getWorld().getBlockState(context.getBlockPos());
        if (targetedBlockStates.get(EndPortalFrameBlock.EYE)){

            BlockPos pos = context.getBlockPos();
            Random rand = new Random();

            targetedBlockStates.cycle(EndPortalFrameBlock.EYE);
            context.getWorld().setBlockState(pos, targetedBlockStates);

            for (int i = 10; i > 0; i--){
                float x = pos.getX() + ((rand.nextFloat(10) - 5) / 5);
                float y = pos.getY() + ((rand.nextFloat(10) - 5) / 5);
                float z = pos.getZ() + ((rand.nextFloat(10) - 5) / 5);

                //Celestium.showParticlesToAll(context.getWorld(), new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.ENDER_EYE)), x, y + 0.6, z);
            }

            context.getWorld().spawnEntity(new ItemEntity(context.getWorld(), pos.getX(), pos.getY(), pos.getZ(),
                    new ItemStack(ModItems.STARDUST, rand.nextInt() + 2)));
        }
    }
}

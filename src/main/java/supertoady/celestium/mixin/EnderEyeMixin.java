package supertoady.celestium.mixin;

import net.minecraft.block.*;
import net.minecraft.client.sound.Sound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import supertoady.celestium.Celestium;

@Mixin(EnderEyeItem.class)
public class EnderEyeMixin {

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	private void override(ItemUsageContext context, CallbackInfoReturnable info) {

		Block targetedBlock = context.getWorld().getBlockState(context.getBlockPos()).getBlock();
		if (!context.getWorld().getGameRules().getBoolean(Celestium.END_PORTAL_OPEN) && targetedBlock == Blocks.END_PORTAL_FRAME) {
			PlayerEntity player = context.getPlayer();
			if (player != null){
				Vec3d pos = player.getPos();
				player.sendMessage(Text.literal("§9< §bThe end is currently disabled. §9>"), true);
				context.getWorld().playSound(pos.x, pos.y, pos.z, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.MASTER, 1, 1, true);
			}
			info.cancel();
		}
	}
}
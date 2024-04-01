package supertoady.celestium.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import supertoady.celestium.Celestium;
import supertoady.celestium.item.ModItems;

import java.util.List;

public class SpaceGunItem extends ToolItem {
    public SpaceGunItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        NbtCompound nbt = stack.getOrCreateNbt();

        if (nbt.getInt("spacegun:charge") <= 0 || user.getItemCooldownManager().isCoolingDown(stack.getItem())) return super.use(world, user, hand);

        int charge = nbt.getInt("spacegun:charge") - 1;
        if (charge <= 0) user.getItemCooldownManager().set(stack.getItem(), 20 * 5);
        else user.getItemCooldownManager().set(stack.getItem(), 20 * 2);
        nbt.putInt("spacegun:charge", charge);

        world.playSound(user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1, 1, true);

        laserStep(user.getEyePos(), user.getRotationVector(), user, 15);

        return super.use(world, user, hand);
    }

    private void laserStep(Vec3d pos, Vec3d dir, PlayerEntity user, int length){
        int length2 = length - 1;

        if (length2 > 0) {
            Celestium.showParticlesToAll(user.world, ParticleTypes.FLAME, pos, 0, 1, 0);
            Celestium.showParticlesToAll(user.world, ParticleTypes.LAVA, pos, 0, 1, 0);

            Vec3d next = new Vec3d(pos.x + dir.x ,pos.y + dir.y, pos.z + dir.z);

            Box froginabox = new Box(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, pos.x - 0.5, pos.y - 0.5, pos.z - 0.5);
            List<Entity> entities = user.world.getOtherEntities(user, froginabox);

            for (Entity entity : entities){
                if (entity.isLiving()){
                    LivingEntity dude = ((LivingEntity) entity);

                    dude.setFireTicks(20 * 5);
                    dude.damage(DamageSource.ON_FIRE, 8);

                    Celestium.showParticlesToAll(user.world, ParticleTypes.EXPLOSION, dude.getPos(), 0, 1, 0);
                }
                entity.addVelocity(dir.x, dir.y / 1.5f, dir.z);
            }

            laserStep(next, dir, user, length2);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        NbtCompound nbt = stack.getOrCreateNbt();
        PlayerEntity user = ((PlayerEntity) entity);
        if (!user.getItemCooldownManager().isCoolingDown(stack.getItem()) && nbt.getInt("spacegun:charge") <= 0) {
            nbt.putInt("spacegun:charge", 3);
        }

        if (user.getMainHandStack() == stack || user.getOffHandStack() == stack){
            user.sendMessage(Text.literal("§cCharge: §6" + nbt.getInt("spacegun:charge")), true);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}

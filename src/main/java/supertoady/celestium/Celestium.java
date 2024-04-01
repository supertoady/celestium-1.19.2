package supertoady.celestium;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ParticleCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import supertoady.celestium.block.ModBlocks;
import supertoady.celestium.item.ModItems;

import java.util.List;

public class Celestium implements ModInitializer {
	public static final String MOD_ID = "celestium";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final GameRules.Key<GameRules.BooleanRule> END_PORTAL_OPEN =
			GameRuleRegistry.register("isEndPortalOpen", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Loaded " + MOD_ID + "!");

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}

	public static ServerWorld getServerFromWorld(World world){
		if (world.getServer() != null){
            return world.getServer().getWorld(world.getRegistryKey());
		}
		return null;
	}

	public static void showParticlesToAll(World world, ParticleEffect particle, Vec3d pos, double delta, int count, double speed){
		ServerWorld serverWorld = getServerFromWorld(world);

		if (serverWorld != null){
			serverWorld.spawnParticles(particle, pos.x, pos.y, pos.z, count, delta, delta, delta, speed);
		}
	}
}
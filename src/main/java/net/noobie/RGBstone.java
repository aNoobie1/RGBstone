package net.noobie;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.noobie.block.ModBlocks;
import net.noobie.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class RGBstone implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("rgbstone");

	public static boolean IsBlocksEqual(BlockState state, Block block){
		if (state.isOf(block)){
			return true;
		}
		return false;
	}

	public static BlockState mostRecentBlock;

	public static ServerWorld world;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModBlocks.RegisterBlocks();
		ModItems.RegisterItems();

		LOGGER.info(String.valueOf(IsBlocksEqual(Blocks.REDSTONE_WIRE.getDefaultState(),ModBlocks.BLUESTONE_WIRE)));
		LOGGER.info(String.valueOf(IsBlocksEqual(ModBlocks.BLUESTONE_WIRE.getDefaultState(),ModBlocks.BLUESTONE_WIRE)));

		ServerWorldEvents.LOAD.register((server, newWorld) -> {
			world = newWorld;
		});


		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, env) -> {
			dispatcher.register(
					literal("playerspeed") // The first part of the command. /playerspeed
							.then(
									literal("set").then( // a keyword thingy. this will add a paramater option to the command. '/playerspeed set'
											argument("speed", FloatArgumentType.floatArg()).then( // Creates am arguement for a float, with the id 'speed'.
													argument("entities", EntityArgumentType.entities()) // Create the arguement for the entities.
															.executes(src ->{ // Execute the command code.
																var appliesTo = EntityArgumentType.getEntities(src, "entities"); // these two variables are getting the arguement values.
																var speedVal = FloatArgumentType.getFloat(src, "speed");
																AtomicInteger entCount = new AtomicInteger(0); // Create a integer thingy. For keeping track.
																appliesTo.forEach((entity) -> { // Regular for each loop.
																	if (entity instanceof LivingEntity livingEntity) { // Check if speed can be applied (ex: Boats cannot get speed, they are NOT a LivingEntity)
																		entCount.addAndGet(1); // += basically
																		livingEntity.
																	}
																});
																String msg; // Message variable
																int ents = entCount.get(); // get the actual count from the counter.
																if (ents > 1) { // Is more then 1???
																	msg = "Set speed for "+entCount+" entities."; // Set the message string
																}
																else if (ents == 1) { // is 1????
																	msg = "Set speed for a entity";
																}
																else{ // Less than 1?????
																	msg = "No entities were applied to.";
																}
																src.getSource().sendMessage(Text.literal(msg)); // Send message on the log and chat of the running user.
																return 1; // Return. Idk for sure but 1 is success...?

															})
											)
									)
							)
			);
		});

		LOGGER.info("Hello Fabric world!");
	}
}
package net.noobie.task;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RedstoneView;
import net.minecraft.world.World;
import net.noobie.RGBstone;
import net.noobie.block.ModBlocks;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.noobie.RGBstone.world;
import static net.noobie.block.ModBlocks.BLUESTONE_BLOCKS;

public class RedstoneBlockCheckTask implements Runnable {
    final Direction[] DIRECTIONS = Direction.values();

    private final CallbackInfoReturnable<Integer> cir;

    private RedstoneView redstoneView;
    private BlockPos pos;
    public RedstoneBlockCheckTask(BlockPos pos, RedstoneView rv, CallbackInfoReturnable<Integer> cir) {
        super();
        this.pos = pos;
        redstoneView = rv;
        this.cir = cir;
    }

    @Override
    public void run() {
        boolean finished = false;
        RGBstone.LOGGER.info("connect");
        while (!finished){
            var block = world.getBlockState(pos);
            RGBstone.LOGGER.info(block.getBlock().getClass().getName());
            RGBstone.LOGGER.info(String.valueOf(RGBstone.IsBlocksEqual(block, ModBlocks.BLUESTONE_WIRE)));
            if (RGBstone.IsBlocksEqual(block.getBlock().getDefaultState(), Blocks.AIR)) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (RGBstone.IsBlocksEqual(block.getBlock().getDefaultState(),ModBlocks.BLUESTONE_WIRE)){
                RGBstone.LOGGER.info("blue");

                int i = 0;

                for (Direction direction : DIRECTIONS) {
                    if (BLUESTONE_BLOCKS.contains(world.getBlockState(pos.offset(direction)).getBlock()))
                    {
                        RGBstone.LOGGER.info("connected to blue");

                        int j = redstoneView.getEmittedRedstonePower(pos.offset(direction), direction);
                        if (j >= 15) {
                            cir.setReturnValue(15);
                        }

                        if (j > i) {
                            i = j;
                        }
                    }

                }

                cir.setReturnValue(i);
                cir.cancel();
                finished = true;
            }
            else {
                finished = true;
            }
        }
        }

};
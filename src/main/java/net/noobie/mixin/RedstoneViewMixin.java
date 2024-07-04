package net.noobie.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RedstoneView;
import net.noobie.RGBstone;
import net.noobie.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.noobie.RGBstone.world;
import static net.noobie.block.ModBlocks.*;

@Mixin(value = RedstoneView.class, remap = false)
public interface RedstoneViewMixin extends RedstoneView {
    @Inject(at=@At("HEAD"), method = "getReceivedRedstonePower", cancellable = true)
    default void modifyConnectionRules(BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        RGBstone.LOGGER.info("connect");
        var block = world.getBlockState(pos).getBlock();
        if (RGBstone.IsBlocksEqual(ModBlocks.BLUESTONE_WIRE, block)){
            RGBstone.LOGGER.info("blue");

            int i = 0;

            for (Direction direction : DIRECTIONS) {
                if (BLUESTONE_BLOCKS.contains(world.getBlockState(pos.offset(direction)).getBlock()))
                {
                    RGBstone.LOGGER.info("connected to blue");

                    int j = getEmittedRedstonePower(pos.offset(direction), direction);
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
        }

    }
    @Shadow
    default int getEmittedRedstonePower(BlockPos pos, Direction direction){return 0;}

    @Shadow
    Direction[] DIRECTIONS = new Direction[]{};
}


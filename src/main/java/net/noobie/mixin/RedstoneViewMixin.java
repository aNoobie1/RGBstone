package net.noobie.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RedstoneView;
import net.minecraft.world.World;
import net.noobie.RGBstone;
import net.noobie.block.ModBlocks;
import net.noobie.task.RedstoneBlockCheckTask;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
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
        if (world != null){
            var task = new RedstoneBlockCheckTask(pos, (RedstoneView)(Object)this, cir);
            var thread = new Thread(task);
            thread.start();
        }

    }
    @Shadow
    default int getEmittedRedstonePower(BlockPos pos, Direction direction){return 0;}

    @Shadow
    Direction[] DIRECTIONS = new Direction[]{};
}


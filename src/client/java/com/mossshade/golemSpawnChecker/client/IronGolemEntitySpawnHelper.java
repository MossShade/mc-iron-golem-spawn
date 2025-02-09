package com.mossshade.golemSpawnChecker.client;

import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Optional;

public class IronGolemEntitySpawnHelper {
    public static Optional<BlockPos.Mutable> trySpawnAt(
            IronGolemEntity mobEntity,
            SpawnReason reason,
            World world,
            BlockPos pos,
            int tries,
            int horizontalRange,
            int verticalRange,
            IronGolemEntitySpawnHelper.Requirements requirements,
            boolean requireEmptySpace
    ) {
        EntityType<IronGolemEntity> entityType = EntityType.IRON_GOLEM;
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (int i = 0; i < tries; i++) {
            int j = MathHelper.nextBetween(world.random, -horizontalRange, horizontalRange);
            int k = MathHelper.nextBetween(world.random, -horizontalRange, horizontalRange);
            mutable.set(pos, j, verticalRange, k);
            if (world.getWorldBorder().contains(mutable)
                    && findSpawnPos(world, verticalRange, mutable, requirements)
                    && (!requireEmptySpace || world.isSpaceEmpty(entityType.getSpawnBox((double)mutable.getX() + 0.5, (double)mutable.getY(), (double)mutable.getZ() + 0.5)))) {
                mobEntity.setPosition(mutable.getX(), mutable.getY(), mutable.getZ());
                if (mobEntity.canSpawn(world, reason) && mobEntity.canSpawn(world)) {
                    return Optional.of(mutable);
                }
            }
        }

        return Optional.empty();
    }

    private static boolean findSpawnPos(World world, int verticalRange, BlockPos.Mutable pos, IronGolemEntitySpawnHelper.Requirements requirements) {
        BlockPos.Mutable mutable = new BlockPos.Mutable().set(pos);
        BlockState blockState = world.getBlockState(mutable);

        for (int i = verticalRange; i >= -verticalRange; i--) {
            pos.move(Direction.DOWN);
            mutable.set(pos, Direction.UP);
            BlockState blockState2 = world.getBlockState(pos);
            if (requirements.canSpawnOn(pos, blockState2, mutable, blockState)) {
                pos.move(Direction.UP);
                return true;
            }

            blockState = blockState2;
        }

        return false;
    }

    public interface Requirements {
        @Deprecated
        IronGolemEntitySpawnHelper.Requirements IRON_GOLEM = (pos, state, abovePos, aboveState) -> !state.isOf(Blocks.COBWEB)
                && !state.isOf(Blocks.CACTUS)
                && !state.isOf(Blocks.GLASS_PANE)
                && !(state.getBlock() instanceof StainedGlassPaneBlock)
                && !(state.getBlock() instanceof StainedGlassBlock)
                && !(state.getBlock() instanceof LeavesBlock)
                && !state.isOf(Blocks.CONDUIT)
                && !state.isOf(Blocks.ICE)
                && !state.isOf(Blocks.TNT)
                && !state.isOf(Blocks.GLOWSTONE)
                && !state.isOf(Blocks.BEACON)
                && !state.isOf(Blocks.SEA_LANTERN)
                && !state.isOf(Blocks.FROSTED_ICE)
                && !state.isOf(Blocks.TINTED_GLASS)
                && !state.isOf(Blocks.GLASS)
                ? (aboveState.isAir() || aboveState.isLiquid()) && (state.isSolid() || state.isOf(Blocks.POWDER_SNOW))
                : false;

        boolean canSpawnOn(BlockPos pos, BlockState state, BlockPos abovePos, BlockState aboveState);
    }
}

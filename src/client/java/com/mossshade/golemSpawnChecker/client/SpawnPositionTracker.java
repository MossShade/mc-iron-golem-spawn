package com.mossshade.golemSpawnChecker.client;

import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class SpawnPositionTracker {
    private static final Set<BlockPos> VALID_SPAWN_POSITIONS = new HashSet<>();

    public static void addSpawnPosition(BlockPos pos) {
        VALID_SPAWN_POSITIONS.add(pos.mutableCopy());
    }

    public static Set<BlockPos> getValidSpawnPositions() {
        return VALID_SPAWN_POSITIONS;
    }

    public static void clearSpawnPositions() {
        VALID_SPAWN_POSITIONS.clear();
    }
}

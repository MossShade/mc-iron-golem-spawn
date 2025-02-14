package com.mossshade.golemSpawnChecker.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class KeyHandler {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (GolemSpawnCheckerClient.getScanKey().wasPressed()) {
                scanForGolemSpawns();
            } else if (GolemSpawnCheckerClient.getClearKey().wasPressed()) {
                SpawnPositionTracker.clearSpawnPositions();
            }
        });
    }

    private static void scanForGolemSpawns() {
        if (client.world == null || client.player == null) return;

        SpawnPositionTracker.clearSpawnPositions(); // Clear previous spawn positions

        World world = client.world;
        BlockPos playerPos = client.player.getBlockPos();
        int radius = 8; // Half of 16x16 area

        // Create a temporary Iron Golem entity at the test position
        IronGolemEntity testGolem = new IronGolemEntity(EntityType.IRON_GOLEM, world);

        if (!GolemSpawnCheckerClient.getCalculateHitbox()) {
            GolemSpawnCheckerOverride.enableOverride();
        }

        for (BlockPos pos : BlockPos.iterate(
                playerPos.add(-radius, 0, -radius),
                playerPos.add(radius, 0, radius))) {
            Optional<BlockPos.Mutable> golemCheck = IronGolemEntitySpawnHelper.trySpawnAt(
                    testGolem,
                    SpawnReason.MOB_SUMMONED,
                    world,
                    pos,
                    1,
                    0,
                    6,
                    IronGolemEntitySpawnHelper.Requirements.IRON_GOLEM,
                    false
            );

            golemCheck.ifPresent(SpawnPositionTracker::addSpawnPosition);
        }

        GolemSpawnCheckerOverride.disableOverride();

        testGolem.discard();
    }
}

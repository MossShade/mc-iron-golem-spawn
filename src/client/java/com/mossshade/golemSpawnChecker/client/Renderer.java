package com.mossshade.golemSpawnChecker.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.Set;

public class Renderer {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void render(WorldRenderContext context) {
        if (client.world == null || client.player == null) return;

        MatrixStack matrices = context.matrixStack();
        if (matrices == null) return;

        Camera camera = context.camera();
        Vec3d camPos = camera.getPos();
        VertexConsumerProvider.Immediate vertexConsumers = client.getBufferBuilders().getEntityVertexConsumers();

        // Get valid Iron Golem spawn positions
        Set<BlockPos> spawnPositions = SpawnPositionTracker.getValidSpawnPositions();

        for (BlockPos pos : spawnPositions) {
            drawDebugBox(matrices, vertexConsumers, pos, camPos);
        }

        vertexConsumers.draw(); // Flush rendering
    }

    private static void drawDebugBox(MatrixStack matrices, VertexConsumerProvider vertexConsumers, BlockPos pos, Vec3d camPos) {
        matrices.push();

        // Offset the box position relative to the player's camera
        double x = pos.getX() - camPos.x;
        double y = pos.getY() - camPos.y;
        double z = pos.getZ() - camPos.z;

        Box box = new Box(x, y, z, x + 1, y + 1, z + 1);
        DebugRenderer.drawBox(matrices, vertexConsumers, box, 0.5f, 0.0f, 0.5f, 0.3f);

        matrices.pop();
    }
}

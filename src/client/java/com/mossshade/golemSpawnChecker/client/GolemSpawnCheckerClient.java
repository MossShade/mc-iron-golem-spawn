package com.mossshade.golemSpawnChecker.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class GolemSpawnCheckerClient implements ClientModInitializer {
    private static KeyBinding scanKey;
    private static KeyBinding clearKey;

    @Override
    public void onInitializeClient() {
        scanKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.golemspawnchecker.scan",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET,
                "category.golemspawnchecker"
        ));
        clearKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.golemspawnchecker.clear",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET,
                "category.golemspawnchecker"
        ));

        GolemSpawnKeyHandler.register();

        WorldRenderEvents.LAST.register(GolemSpawnRenderer::render);
    }

    public static KeyBinding getScanKey() {
        return scanKey;
    }

    public static KeyBinding getClearKey() {
        return clearKey;
    }
}

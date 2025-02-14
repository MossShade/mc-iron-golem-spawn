package com.mossshade.golemSpawnChecker.client;

import com.mossshade.golemSpawnChecker.client.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class GolemSpawnCheckerClient implements ClientModInitializer {
    private static KeyBinding scanKey;
    private static KeyBinding clearKey;
    private static Boolean calculateHitbox;

    @Override
    public void onInitializeClient() {

        ConfigManager.loadConfig();

        scanKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                Constants.SCAN_KEY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET,
                Constants.CATEGORY
        ));
        clearKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                Constants.CLEAR_KEY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET,
                Constants.CATEGORY
        ));
        calculateHitbox = ConfigManager.getCalculateHitbox();

        KeyHandler.register();

        WorldRenderEvents.LAST.register(Renderer::render);
    }

    public static KeyBinding getScanKey() {
        return scanKey;
    }

    public static KeyBinding getClearKey() {
        return clearKey;
    }

    public static Boolean getCalculateHitbox() {return calculateHitbox;}

    public static void setCalculateHitbox(Boolean value) {
        calculateHitbox = value;
    }
}

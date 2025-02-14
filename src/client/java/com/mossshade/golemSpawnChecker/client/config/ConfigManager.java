package com.mossshade.golemSpawnChecker.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mossshade.golemSpawnChecker.client.Constants;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigManager {
    private static final Path CONFIG_PATH = Paths.get(Constants.CONFIG_PATH);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static ConfigData configData;

    public static class ConfigData {
        public boolean calculateHitbox = true;
    }

    public static void loadConfig() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                configData = GSON.fromJson(reader, ConfigData.class);
            } catch (IOException e) {
                System.err.println("Failed to load config file: " + CONFIG_PATH);
                System.err.println(e.getMessage());
            }
        }

        if (configData == null) {
            configData = new ConfigData();
            saveConfig();
        }
    }

    public static void saveConfig() {
        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
            GSON.toJson(configData, writer);
        } catch (IOException e) {
            System.err.println("Failed to save config file: " + CONFIG_PATH);
            System.err.println(e.getMessage());
        }
    }

    public static boolean getCalculateHitbox() {
        return configData.calculateHitbox;
    }

    public static void setCalculateHibox(boolean calculateHitbox) {
        configData.calculateHitbox = calculateHitbox;
        saveConfig();
    }

}

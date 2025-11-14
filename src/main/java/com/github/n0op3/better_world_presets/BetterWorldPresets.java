package com.github.n0op3.better_world_presets;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BetterWorldPresets {
    public static final String MOD_ID = "better_world_presets";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Path getConfigDir() {
        var path = FabricLoader.getInstance().getConfigDir().resolve(MOD_ID);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            LOGGER.error("Failed to create config directory!");
            throw new RuntimeException(e);
        }
        return path;
    }
}
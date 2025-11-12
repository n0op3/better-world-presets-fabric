package com.github.n0op3.better_world_presets;

import com.github.n0op3.better_world_presets.config.WorldPresetConfig;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BetterWorldPresets {
    public static final String MOD_ID = "better_world_presets";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final List<BetterWorldPreset> WORLD_PRESETS = new ArrayList<>();
    private static final List<Runnable> PRESET_CHANGE_LISTENERS = new ArrayList<>();
    private static BetterWorldPreset CURRENT_PRESET;

    public static void registerPresetChangeListener(Runnable listener) {
        PRESET_CHANGE_LISTENERS.add(listener);
    }

    public static BetterWorldPreset getCurrentPreset() {
        return CURRENT_PRESET;
    }

    public static void setCurrentPreset(BetterWorldPreset preset) {
        CURRENT_PRESET = preset;
        PRESET_CHANGE_LISTENERS.forEach(Runnable::run);
    }

    public static void createPreset(BetterWorldPreset preset) {
        addPreset(preset);
        WorldPresetConfig.saveWorldPreset(preset);
    }

    public static void addPreset(BetterWorldPreset preset) {
        WORLD_PRESETS.add(preset);
    }

    public static void removePreset(BetterWorldPreset preset) {
        WORLD_PRESETS.remove(preset);
        WorldPresetConfig.deletePresetFile(preset.worldName());
    }

    public static void onShutdown() {
        BetterWorldPresets.LOGGER.info("Saving preset data...");
        WorldPresetConfig.saveAllPresets();
    }

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
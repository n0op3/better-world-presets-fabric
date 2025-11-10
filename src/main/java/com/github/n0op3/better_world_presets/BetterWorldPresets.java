package com.github.n0op3.better_world_presets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BetterWorldPresets {
    public static final String MOD_ID = "better_world_presets";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final List<WorldPreset> WORLD_PRESETS = new ArrayList<>();
    private static final List<Runnable> PRESET_CHANGE_LISTENERS = new ArrayList<>();
    private static WorldPreset CURRENT_PRESET;

    public static void registerPresetChangeListener(Runnable listener) {
        PRESET_CHANGE_LISTENERS.add(listener);
    }

    public static WorldPreset getCurrentPreset() {
        return CURRENT_PRESET;
    }

    public static void setCurrentPreset(WorldPreset preset) {
        CURRENT_PRESET = preset;
        PRESET_CHANGE_LISTENERS.forEach(Runnable::run);
    }

}
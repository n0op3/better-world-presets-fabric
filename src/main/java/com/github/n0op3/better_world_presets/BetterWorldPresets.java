package com.github.n0op3.better_world_presets;

import net.minecraft.world.gen.GeneratorOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BetterWorldPresets {
	public static final String MOD_ID = "better-world-presets";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static List<WorldPreset> WORLD_PRESETS = new ArrayList<>(Collections.singleton(new WorldPreset("Test World Preset", GeneratorOptions.DEMO_OPTIONS)));
}
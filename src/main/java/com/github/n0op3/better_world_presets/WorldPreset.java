package com.github.n0op3.better_world_presets;

import net.minecraft.world.GameMode;
import net.minecraft.world.gen.GeneratorOptions;

public record WorldPreset(
        String worldName,
        String seed,
        boolean generateStructures,
        boolean bonusChest,
        GameMode gameMode
) {
}
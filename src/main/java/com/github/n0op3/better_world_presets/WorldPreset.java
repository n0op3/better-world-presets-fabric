package com.github.n0op3.better_world_presets;

import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;

public record WorldPreset(
        String worldName,
        String seed,
        boolean generateStructures,
        boolean bonusChest,
        GameMode gameMode,
        Difficulty difficulty,
        boolean commandsAllowed
) {
}
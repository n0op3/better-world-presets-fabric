package com.github.n0op3.better_world_presets;

import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.world.GeneratorOptionsHolder;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;

public record WorldPreset(
        String worldName,
        String seed,
        boolean generateStructures,
        boolean bonusChest,
        WorldCreator.Mode gameMode,
        Difficulty difficulty,
        boolean commandsAllowed,
        WorldCreator.WorldType worldType,
        GameRules gameRules,
        GeneratorOptionsHolder generatorOptionsHolder
) {
}
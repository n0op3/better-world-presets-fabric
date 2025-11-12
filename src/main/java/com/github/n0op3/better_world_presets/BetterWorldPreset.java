package com.github.n0op3.better_world_presets;

import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public record BetterWorldPreset(
        String worldName,
        String seed,
        boolean generateStructures,
        boolean bonusChest,
        WorldCreator.Mode gameMode,
        Difficulty difficulty,
        boolean commandsAllowed,
        GameRules gameRules,
        ChunkGenerator chunkGenerator,
        WorldCreator.WorldType worldType
) {
}
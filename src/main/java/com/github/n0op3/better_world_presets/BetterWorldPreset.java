package com.github.n0op3.better_world_presets;

import com.github.n0op3.better_world_presets.config.PresetManager;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class BetterWorldPreset {
    private String worldName;
    private final String seed;
    private final boolean generateStructures;
    private final boolean bonusChest;
    private final WorldCreator.Mode gameMode;
    private final Difficulty difficulty;
    private final boolean commandsAllowed;
    private final GameRules gameRules;
    private final ChunkGenerator chunkGenerator;
    private final WorldCreator.WorldType worldType;

    public BetterWorldPreset(String worldName, String seed, boolean generateStructures, boolean bonusChest, WorldCreator.Mode gameMode, Difficulty difficulty, boolean commandsAllowed, GameRules gameRules, ChunkGenerator chunkGenerator, WorldCreator.WorldType worldType) {
        this.worldName = worldName;
        this.seed = seed;
        this.generateStructures = generateStructures;
        this.bonusChest = bonusChest;
        this.gameMode = gameMode;
        this.difficulty = difficulty;
        this.commandsAllowed = commandsAllowed;
        this.gameRules = gameRules;
        this.chunkGenerator = chunkGenerator;
        this.worldType = worldType;
    }

    public String worldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        PresetManager.onRenamePreset(this.worldName);
        this.worldName = worldName;
        PresetManager.saveWorldPreset(this);
    }

    public String seed() {
        return seed;
    }

    public boolean generateStructures() {
        return generateStructures;
    }

    public boolean bonusChest() {
        return bonusChest;
    }

    public WorldCreator.Mode gameMode() {
        return gameMode;
    }

    public Difficulty difficulty() {
        return difficulty;
    }

    public boolean commandsAllowed() {
        return commandsAllowed;
    }

    public GameRules gameRules() {
        return gameRules;
    }

    public ChunkGenerator chunkGenerator() {
        return chunkGenerator;
    }

    public WorldCreator.WorldType worldType() {
        return worldType;
    }
}
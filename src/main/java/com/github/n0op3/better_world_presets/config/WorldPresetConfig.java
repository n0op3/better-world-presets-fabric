package com.github.n0op3.better_world_presets.config;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import com.github.n0op3.better_world_presets.WorldPreset;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldPresetConfig {

    private static final Random rng = new Random();

    public static void saveWorldPreset(WorldPreset preset) {
        NbtCompound nbt = presetToNbt(preset);

        try {
            Files.createDirectories(BetterWorldPresets.getConfigDir());
            var path = BetterWorldPresets.getConfigDir().resolve(preset.worldName() + "_" + rng.nextInt() + ".nbt");
            NbtIo.writeCompressed(nbt, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveAllPresets() {
        for (File presetFile : BetterWorldPresets.getConfigDir().toFile().listFiles()) {
            presetFile.delete();
        }
        BetterWorldPresets.getPresets().forEach(WorldPresetConfig::saveWorldPreset);
    }

    private static @NotNull NbtCompound presetToNbt(WorldPreset preset) {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("world_name", preset.worldName());
        nbt.putString("seed", preset.seed());
        nbt.putBoolean("generate_structures", preset.generateStructures());
        nbt.putBoolean("bonus_chest", preset.bonusChest());
        nbt.putString("game_mode", preset.gameMode().defaultGameMode.name());
        nbt.putString("difficulty", preset.difficulty().asString());
        nbt.putBoolean("commands_allowed", preset.commandsAllowed());
        nbt.put("game_rules", preset.gameRules().toNbt());
        return nbt;
    }

    public static List<WorldPreset> loadPresets() {
        List<WorldPreset> worldPresets = new ArrayList<>();
        if (!Files.exists(BetterWorldPresets.getConfigDir())) {
            return worldPresets;
        }

        for (File presetFile : BetterWorldPresets.getConfigDir().toFile().listFiles()) {
            try {
                NbtCompound nbt = NbtIo.read(presetFile.toPath());
                // TODO
            } catch (IOException e) {
                BetterWorldPresets.LOGGER.error("Failed to load preset from file {}: {}", presetFile.getName(), e.getMessage());
                e.printStackTrace();
            }
        }
        return worldPresets;
    }
}

package com.github.n0op3.better_world_presets.config;

import com.github.n0op3.better_world_presets.BetterWorldPreset;
import com.github.n0op3.better_world_presets.BetterWorldPresets;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.registry.RegistryOps;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class WorldPresetConfig {

    public static WorldCreator WORLD_CREATOR;

    public static void saveWorldPreset(BetterWorldPreset preset) {
        NbtCompound nbt = presetToNbt(preset);

        try {
            Files.createDirectories(BetterWorldPresets.getConfigDir());
            var path = BetterWorldPresets.getConfigDir().resolve(preset.worldName() + ".nbt");
            NbtIo.writeCompressed(nbt, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveAllPresets() {
        BetterWorldPresets.WORLD_PRESETS.forEach(WorldPresetConfig::saveWorldPreset);
    }

    private static @NotNull NbtCompound presetToNbt(BetterWorldPreset preset) {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("world_name", preset.worldName());
        nbt.putString("seed", preset.seed());
        nbt.putBoolean("generate_structures", preset.generateStructures());
        nbt.putBoolean("bonus_chest", preset.bonusChest());
        nbt.putString("game_mode", preset.gameMode().name());
        nbt.putString("difficulty", preset.difficulty().getName());
        nbt.putBoolean("commands_allowed", preset.commandsAllowed());
        nbt.put("game_rules", preset.gameRules().toNbt());
        nbt.put("chunk_generator", ChunkGenerator.CODEC.encodeStart(RegistryOps.of(NbtOps.INSTANCE, WORLD_CREATOR.getGeneratorOptionsHolder().getCombinedRegistryManager()), preset.chunkGenerator()).getOrThrow());
        nbt.putString("world_type", WORLD_CREATOR.getWorldType().preset().getIdAsString());
        return nbt;
    }

    public static void loadPresets() {
        BetterWorldPresets.WORLD_PRESETS.clear();
        if (!Files.exists(BetterWorldPresets.getConfigDir())) {
            return;
        }

        for (File presetFile : BetterWorldPresets.getConfigDir().toFile().listFiles()) {
            try {
                NbtCompound nbt = NbtIo.readCompressed(presetFile.toPath(), NbtSizeTracker.ofUnlimitedBytes());
                GameRules gameRules = new GameRules();
                NbtCompound rulesNbt = nbt.getCompound("game_rules");
                gameRules.accept(new GameRules.Visitor() {
                    @Override
                    public void visitBoolean(GameRules.Key<GameRules.BooleanRule> key, GameRules.Type<GameRules.BooleanRule> type) {
                        String ruleName = key.getName();
                        if (rulesNbt.contains(ruleName)) {
                            String valueStr = rulesNbt.getString(ruleName);
                            var rule = gameRules.get(key);
                            rule.set(Boolean.parseBoolean(valueStr), null);
                        }
                    }

                    @Override
                    public void visitInt(GameRules.Key<GameRules.IntRule> key, GameRules.Type<GameRules.IntRule> type) {
                        String ruleName = key.getName();
                        if (rulesNbt.contains(ruleName)) {
                            String valueStr = rulesNbt.getString(ruleName);
                            var rule = gameRules.get(key);
                            rule.set(Integer.parseInt(valueStr), null);
                        }
                    }
                });

                ChunkGenerator chunkGenerator = ChunkGenerator.CODEC.parse(RegistryOps.of(NbtOps.INSTANCE, WORLD_CREATOR.getGeneratorOptionsHolder().getCombinedRegistryManager()), nbt.get("chunk_generator")).getOrThrow();
                WorldCreator.WorldType worldType = WORLD_CREATOR.getNormalWorldTypes().stream().filter(type -> Objects.equals(type.preset().getIdAsString(), nbt.getString("world_type"))).findFirst().get();
                worldType.preset().value().getOverworld().get().chunkGenerator = chunkGenerator;
                BetterWorldPreset preset = new BetterWorldPreset(
                        nbt.getString("world_name"),
                        nbt.getString("seed"),
                        nbt.getBoolean("generate_structures"),
                        nbt.getBoolean("bonus_chest"),
                        WorldCreator.Mode.valueOf(nbt.getString("game_mode")),
                        Difficulty.byName(nbt.getString("difficulty")),
                        nbt.getBoolean("commands_allowed"),
                        gameRules,
                        chunkGenerator,
                        worldType
                );
                BetterWorldPresets.addPreset(preset);
            } catch (IOException e) {
                BetterWorldPresets.LOGGER.error("Failed to load preset fromfile {}: {}", presetFile.getName(), e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void deletePresetFile(String fileName) {
        try {
            Files.delete(BetterWorldPresets.getConfigDir().resolve(fileName + ".nbt"));
        } catch (IOException e) {
            BetterWorldPresets.LOGGER.error("Failed to delete a preset file: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}

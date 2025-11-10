package com.github.n0op3.better_world_presets.config;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import com.github.n0op3.better_world_presets.WorldPreset;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.dimension.DimensionOptionsRegistryHolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WorldPresetConfig {

    public static void saveWorldPreset(WorldPreset preset) {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("world_name", preset.worldName());
        nbt.putString("seed", preset.seed());
        nbt.putBoolean("generate_structures", preset.generateStructures());
        nbt.putBoolean("bonus_chest", preset.bonusChest());
        nbt.putString("game_mode", preset.gameMode().defaultGameMode.name());
        nbt.putString("difficulty", preset.difficulty().asString());
        nbt.putBoolean("commands_allowed", preset.commandsAllowed());
        nbt.putString("world_type", preset.worldType().preset().getIdAsString());
        nbt.put("game_rules", preset.gameRules().toNbt());
        nbt.put("dimensions", DimensionOptionsRegistryHolder.CODEC.codec().encodeStart(NbtOps.INSTANCE, preset.dimensions()).getOrThrow());

        Path path = FabricLoader.getInstance().getConfigDir().resolve(BetterWorldPresets.MOD_ID);
        try {
            Files.createDirectories(path);
            path = path.resolve("data.nbt");
            NbtIo.writeCompressed(nbt, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

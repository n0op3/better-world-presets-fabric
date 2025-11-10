package com.github.n0op3.better_world_presets.config;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import com.github.n0op3.better_world_presets.WorldPreset;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.dimension.DimensionOptionsRegistryHolder;

public class WorldPresetConfig {

    public static void saveWorldPreset(WorldPreset preset) {
        DataResult<JsonElement> result = DimensionOptionsRegistryHolder.CODEC.encode(preset.dimensions(), JsonOps.INSTANCE, DimensionOptionsRegistryHolder.CODEC.compressedBuilder(JsonOps.INSTANCE)).build(new JsonObject());
        JsonElement json = result.resultOrPartial(BetterWorldPresets.LOGGER::error).get();
        BetterWorldPresets.LOGGER.info("Serialized dims: {}", json);
    }

}

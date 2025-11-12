package com.github.n0op3.better_world_presets.mixin;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import net.minecraft.world.dimension.DimensionOptionsRegistryHolder;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionOptionsRegistryHolder.class)
public class DimensionOptionsRegistryHolderMixin {

    @Inject(method = "getChunkGenerator", at = @At("HEAD"), cancellable = true)
    private void getChunkGenerator(CallbackInfoReturnable<ChunkGenerator> cir) {
        if (BetterWorldPresets.getCurrentPreset() != null) {
            cir.setReturnValue(BetterWorldPresets.getCurrentPreset().chunkGenerator());
            cir.cancel();
        }
    }

}

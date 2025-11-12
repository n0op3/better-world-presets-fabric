package com.github.n0op3.better_world_presets.mixin;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(method = "close", at = @At("HEAD"))
    private void shutdown(CallbackInfo ci) {
        BetterWorldPresets.onShutdown();
    }

}

package com.github.n0op3.better_world_presets.mixin;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.world.GeneratorOptionsHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.Optional;
import java.util.OptionalLong;

@Mixin(WorldCreator.class)
public class WorldCreatorMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void registerListener(Path savesDirectory, GeneratorOptionsHolder generatorOptionsHolder, Optional defaultWorldType, OptionalLong seed, CallbackInfo ci) {
        BetterWorldPresets.registerPresetChangeListener(() -> ((WorldCreator) (Object) this).setWorldName(BetterWorldPresets.getCurrentPreset().worldName()));
    }

}

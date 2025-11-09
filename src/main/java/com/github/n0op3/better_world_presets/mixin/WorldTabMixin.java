package com.github.n0op3.better_world_presets.mixin;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.WorldTab.class)
public class WorldTabMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void registerListener(CreateWorldScreen createWorldScreen, CallbackInfo ci) {
        BetterWorldPresets.registerPresetChangeListener(() -> ((CreateWorldScreen.WorldTab) (Object) this).seedField.setText(BetterWorldPresets.getCurrentPreset().seed()));
    }

}

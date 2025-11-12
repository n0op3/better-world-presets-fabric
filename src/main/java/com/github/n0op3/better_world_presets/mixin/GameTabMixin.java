package com.github.n0op3.better_world_presets.mixin;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.GameTab.class)
public class GameTabMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void registerListener(CreateWorldScreen createWorldScreen, CallbackInfo ci, @Local(ordinal = 0) CyclingButtonWidget<WorldCreator.Mode> gameModeButton, @Local(ordinal = 1) CyclingButtonWidget<Difficulty> difficultyButton, @Local(ordinal = 2) CyclingButtonWidget<Boolean> commandsAllowedButton) {
        BetterWorldPresets.registerPresetChangeListener(() -> {
            if (BetterWorldPresets.getCurrentPreset() == null) return;
            ((CreateWorldScreen.GameTab) (Object) this).worldNameField.setText(BetterWorldPresets.getCurrentPreset().worldName());
            gameModeButton.setValue(BetterWorldPresets.getCurrentPreset().gameMode());
            difficultyButton.setValue(BetterWorldPresets.getCurrentPreset().difficulty());
            commandsAllowedButton.setValue(BetterWorldPresets.getCurrentPreset().commandsAllowed());
        });
    }

}

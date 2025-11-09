package com.github.n0op3.better_world_presets.mixin;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Positioner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CreateWorldScreen.GameTab.class)
public class GameTabMixin {

    @Inject(method = "<init>", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void registerListener(CreateWorldScreen createWorldScreen, CallbackInfo ci, GridWidget.Adder adder, Positioner positioner, CyclingButtonWidget<WorldCreator.Mode> cyclingButtonWidget, CyclingButtonWidget cyclingButtonWidget2, CyclingButtonWidget cyclingButtonWidget3) {
        BetterWorldPresets.registerPresetChangeListener(() -> {
            ((CreateWorldScreen.GameTab) (Object) this).worldNameField.setText(BetterWorldPresets.getCurrentPreset().worldName());
            cyclingButtonWidget.setValue(WorldCreator.Mode.valueOf(BetterWorldPresets.getCurrentPreset().gameMode().name()));
        });
    }

}

package com.github.n0op3.better_world_presets.mixin;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import com.github.n0op3.better_world_presets.screen.WorldPresetsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldCallback;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.client.world.GeneratorOptionsHolder;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.OptionalLong;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends Screen {

    protected CreateWorldScreenMixin(Text title) {
        super(title);
    }

    @ModifyVariable(method = "init", at = @At("STORE"), ordinal = 0)
    private DirectionalLayoutWidget injected(DirectionalLayoutWidget directionalLayoutWidget) {
        this.addDrawableChild(directionalLayoutWidget.add(
                TextIconButtonWidget.builder(
                                Text.empty(),
                                button -> MinecraftClient.getInstance().setScreen(new WorldPresetsScreen((CreateWorldScreen) (Object) this)),
                                true)
                        .width(20)
                        .texture(Identifier.of(BetterWorldPresets.MOD_ID, "icon/menu"), 15, 15)
                        .build()));
        return directionalLayoutWidget;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void registerListener(MinecraftClient client, Runnable runnable, GeneratorOptionsHolder generatorOptionsHolder, Optional defaultWorldType, OptionalLong seed, CreateWorldCallback callback, CallbackInfo ci) {
        BetterWorldPresets.registerPresetChangeListener(() -> {
            ((CreateWorldScreen)(Object)this).getWorldCreator().setSeed(BetterWorldPresets.getCurrentPreset().seed());
            ((CreateWorldScreen)(Object)this).getWorldCreator().setGameMode(WorldCreator.Mode.valueOf(BetterWorldPresets.getCurrentPreset().gameMode().name()));
        });
    }
}
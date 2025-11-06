package com.github.n0op3.mixin;

import com.github.n0op3.BetterWorldPresets;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CreateWorldScreen.class)
public abstract class WorldGenScreenMixin extends Screen {

    protected WorldGenScreenMixin(Text title) {
        super(title);
    }

    @ModifyVariable(method = "init", at =  @At("STORE"), ordinal = 0)
    private DirectionalLayoutWidget injected(DirectionalLayoutWidget directionalLayoutWidget) {
        this.addDrawableChild(directionalLayoutWidget.add(ButtonWidget.builder(Text.literal("L"), button -> BetterWorldPresets.LOGGER.info("Load a preset!")).width(20).build()));
        this.addDrawableChild(directionalLayoutWidget.add(ButtonWidget.builder(Text.literal("S"), button -> BetterWorldPresets.LOGGER.info("Save a preset!")).width(20).build()));
        return directionalLayoutWidget;
    }
}
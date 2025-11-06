package com.github.n0op3.better_world_presets.mixin;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CreateWorldScreen.class)
public abstract class WorldGenScreenMixin extends Screen {

    protected WorldGenScreenMixin(Text title) {
        super(title);
    }

    @ModifyVariable(method = "init", at = @At("STORE"), ordinal = 0)
    private DirectionalLayoutWidget injected(DirectionalLayoutWidget directionalLayoutWidget) {
        this.addDrawableChild(directionalLayoutWidget.add(TextIconButtonWidget.builder(Text.empty(), button -> BetterWorldPresets.LOGGER.info("Export a preset"), true).width(20).texture(Identifier.of(BetterWorldPresets.MOD_ID, "icon/export"), 15, 15).build()));
        this.addDrawableChild(directionalLayoutWidget.add(TextIconButtonWidget.builder(Text.empty(), button -> BetterWorldPresets.LOGGER.info("Import a preset"), true).width(20).texture(Identifier.of(BetterWorldPresets.MOD_ID, "icon/import"), 15, 15).build()));
        return directionalLayoutWidget;
    }
}
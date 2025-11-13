package com.github.n0op3.better_world_presets.screen;

import com.github.n0op3.better_world_presets.BetterWorldPreset;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.EmptyWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

public class PresetRenameScreen extends Screen {

    private final BetterPresetsScreen parent;
    private final DirectionalLayoutWidget layout = DirectionalLayoutWidget.vertical().spacing(5);
    private final TextFieldWidget nameFieldWidget;
    private final BetterWorldPreset preset;

    protected PresetRenameScreen(BetterPresetsScreen parent, BetterWorldPreset preset) {
        super(Text.literal("Rename the preset"));
        this.parent = parent;
        this.preset = preset;

        this.layout.add(new EmptyWidget(200, 20));
        this.layout.add(new TextWidget(Text.literal("Rename preset"), MinecraftClient.getInstance().textRenderer));
        this.nameFieldWidget = layout.add(new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 200, 20, Text.literal(preset.worldName())));
        this.nameFieldWidget.setText(preset.worldName());

        this.layout.forEachChild(this::addDrawableChild);
        this.refreshWidgetPositions();
    }

    @Override
    protected void refreshWidgetPositions() {
        this.layout.setX(width / 2 - layout.getWidth() / 2);
        this.layout.setY(height / 2 - layout.getHeight() / 2);
        this.layout.refreshPositions();
    }

    @Override
    public void close() {
        preset.setWorldName(nameFieldWidget.getText());
        MinecraftClient.getInstance().setScreen(parent);
    }
}
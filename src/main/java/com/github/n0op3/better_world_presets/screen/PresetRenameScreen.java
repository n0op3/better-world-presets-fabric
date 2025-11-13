package com.github.n0op3.better_world_presets.screen;

import com.github.n0op3.better_world_presets.BetterWorldPreset;
import com.github.n0op3.better_world_presets.BetterWorldPresets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;

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

        DirectionalLayoutWidget buttonLayout = DirectionalLayoutWidget.horizontal().spacing(4);
        buttonLayout.add(ButtonWidget.builder(Text.literal("Rename"), onPress -> renameAndClose()).size(98, 20).build());
        buttonLayout.add(ButtonWidget.builder(Text.literal("Cancel"), onPress -> close()).size(98, 20).build());
        this.layout.add(buttonLayout);

        this.layout.forEachChild(this::addDrawableChild);
        this.refreshWidgetPositions();
    }

    @Override
    protected void init() {
        this.refreshWidgetPositions();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW_KEY_ENTER) {
            this.renameAndClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    protected void refreshWidgetPositions() {
        this.layout.setX(width / 2 - layout.getWidth() / 2);
        this.layout.setY(height / 2 - layout.getHeight() / 2);
        this.layout.refreshPositions();
    }

    private void renameAndClose() {
        if (BetterWorldPresets.WORLD_PRESETS.stream().anyMatch(preset -> Objects.equals(preset.worldName(), nameFieldWidget.getText()))) {
            MinecraftClient.getInstance().setScreen(new ConfirmScreen(confirmed -> {
                this.close();
                if (confirmed) {
                    preset.setWorldName(nameFieldWidget.getText());
                } else {
                    MinecraftClient.getInstance().setScreen(new PresetRenameScreen(parent, preset));
                }
            }, Text.literal("This preset already exists!"), Text.literal("Do you want to overwrite the existing preset?")));
        } else {
            preset.setWorldName(nameFieldWidget.getText());
            this.close();
        }
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
    }
}
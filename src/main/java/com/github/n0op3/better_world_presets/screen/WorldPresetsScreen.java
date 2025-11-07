package com.github.n0op3.better_world_presets.screen;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import com.github.n0op3.better_world_presets.widget.PresetListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;

public class WorldPresetsScreen extends Screen {

    private final Screen parent;
    private PresetListWidget list;
    private ThreePartsLayoutWidget layout;
    private final ButtonWidget createButton = ButtonWidget.builder(Text.literal("Save settings as a preset"), button -> this.saveSettingsAsPreset()).build();
    private final ButtonWidget editButton = ButtonWidget.builder(Text.literal("Edit preset"), button -> this.editCurrentPreset()).build();
    private final ButtonWidget deleteButton = ButtonWidget.builder(Text.literal("Delete preset"), button -> this.deleteCurrentPreset()).build();
    private final ButtonWidget loadButton = ButtonWidget.builder(Text.literal("Load preset"), button -> this.loadCurrentPreset()).build();

    public WorldPresetsScreen() {
        super(Text.literal("World Presets"));
        this.parent = MinecraftClient.getInstance().currentScreen;
        this.layout = new ThreePartsLayoutWidget(this, 33, 54);
        this.editButton.active = false;
        this.deleteButton.active = false;
        this.loadButton.active = false;
    }

    @Override
    protected void init() {
        this.clearChildren();

        this.list = new PresetListWidget(MinecraftClient.getInstance(), layout.getWidth(), layout.getContentHeight(), layout.getY() + layout.getHeaderHeight(), layout.getHeaderHeight()).selectionCallback(this::entrySelected);

        this.layout = new ThreePartsLayoutWidget(this, 33, 54);
        layout.addHeader(new TextWidget(Text.literal("Preset Menu"), MinecraftClient.getInstance().textRenderer));

        layout.addBody(this.list);

        GridWidget grid = layout.addFooter(new GridWidget(0, 0).setRowSpacing(4).setColumnSpacing(8));

        grid.add(createButton, 0, 0);
        grid.add(editButton, 0, 1);
        grid.add(deleteButton, 1, 0);
        grid.add(loadButton, 1, 1);

        layout.refreshPositions();
        layout.forEachChild(this::addDrawableChild);
    }

    private void entrySelected(PresetListWidget.Entry entry) {
        if (entry != null) {
            this.editButton.active = true;
            this.deleteButton.active = true;
            this.loadButton.active = true;
        }
    }

    private void loadCurrentPreset() {
        BetterWorldPresets.LOGGER.info("Load preset: {}", list.getSelectedOrNull().getName());
    }

    private void deleteCurrentPreset() {
        if (list.getSelectedOrNull() == null) {
            return;
        }

        BetterWorldPresets.WORLD_PRESETS.remove(list.getSelectedOrNull().preset);
        this.clearAndInit();
    }

    private void editCurrentPreset() {
        if (list.getSelectedOrNull() == null) {
            return;
        }
        BetterWorldPresets.LOGGER.info("Edit preset: {}", list.getSelectedOrNull().getName());
    }

    private void saveSettingsAsPreset() {
        if (list.getSelectedOrNull() == null) {
            return;
        }

        BetterWorldPresets.LOGGER.info("Save settings as preset");
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
package com.github.n0op3.better_world_presets.screen;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import com.github.n0op3.better_world_presets.widget.PresetListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;

public class WorldPresetsScreen extends Screen {

    private final Screen parent;
    private PresetListWidget list;
    private ThreePartsLayoutWidget layout;
    private boolean disabled;
    private ButtonWidget createButton = ButtonWidget.builder(Text.literal("Create new preset"), button -> this.createNewPreset()).build();
    private ButtonWidget editButton = ButtonWidget.builder(Text.literal("Edit preset"), button -> this.editCurrentPreset()).build();
    private ButtonWidget deleteButton = ButtonWidget.builder(Text.literal("Delete preset"), button -> this.deleteCurrentPreset()).build();
    private ButtonWidget loadButton = ButtonWidget.builder(Text.literal("Load preset"), button -> this.loadCurrentPreset()).build();

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
    }

    private void editCurrentPreset() {
        if (list.getSelectedOrNull() == null) {
            return;
        }
        BetterWorldPresets.LOGGER.info("Edit preset: {}", list.getSelectedOrNull().getName());
    }

    private void createNewPreset() {
        if (list.getSelectedOrNull() == null) {
            return;
        }

        BetterWorldPresets.LOGGER.info("Add a new preset");
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
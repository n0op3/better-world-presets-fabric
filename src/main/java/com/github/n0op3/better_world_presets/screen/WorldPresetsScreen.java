package com.github.n0op3.better_world_presets.screen;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import com.github.n0op3.better_world_presets.WorldPreset;
import com.github.n0op3.better_world_presets.config.WorldPresetConfig;
import com.github.n0op3.better_world_presets.widget.PresetListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.text.Text;
import net.minecraft.world.gen.GeneratorOptions;

public class WorldPresetsScreen extends Screen {

    private final CreateWorldScreen parent;
    private final ButtonWidget createButton = ButtonWidget.builder(Text.literal("Save settings as a preset"), button -> this.saveSettingsAsPreset()).build();
    private final ButtonWidget backButton = ButtonWidget.builder(Text.literal("Back"), button -> this.back()).build();
    private PresetListWidget list;
    private final ButtonWidget loadButton = ButtonWidget.builder(Text.literal("Load preset"), button -> this.loadCurrentPreset()).build();
    private ThreePartsLayoutWidget layout;    private final ButtonWidget deleteButton = ButtonWidget.builder(Text.literal("Delete preset"), button -> this.deleteCurrentPreset()).build();
    public WorldPresetsScreen(CreateWorldScreen parent) {
        super(Text.literal("World Presets"));
        this.parent = parent;
        this.layout = new ThreePartsLayoutWidget(this, 33, 54);
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
        grid.add(deleteButton, 0, 1);
        grid.add(loadButton, 1, 0);
        grid.add(backButton, 1, 1);

        layout.refreshPositions();
        layout.forEachChild(this::addDrawableChild);
    }

    private void entrySelected(PresetListWidget.Entry entry) {
        if (entry != null) {
            this.deleteButton.active = true;
            this.loadButton.active = true;
        } else {
            this.deleteButton.active = false;
            this.loadButton.active = false;
        }
    }

    private void loadCurrentPreset() {
        if (list.getSelectedOrNull() == null) {
            return;
        }
        BetterWorldPresets.LOGGER.info("Load preset: {}", list.getSelectedOrNull().getName());
        BetterWorldPresets.setCurrentPreset(list.getSelectedOrNull().preset);
        this.close();
    }

    private void deleteCurrentPreset() {
        if (list.getSelectedOrNull() == null) {
            return;
        }

        BetterWorldPresets.removePreset(list.getSelectedOrNull().preset);
        this.entrySelected(null);
        this.clearAndInit();
    }

    private void back() {
        MinecraftClient.getInstance().setScreen(this.parent);
    }

    private void saveSettingsAsPreset() {
        GeneratorOptions generatorOptions = parent.getWorldCreator().getGeneratorOptionsHolder().generatorOptions();
        WorldCreator worldCreator = parent.getWorldCreator();
        BetterWorldPresets.addPreset(new WorldPreset(
                worldCreator.getWorldName(),
                worldCreator.getSeed(),
                worldCreator.shouldGenerateStructures(),
                generatorOptions.hasBonusChest(),
                worldCreator.getGameMode(),
                worldCreator.getDifficulty(),
                worldCreator.areCheatsEnabled(),
                worldCreator.getWorldType(),
                worldCreator.getGameRules(),
                worldCreator.getGeneratorOptionsHolder().selectedDimensions()
        ));
        this.clearAndInit();
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
    }


}
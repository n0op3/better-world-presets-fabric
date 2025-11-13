package com.github.n0op3.better_world_presets.screen;

import com.github.n0op3.better_world_presets.BetterWorldPreset;
import com.github.n0op3.better_world_presets.BetterWorldPresets;
import com.github.n0op3.better_world_presets.config.PresetsManager;
import com.github.n0op3.better_world_presets.widget.PresetListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.world.gen.GeneratorOptions;

public class BetterPresetsScreen extends Screen {

    private final CreateWorldScreen parent;
    private PresetListWidget list;
    private ThreePartsLayoutWidget layout;
    private final ButtonWidget createButton = ButtonWidget.builder(Text.literal("Save settings as a preset"), button -> this.saveSettingsAsPreset()).build();
    private final ButtonWidget backButton = ButtonWidget.builder(Text.literal("Back"), button -> this.back()).build();
    private final ButtonWidget loadButton = ButtonWidget.builder(Text.literal("Load preset"), button -> this.loadCurrentPreset()).build();
    private final ButtonWidget deleteButton = ButtonWidget.builder(Text.literal("Delete preset"), button -> this.deleteCurrentPreset()).build();
    private final ButtonWidget renameButton = ButtonWidget.builder(Text.literal("Rename preset"), button -> this.renamePreset()).build();

    public BetterPresetsScreen(CreateWorldScreen parent) {
        super(Text.literal("World Presets"));
        this.parent = parent;
        this.layout = new ThreePartsLayoutWidget(this, 33, 54);
        this.deleteButton.active = false;
        this.loadButton.active = false;
        this.renameButton.active = false;
        PresetsManager.WORLD_CREATOR = parent.getWorldCreator();
    }

    @Override
    protected void init() {
        PresetsManager.loadPresets();
        entrySelected(null);

        this.clearChildren();

        this.list = new PresetListWidget(MinecraftClient.getInstance(), layout.getWidth(), layout.getContentHeight(), layout.getY() + layout.getHeaderHeight(), layout.getHeaderHeight()).selectionCallback(this::entrySelected);

        this.layout = new ThreePartsLayoutWidget(this, 33, 54);

        layout.addHeader(new TextWidget(Text.literal("Preset Menu"), MinecraftClient.getInstance().textRenderer));
        addDrawableChild(ButtonWidget.builder(Text.literal("Open presets folder"), button -> Util.getOperatingSystem().open(BetterWorldPresets.getConfigDir().toUri())).position(width - ButtonWidget.DEFAULT_WIDTH - 4, layout.getHeaderHeight() / 2 - ButtonWidget.DEFAULT_HEIGHT / 2).build());

        layout.addBody(this.list);

        GridWidget grid = layout.addFooter(new GridWidget(0, 0).setRowSpacing(4).setColumnSpacing(8));

        grid.add(renameButton, 0, 0);
        grid.add(loadButton, 0, 1);
        grid.add(deleteButton, 0, 2);
        grid.add(createButton, 1, 0);
        grid.add(backButton, 1, 2);

        layout.refreshPositions();
        layout.forEachChild(this::addDrawableChild);
    }

    private void entrySelected(PresetListWidget.Entry entry) {
        if (entry != null) {
            this.deleteButton.active = true;
            this.loadButton.active = true;
            this.renameButton.active = true;
        } else {
            this.deleteButton.active = false;
            this.loadButton.active = false;
            this.renameButton.active = false;
        }
    }

    private void loadCurrentPreset() {
        BetterWorldPresets.setCurrentPreset(getSelectedPreset());
        this.close();
    }

    private void deleteCurrentPreset() {
        BetterWorldPreset preset = getSelectedPreset();
        MinecraftClient.getInstance().setScreen(new ConfirmScreen(confirmed -> {
            if (confirmed) {
                BetterWorldPresets.removePreset(preset);
                this.entrySelected(null);
                this.clearAndInit();
            }
            MinecraftClient.getInstance().setScreen(this);
        }, Text.literal("Are you sure?"), Text.literal("If you proceed, the preset \"" + getSelectedPreset().worldName() + "\" will be deleted. This cannot be undone!")));
    }

    private void renamePreset() {
        MinecraftClient.getInstance().setScreen(new PresetRenameScreen(this, getSelectedPreset()));
    }

    private void back() {
        MinecraftClient.getInstance().setScreen(this.parent);
    }

    private void saveSettingsAsPreset() {
        WorldCreator worldCreator = parent.getWorldCreator();

        if (BetterWorldPresets.WORLD_PRESETS.stream().anyMatch(betterWorldPreset -> betterWorldPreset.worldName().equals(worldCreator.getWorldName()))) {
            MinecraftClient.getInstance().setScreen(new ConfirmScreen(confirmed -> {
                if (confirmed) savePreset();

                MinecraftClient.getInstance().setScreen(this);
            }, Text.literal("This preset already exists!"), Text.literal("If you proceed, the " + worldCreator.getWorldName() + " preset will be overridden. Are you sure?")));
        } else {
            savePreset();
        }
    }

    private void savePreset() {
        WorldCreator worldCreator = parent.getWorldCreator();
        GeneratorOptions generatorOptions = worldCreator.getGeneratorOptionsHolder().generatorOptions();
        BetterWorldPresets.addPreset(new BetterWorldPreset(
                worldCreator.getWorldName(),
                worldCreator.getSeed(),
                worldCreator.shouldGenerateStructures(),
                generatorOptions.hasBonusChest(),
                worldCreator.getGameMode(),
                worldCreator.getDifficulty(),
                worldCreator.areCheatsEnabled(),
                worldCreator.getGameRules(),
                worldCreator.getGeneratorOptionsHolder().selectedDimensions().getChunkGenerator(),
                worldCreator.getWorldType()
        ));
        this.clearAndInit();

    }

    private BetterWorldPreset getSelectedPreset() {
        return list.getFocused().preset;
    }

    @Override
    public void close() {
        PresetsManager.saveAllPresets();
        MinecraftClient.getInstance().setScreen(parent);
    }

}